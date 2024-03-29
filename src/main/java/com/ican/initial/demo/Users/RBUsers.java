package com.ican.initial.demo.Users;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RBUsers {
    private Integer id;
    private String name;
    @NotNull(message = "Surname cannot be null")
    private String surname;
    private String password;
    private Integer isactive;
    private String email;
    private Date datecreated;
    private Date datelastupdated;

    @JsonProperty(value = "id")
    public Integer getid() {
        return id;
    }

    @JsonProperty(value = "id")
    public void setid(Integer id) {
        this.id = id;
    }

    @JsonProperty(value = "name")
    public String getname() {
        return name;
    }

    @JsonProperty(value = "name")
    public void setname(String name) {
        this.name = name;
    }

    @JsonProperty(value = "surname")
    public String getsurname() {
        return surname;
    }

    @JsonProperty(value = "surname")
    public void setsurname(String surname) {
        this.surname = surname;
    }

    @JsonProperty(value = "email")
    public String getemail() {
        return email;
    }

    @JsonProperty(value = "email")
    public void setemail(String email) {
        this.email = email;
    }

    @JsonProperty(value = "password")
    public String getpassword() {
        return password;
    }

    @JsonProperty(value = "password")
    public void setpassword(String password) {
        this.password = password;
    }

    @JsonProperty(value = "isactive")
    public Integer getisactive() {
        return isactive;
    }

    @JsonProperty(value = "isactive")
    public void setisactive(Integer isactive) {
        this.isactive = isactive;
    }

    @JsonProperty(value = "datecreated")
    public Date getdatecreated() {
        return datecreated;
    }

    @JsonProperty(value = "datecreated")
    public void setdatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    @JsonProperty(value = "datelastupdated")
    public Date getdatelastupdated() {
        return datelastupdated;
    }

    @JsonProperty(value = "datelastupdated")
    public void setdatelastupdated(Date datelastupdated) {
        this.datelastupdated = datelastupdated;
    }

}
