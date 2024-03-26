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

    public static final String CTYPE_INTEGER = "Integer";
    public static final String CTYPE_STRING = "String";
    public static final String CTYPE_BOOLEAN = "boolean";

    public static final String MSG_NOTNULL = "Field value cannot be null.";
    public static final String MSG_STRINGTOOLONG = "Field value length exceeds expected size.";
    public static final String NEWLINE = "\r\n";

    public static class DatabaseRow {
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

    private static String appendColumnNameToErrorMessage(String columName, String message) {
        return "COLUMNNAME : " + columName + ", MESSAGE : " + message;
    }

    private static String validateString(String columnName, Integer columnLength, Object fieldValue) {
        String result = "";

        Integer fieldLength = fieldValue.toString().length();

        if (fieldLength > columnLength) {
            result = appendColumnNameToErrorMessage(columnName,
                    MSG_STRINGTOOLONG + " : " + fieldLength + " vs " + columnLength);
        }
        return result + NEWLINE;
    }

    private static String validateColumn(String columnName, String columnType, Integer columnLength, boolean nullable,
            Object fieldValue) {

        String result = "";

        if (!nullable) {
            if (fieldValue == null) {
                result += appendColumnNameToErrorMessage(columnName, MSG_NOTNULL);
                return result + NEWLINE;
            }
        }

        switch (columnType) {
            case CTYPE_STRING:
                result += validateString(columnName, columnLength, fieldValue);
                break;
            default:
                break;
        }

        return result;
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
            validationResult += validateColumn(valColumnName, valColumnType, valColumnLength, valNullable, fieldValue);

        }

        System.out.println(validationResult);
        return validationResult;

    }

}
