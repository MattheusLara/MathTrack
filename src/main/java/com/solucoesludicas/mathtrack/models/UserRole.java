package com.solucoesludicas.mathtrack.models;

public enum UserRole {
    ADMIN("admin"),
    USER("user");
    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}