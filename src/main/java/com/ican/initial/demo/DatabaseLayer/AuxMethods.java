package com.ican.initial.demo.DatabaseLayer;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

public class AuxMethods {

    private static final String COLUMNNAMETABLENAME = "TABLENAME";
    private static final String COLUMNNAMEColumnName = "ColumnName";
    private static final String COLUMNNAMEColumnType = "ColumnType";
    private static final String COLUMNNAMEColumnLength = "ColumnLength";
    private static final String COLUMNNAMENullable = "Nullable";

    public static List<Map<String, Object>> filterListByColumn(List<Map<String, Object>> originalList,
            String columnName, Object columnValue) {
        return originalList.stream()
                .filter(map -> map.containsKey(columnName) && map.get(columnName).equals(columnValue))
                .collect(Collectors.toList());
    }

    public static void doValidityCheck(List<Map<String, Object>> metadata, String tableName,
            List<Map<String, Object>> values) {
        List<Map<String, Object>> tableDefinition = filterListByColumn(metadata, COLUMNNAMETABLENAME, tableName);

        String valColumnName = null;
        String valColumnType = null;
        Integer valColumnLength = null;
        boolean valNullable = false;

        for (Map<String, Object> map : tableDefinition) {
            // Iterate over the map entries
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                // Get the column name into the variable named ColumnName
                valColumnName = entry.getKey().toString();

                switch (valColumnName) {
                    case COLUMNNAMEColumnName:
                        // columnName is not important, there is no validation for columnName
                        break;
                    case COLUMNNAMEColumnType:
                        valColumnType = entry.getValue().toString();
                        break;
                    case COLUMNNAMEColumnLength:
                        valColumnLength = (Integer) entry.getValue();
                        break;
                    case COLUMNNAMENullable:
                        valNullable = (boolean) entry.getValue();
                        break;
                    default:
                        break;
                }

            }
        }

        int a = 3;
    }

}
