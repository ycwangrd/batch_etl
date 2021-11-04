package com.huayanginfo.etl.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月04日 星期三 17:02:01
 */
@Log4j2
@Service
public class JdbcDataService {

    @Autowired
    private DataSource dataSource;

    /**
     * 执行SQL并返回值
     *
     * @param tableName 表名
     * @return
     */
    private String getCreateTableSql(String tableName) {
        try {
            Connection conn = dataSource.getConnection();
            ResultSet resultSet = conn.createStatement().executeQuery("show create table " + tableName);
            resultSet.next();
            return resultSet.getString(2);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("出现异常:", e);
        }
        return "";
    }

    /**
     * 将MySQL建表语句转换为ClickHouse建表语句
     *
     * @param tableName 表名
     * @return
     */
    public String changeMysqlTable2ClickHouse(String tableName) {
        String createTableSql = getCreateTableSql(tableName);
        String[] rows = createTableSql.split("\n");
        StringBuilder replaceTables = new StringBuilder();
        String primaryKey = "";
        for (String row : rows) {
            System.out.println(row);
            if (StringUtils.containsIgnoreCase(row, "primary key")) {
                primaryKey = "`" + row.split("`")[1] + "`";
            }
            if (row.contains(" KEY ")) {
                continue;
            }
            if (row.contains(") ENGINE=InnoDB")) {
                row = ") ENGINE = MergeTree";
            }
            String changeRow = row.replaceAll(" NOT NULL", "")
                    .replaceAll(" NULL", "")
                    .replaceAll(" COLLATE utf8_general_ci", "")
                    .replaceAll(" COLLATE utf8mb4_zh_0900_as_cs", "")
                    .replaceAll("AUTO_INCREMENT", "")
                    .replaceAll("CHARACTER SET utf8mb4", "")
                    .replaceAll("CHARACTER SET utf8", "")
                    .replaceAll("ON UPDATE CURRENT_TIMESTAMP", "")
                    .replaceAll("CURRENT_TIMESTAMP", "")
                    .replaceAll("double\\(\\d+,\\d+\\)", "Float64")
                    .replaceFirst("double", "Float64")
                    .replaceAll("float\\(\\d+,\\d+\\)", "Float32")
                    .replaceFirst(" timestamp ", " DateTime ")
                    .replaceFirst(" datetime ", " DateTime ")
                    .replaceFirst(" date ", " Date ")
                    .replaceFirst(" time ", " DateTime ");
            // varchar 和 char
            changeRow = changeRow.replaceAll("varchar\\(\\d+\\)", "String")
                    .replaceAll("char\\(\\d+\\)", "String")
                    .replaceAll("longtext", "String")
                    .replaceAll("text", "String");
            String[] changeColumns = changeRow.split("[ ]");
            // 设置第一个字段为主键
            if (StringUtils.isBlank(primaryKey) && !StringUtils.startsWithIgnoreCase(row, "CREATE TABLE")) {
                primaryKey = changeColumns[2];
            }
            final String columnType = changeColumns[3];
            // 附件字段跳过
            if (StringUtils.containsIgnoreCase(columnType, "blob")) {
                continue;
            }
            if (StringUtils.containsAnyIgnoreCase(columnType, "smallint", "tinyint", "int", "bigint")) {
                String columnLen = columnType
                        .replaceAll("smallint", "")
                        .replaceAll("tinyint", "")
                        .replaceAll("bigint", "")
                        .replaceAll("int", "")
                        .replaceAll("\\(", "")
                        .replaceAll("\\)", "");
                int length = (StringUtils.isNotBlank(columnLen) && !",".equalsIgnoreCase(StringUtils.trim(columnLen))) ? Integer.parseInt(columnLen) : 4;
                String type = "int";
                if (columnType.contains("smallint")) {
                    type = "smallint";
                } else if (columnType.contains("tinyint")) {
                    type = "tinyint";
                } else if (columnType.contains("bigint")) {
                    type = "bigint";
                }
                // 去掉长度
                changeRow = changeRow.replaceFirst("\\(" + length + "\\)", "");
                if (length < 3) {
                    changeRow = changeRow.replaceFirst(type, "Int8");
                } else if (length < 5) {
                    changeRow = changeRow.replaceFirst(type, "Int16");
                } else if (length <= 9) {
                    changeRow = changeRow.replaceFirst(type, "Int32");
                } else {
                    changeRow = changeRow.replaceFirst(type, "Int64");
                }
            }
            changeRow = changeRow.replaceAll("DEFAULT\\s*COMMENT", "COMMENT");
            changeRow = changeRow.replaceAll("DEFAULT\\s+\\)", "\\)").replaceAll("DEFAULT\\,", "\\,");
            if (changeRow.startsWith(")") && replaceTables.toString().endsWith(",")) {
                replaceTables.deleteCharAt(replaceTables.length() - 1);
            }
            replaceTables.append(changeRow);
        }
        replaceTables.append(" primary key " + primaryKey);
        return replaceTables.toString();
    }
}
