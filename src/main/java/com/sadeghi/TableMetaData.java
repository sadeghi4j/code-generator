package com.sadeghi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M.Alimohamadi on 7/4/2016.
 */
public class TableMetaData {
    private boolean tableOrView; // if is true -> TABLE, if is false -> VIEW
    private String name; // table or view name

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    List<ColumnMetaData> columns;

    public void addColumnMetaData(ColumnMetaData columnMetaData){
        columns.add(columnMetaData);
    }

    public TableMetaData(boolean tableOrView) {
        this.tableOrView =tableOrView;
        columns=new ArrayList<ColumnMetaData>();
    }
}
