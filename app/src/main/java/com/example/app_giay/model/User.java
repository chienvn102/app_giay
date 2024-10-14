package com.example.app_giay.model;

public class User {
    private int id;
    private String username;
    private String password;
    private int roleId;
    private String name;

    // Constructor mặc định
    public User() {
    }

    // Constructor đầy đủ
    public User(int id, String username, String password, int roleId, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.name = name;
    }

    // Getter và Setter cho các thuộc tính
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Phương thức toString để in thông tin User
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                ", name='" + name + '\'' +
                '}';
    }
}

