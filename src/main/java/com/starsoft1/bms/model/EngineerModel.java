package com.starsoft1.bms.model;

public class EngineerModel {
    private int engineer_id;
    private int user_id;
    private String engineer_name;
    private String engineer_companyName;

    public int getEngineer_id() {
        return engineer_id;
    }

    public void setEngineer_id(int engineer_id) {
        this.engineer_id = engineer_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEngineer_name() {
        return engineer_name;
    }

    public void setEngineer_name(String engineer_name) {
        this.engineer_name = engineer_name;
    }

    public String getEngineer_companyName() {
        return engineer_companyName;
    }

    public void setEngineer_companyName(String engineer_companyName) {
        this.engineer_companyName = engineer_companyName;
    }
}
