package com.sadeghi;

/**
 * Created by M.Alimohamadi on 7/4/2016.
 */
public class ColumnMetaData {
    private String columnName;
    private String columnLabel;
    private int columnType;
    private String columnTypeName;
    private boolean nullable;
    private int scale;
    private int precision;

    public String getColumnLabel() {
        return columnLabel;
    }

    public void setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
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

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }


    @Override
    public String toString() {
        return "ColumnMetaData{" +
                "columnName='" + columnName + '\'' +
                ", columnLabel='" + columnLabel + '\'' +
                ", columnType=" + columnType +
                ", columnTypeName='" + columnTypeName + '\'' +
                ", nullable=" + nullable +
                ", scale=" + scale +
                ", precision=" + precision +
                ", columnClassName='" + columnClassName + '\'' +
                '}';
    }

}
