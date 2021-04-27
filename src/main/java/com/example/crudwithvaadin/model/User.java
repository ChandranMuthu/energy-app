package com.example.crudwithvaadin.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    private  String firstName;
    @NotNull
    private  String lastName;
    @NotNull
    private  String userName;
    @NotNull
    private  String password;
    @NotNull
    private  String emailId;
    @NotNull
    private  Role role;
    private LocalDateTime createdAt;

    public User() {
    }

    public User(String firstName, String lastName, String userName, String password, String emailId, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.emailId = emailId;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailId() {
        return emailId;
    }

    public Role getRole() {
        return role;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("emailId", emailId)
                .append("userName", userName)
                .append("role", role)
                .toString();
    }
}
