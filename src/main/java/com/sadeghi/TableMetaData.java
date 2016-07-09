package com.sadeghi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M.Alimohamadi on 7/4/2016.
 */
public class TableMetaData {
    private boolean tableOrView; // if is true -> TABLE, if is false -> VIEW
    private String name; // table or view name
    private String prefix = "TBL_";
    private List<ColumnMetaData> columns;

    public TableMetaData(boolean tableOrView) {
        this.tableOrView = tableOrView;
        columns = new ArrayList<ColumnMetaData>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return StringUtil.underscoreToCamelCase(prefix, false, name);
    }

    public void addColumnMetaData(ColumnMetaData columnMetaData) {
        columns.add(columnMetaData);
    }

}
