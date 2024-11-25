package com.example.data_classes;

public class Classes {
    public static final String FIELD_CLASS = "clasz";
    public static final String FIELD_DEPARTMENT = "department";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_NUMBER = "number";
    public static final String FIELD_ID = "id";
    public static final String FIELD_DESCRIPTION = "description";

    private String clasz;
    private String department;
    private String name;
    private String description;
    private int number;
    private int id;

    public Classes() {}

    public Classes(String clasz, String department, String name, String description, int number, int id) {
        this.clasz = clasz;
        this.department = department;
        this.name = name;
        this.description = description;
        this.number = number;
        this.id = id;

    }
    public String getFieldName() {
        return name;
    }

    public void setFieldName(String name) {
        this.name = name;
    }

    public String getFieldClasz() {
        return clasz;
    }

    public void setFieldClasz(String clasz) {
        this.clasz = clasz;
    }

    public String getFieldDescription() {
        return description;
    }

    public void setFieldDescription(String description) {
        this.description = description;
    }
    public int getFieldNumber() {
        return number;
    }

    public void setFieldNumber(int number) {
        this.number = number;
    }

    public int getFieldId() {
        return id;
    }

    public void setFieldId(int id) {
        this.id = id;
    }

}
