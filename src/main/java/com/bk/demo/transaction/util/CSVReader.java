package com.bk.demo.transaction.util;

import com.bk.demo.transaction.model.CreditTransaction;
import java.lang.reflect.Field;

public class CSVReader<T> {
    private final Class<T> type;
    private final boolean hasHeader;

    /**
     * Constructor for CSVReader
     */
    public CSVReader(Class<T> type, boolean hasHeader) {
        this.type = type;
        this.hasHeader = hasHeader;
    }

    /**
     * Read a CSV file and convert it to a list of POJOs
     *
     * @param filePath Path to the CSV file
     * @return List of POJOs of type T
     * @throws Exception If there's an error reading the file or mapping to POJOs
     */
/*    public CSVParser parseFile(String filePath) throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)){
             BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));
             CSVFormat format = hasHeader ? CSVFormat.DEFAULT.builder().setHeader().build() : CSVFormat.DEFAULT.builder().build();
             return format.parse(reader);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV resource file: " + filePath, e);
        }
    }*/

    /**
     * Read a CSV file and convert it to a list of POJOs
     *
     * @param filePath Path to the CSV file
     * @return List of POJOs of type T
     * @throws Exception If there's an error reading the file or mapping to POJOs
     */
    /*public List<T> read(String filePath) throws Exception {
        List<T> result = new ArrayList<>();

        try (Reader reader = new FileReader(filePath)) {
            CSVFormat format = hasHeader ? CSVFormat.DEFAULT.withFirstRecordAsHeader() : CSVFormat.DEFAULT;
            CSVParser parser = format.parse(reader);

            for (CSVRecord record : parser) {
                T instance = type.getDeclaredConstructor().newInstance();

                if (hasHeader) {
                    // Map by header names
                    Map<String, Integer> headerMap = parser.getHeaderMap();
                    for (Field field : type.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (headerMap.containsKey(field.getName())) {
                            String value = record.get(field.getName());
                            setFieldValue(field, instance, value);
                        }
                    }
                } else {
                    // Map by position
                    Field[] fields = type.getDeclaredFields();
                    for (int i = 0; i < record.size() && i < fields.length; i++) {
                        Field field = fields[i];
                        field.setAccessible(true);
                        String value = record.get(i);
                        setFieldValue(field, instance, value);
                    }
                }

                result.add(instance);
            }
        }

        return result;
    }*/

    /**
     * Set field value based on its type
     *
     * @param field    The field to set
     * @param instance The object instance
     * @param value    The string value from CSV
     * @throws IllegalAccessException If field cannot be accessed
     */
    public void setFieldValue(Field field, CreditTransaction instance, String value) throws IllegalAccessException {
        if (value == null || value.isEmpty()) {
            return;
        }

        Class<?> fieldType = field.getType();

        if (fieldType == String.class) {
            field.set(instance, value);
        } else if (fieldType == int.class || fieldType == Integer.class) {
            field.set(instance, Integer.parseInt(value));
        } else if (fieldType == long.class || fieldType == Long.class) {
            field.set(instance, Long.parseLong(value));
        } else if (fieldType == double.class || fieldType == Double.class) {
            field.set(instance, Double.parseDouble(value));
        } else if (fieldType == float.class || fieldType == Float.class) {
            field.set(instance, Float.parseFloat(value));
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            field.set(instance, Boolean.parseBoolean(value));
        }
        // Add more type conversions as needed
    }
}
