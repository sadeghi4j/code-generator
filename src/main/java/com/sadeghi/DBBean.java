package com.sadeghi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
    public List<String> getTables(boolean tableOrView,String schemaPattern) {
        String metaDataType="TABLE";
        if(!tableOrView){
            metaDataType="VIEW";
        }
        ResultSet resultSet = null;
        try {
            /*ResultSet catalog = getConnection().getMetaData().getUserName();
            while (catalog.next()) {
                String c = catalog.getString(catalog.getString(0));
                System.out.println("catalog: " + c);
            }*/
            String catalog = getConnection().getMetaData().getUserName();
            resultSet = getConnection().getMetaData().getTables(catalog, schemaPattern+"%", "%", new String[]{metaDataType});
            List<String> tableNames = new LinkedList<String>();
            while (resultSet.next()) {
                String name = resultSet.getString("TABLE_NAME");
                System.out.println(name);
                tableNames.add(name);
            }
            return tableNames;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // if tableOrView parameter is true function return table metadata,if is false return view metadata.
    public TableMetaData getTableMetaData(String tableName,boolean tableOrView) {
        TableMetaData retTableMetaData=new TableMetaData(tableOrView);
        StringBuilder query = new StringBuilder();
        query.append("select * from ").append(tableName);
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());
            ResultSetMetaData tableMetaData = resultSet.getMetaData();
            for (int i = 1; i <= tableMetaData.getColumnCount(); i++) {
                ColumnMetaData columnMetaData=new ColumnMetaData();
                columnMetaData.setColumnName(tableMetaData.getColumnName(i));
                columnMetaData.setColumnClassName(tableMetaData.getColumnClassName(i));
                columnMetaData.setColumnLable(tableMetaData.getColumnLabel(i));
                columnMetaData.setColumnType(tableMetaData.getColumnType(i));
                columnMetaData.setColumnTypeName(tableMetaData.getColumnTypeName(i));

                retTableMetaData.addColumnMetaData(columnMetaData);
                System.out.println("ColumnName: " + tableMetaData.getColumnName(i) + " * " + "ColumnLabel: " + tableMetaData.getColumnLabel(i) + " * "
                        + "ColumnType: " + tableMetaData.getColumnType(i) + " * " + "ColumnTypeName: " + tableMetaData.getColumnTypeName(i) + " * " +
                        "ColumnClassName: " + tableMetaData.getColumnClassName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retTableMetaData;
    }
}