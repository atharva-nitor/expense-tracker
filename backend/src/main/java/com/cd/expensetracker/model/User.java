package com.cd.expensetracker.model;
import jakarta.persistence.*;
//import lombok.Data;
//@Data
@Entity
@Table(name = "users")
public class User {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(unique = true, nullable = false)
    private String username;
//    @Column(nullable = false)
    private String password;
//    @Column(nullable = false)
    private String email;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
