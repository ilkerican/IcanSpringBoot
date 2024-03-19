package com.ican.initial.demo.DatabaseLayer;

import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuxMethods {

    private static final String COLUMNNAMETABLENAME = "TABLENAME";
    private static final String COLUMNNAMEColumnName = "ColumnName";
    private static final String COLUMNNAMEColumnType = "ColumnType";
    private static final String COLUMNNAMEColumnLength = "ColumnLength";
    private static final String COLUMNNAMENullable = "Nullable";

    public class DatabaseRow {
        private Map<String, Object> fields;

        public DatabaseRow() {
            fields = new HashMap<>();
        }

        public void setFieldValue(String fieldName, Object fieldValue) {
            fields.put(fieldName, fieldValue);
        }

        public Object getFieldValue(String fieldName) {
            return fields.get(fieldName);
        }
    }

    public static List<Map<String, Object>> filterListByColumn(List<Map<String, Object>> originalList,
            String columnName, Object columnValue) {
        return originalList.stream()
                .filter(map -> map.containsKey(columnName) && map.get(columnName).equals(columnValue))
                .collect(Collectors.toList());
    }

    private static String validateColumn(String columnType, Integer columnLength, boolean nullable, Object fieldValue) {

        return null;// TODO : implement this
    }

    public static String doValidityCheck(List<Map<String, Object>> metadata, String tableName,
            DatabaseRow rowData) {
        List<Map<String, Object>> tableDefinition = filterListByColumn(metadata, COLUMNNAMETABLENAME, tableName);

        String valColumnNameIteration = null;
        String valColumnName = null;
        String valColumnType = null;
        Integer valColumnLength = null;
        boolean valNullable = false;

        String validationResult = "";

        for (Map<String, Object> map : tableDefinition) {
            // Iterate over the map entries
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                // Get the column name into the variable named ColumnName
                valColumnNameIteration = entry.getKey().toString();

                switch (valColumnNameIteration) {
                    case COLUMNNAMEColumnName:
                        valColumnName = entry.getValue().toString();
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

            Object fieldValue = rowData.getFieldValue(valColumnName);
            validationResult += validateColumn(valColumnType, valColumnLength, valNullable, fieldValue);

            // Her kolonla ilgili değerlendirme bu noktada yapılacak
            // valColumnName, valColumnType, valColumnLength, valNullable : bu değerleri
            // kullanarak values içinde gelecek olan satırdan ilgili kolon bulunup
            // o kolon için validasyon kontrolü yapılacak
            // Bu ayrı bir fonksiyon olsa iyi olur
        }

        return validationResult;

    }

}
