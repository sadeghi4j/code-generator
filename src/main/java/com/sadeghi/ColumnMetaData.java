package com.sadeghi;

/**
 * Created by M.Alimohamadi on 7/4/2016.
 */
public class ColumnMetaData {
    private String columnName;
    private String columnLable;
    private int columnType;
    private String columnTypeName;

    public String getColumnLable() {
        return columnLable;
    }

    public void setColumnLable(String columnLable) {
        this.columnLable = columnLable;
    }

    public int getColumnType() {
        return columnType;
    }

    public void setColumnType(int  columnType) {
        this.columnType = columnType;
    }

    public String getColumnTypeName() {
        return columnTypeName;
    }

    public void setColumnTypeName(String columnTypeName) {
        this.columnTypeName = columnTypeName;
    }

    private String columnClassName;

    public String getColumnClassName() {
        return columnClassName;
    }

    public void setColumnClassName(String columnClassName) {
        this.columnClassName = columnClassName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
