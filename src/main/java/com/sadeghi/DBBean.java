package com.sadeghi;

import oracle.jdbc.OracleConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tezi on 04/07/16.
 */
@Component
public class DBBean extends JdbcDaoSupport {

    @Autowired
    public DBBean(DataSource dataSource) {
        setDataSource(dataSource);
    }

    // if tableOrView parameter is true function return table names,if is false return view names.
    public List<String> getTables(boolean tableOrView, String schemaPattern) {
        String metaDataType = "TABLE";
        if (!tableOrView) {
            metaDataType = "VIEW";
        }
        List<String> tableNames = new LinkedList<String>();
        ResultSet resultSet = null;
        Connection connection = getConnection();
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            String catalog = databaseMetaData.getUserName();
            resultSet = databaseMetaData.getTables(catalog, schemaPattern + "%", "%", new String[]{metaDataType});

            while (resultSet.next()) {
                String name = resultSet.getString("TABLE_NAME");
                System.out.println(name);
                tableNames.add(name);
            }
            return tableNames;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeConnection(connection);
        }
        return tableNames;
    }

    // if tableOrView parameter is true function return table metadata,if is false return view metadata.
    public TableMetaData getTableMetaData(String tableName, String schemaPattern, boolean tableOrView) {
        TableMetaData retTableMetaData = new TableMetaData(tableOrView);
        retTableMetaData.setName(tableName);
        StringBuilder tableMDQuery = new StringBuilder();
        StringBuilder fkQuery = new StringBuilder();
        tableMDQuery.append("select * from ").append(tableName);
        fkQuery.append("SELECT a.CONSTRAINT_NAME,a.TABLE_NAME,d.COLUMN_NAME, b.TABLE_NAME,c.COLUMN_NAME from all_constraints a\n" +
                " JOIN all_constraints b ON a.R_CONSTRAINT_NAME=b.CONSTRAINT_NAME\n" +
                " JOIN all_cons_columns c ON c.CONSTRAINT_NAME=a.R_CONSTRAINT_NAME\n" +
                " JOIN all_cons_columns d ON d.CONSTRAINT_NAME=a.CONSTRAINT_NAME\n" +
                " WHERE a.OWNER='").append(schemaPattern).append("' AND a.CONSTRAINT_TYPE='R'").append("AND a.TABLE_NAME='")
        .append(tableName).append("'");
        OracleConnection connection = (OracleConnection) getConnection();
        connection.setRemarksReporting(true);
        Statement tableMDStatement = null;
        Statement fkStatement = null;
        ResultSet tableResultSet = null;
        ResultSet columnsResultSet = null;
        ResultSet fkResultSet = null;

        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            String catalog = databaseMetaData.getUserName();
            columnsResultSet = databaseMetaData.getColumns(catalog, schemaPattern + "%", tableName + "%", "%");
            List<String> comments = new LinkedList<String>();

            String comment;
            while (columnsResultSet.next()) {
                comment = columnsResultSet.getString("REMARKS");
                comments.add(comment);
            }

            tableMDStatement = connection.createStatement();
            fkStatement = connection.createStatement();
            tableResultSet = tableMDStatement.executeQuery(tableMDQuery.toString());
            fkResultSet = fkStatement.executeQuery(fkQuery.toString());
            //while

//            tableMDStatement.executeQuery(tableMDQuery.toString());
            ResultSetMetaData tableMetaData = tableResultSet.getMetaData();
            for (int i = 1; i <= tableMetaData.getColumnCount(); i++) {
                ColumnMetaData columnMetaData = new ColumnMetaData();
                columnMetaData.setColumnName(tableMetaData.getColumnName(i));
                columnMetaData.setColumnClassName(tableMetaData.getColumnClassName(i));
                columnMetaData.setColumnLabel(tableMetaData.getColumnLabel(i));
                columnMetaData.setColumnType(tableMetaData.getColumnType(i));
                columnMetaData.setColumnTypeName(tableMetaData.getColumnTypeName(i));
                columnMetaData.setScale(tableMetaData.getScale(i));
                columnMetaData.setPrecision(tableMetaData.getPrecision(i));
                columnMetaData.setNullable(tableMetaData.isNullable(i) == 1 ? true : false);
                columnMetaData.setComment(comments.get(i - 1));

                retTableMetaData.addColumnMetaData(columnMetaData);
                System.out.println(columnMetaData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeStatement(tableMDStatement);
            JdbcUtils.closeStatement(fkStatement);
            JdbcUtils.closeResultSet(tableResultSet);
            JdbcUtils.closeResultSet(columnsResultSet);
            JdbcUtils.closeResultSet(fkResultSet);
            JdbcUtils.closeConnection(connection);
        }
        return retTableMetaData;
    }
}