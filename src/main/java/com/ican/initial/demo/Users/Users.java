package com.ican.initial.demo.Users;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import javax.persistence.Column;

@Entity // This tells Hibernate to make a table out of this class
// @Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @NotNull(message = "surname is mandatory")
    private String surname;
    private String password;
    private Integer isactive;
    private String email;
    private Date datecreated;
    private Date datelastupdated;

    public Integer getid() {
        return id;
    }

    public void setid(Integer id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    @Column(nullable = false)
    public void setname(String name) {
        this.name = name;
    }

    public String getsurname() {
        return surname;
    }

    public void setsurname(String surname) {
        this.surname = surname;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public Integer getisactive() {
        return isactive;
    }

    public void setisactive(Integer isactive) {
        this.isactive = isactive;
    }

    public void setdatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public void setdatelastupdated(Date datelastupdated) {
        this.datelastupdated = datelastupdated;
    }

    public Date getdatecreated() {
        return datecreated;
    }

    public Date getdatelastupdated() {
        return datelastupdated;
    }
}