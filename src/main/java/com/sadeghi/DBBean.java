package com.sadeghi;

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
        String metaDataType="TABLE";
        if(!tableOrView){
            metaDataType="VIEW";
        }
        List<String> tableNames = new LinkedList<String>();
        ResultSet resultSet = null;
        Connection connection = getConnection();
        try {
            /*ResultSet catalog = getConnection().getMetaData().getUserName();
            while (catalog.next()) {
                String c = catalog.getString(catalog.getString(0));
                System.out.println("catalog: " + c);
            }*/
            String catalog = connection.getMetaData().getUserName();
            resultSet = connection.getMetaData().getTables(catalog, schemaPattern+"%", "%", new String[]{metaDataType});

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
    public TableMetaData getTableMetaData(String tableName,boolean tableOrView) {
        TableMetaData retTableMetaData=new TableMetaData(tableOrView);
        retTableMetaData.setName(tableName);
        StringBuilder query = new StringBuilder();
        query.append("select * from ").append(tableName);
            Connection connection = getConnection();
            Statement statement = null;
            ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query.toString());
            ResultSetMetaData tableMetaData = resultSet.getMetaData();
            for (int i = 1; i <= tableMetaData.getColumnCount(); i++) {
                ColumnMetaData columnMetaData=new ColumnMetaData();
                columnMetaData.setColumnName(tableMetaData.getColumnName(i));
                columnMetaData.setColumnClassName(tableMetaData.getColumnClassName(i));
                columnMetaData.setColumnLabel(tableMetaData.getColumnLabel(i));
                columnMetaData.setColumnType(tableMetaData.getColumnType(i));
                columnMetaData.setColumnTypeName(tableMetaData.getColumnTypeName(i));
                columnMetaData.setScale(tableMetaData.getScale(i));
                columnMetaData.setPrecision(tableMetaData.getPrecision(i));
                columnMetaData.setNullable(tableMetaData.isNullable(i) == 1 ? true : false);

                retTableMetaData.addColumnMetaData(columnMetaData);
                System.out.println(columnMetaData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeConnection(connection);
        }
        return retTableMetaData;
    }
}