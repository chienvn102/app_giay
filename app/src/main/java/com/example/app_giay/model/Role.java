package com.example.app_giay.model;

public class Role {
    private int id;
    private String roleName;

    // Constructor mặc định
    public Role() {
    }

    // Constructor đầy đủ
    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    // Getter và Setter cho các thuộc tính
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    // Phương thức toString để in thông tin Role
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
