package ru.spirina.t_automation.sql;

import java.sql.Date;

public class PersonDO {

    private Integer id;
    private String surname;
    private String name;
    private String middlename;
    private Date birthday;
    private String gender;
    private String inn;
    private Integer address_id;

    public PersonDO() {
        // empty constructor for BeanListHandler
    }

    public PersonDO(Integer id, String surname, String name, String middlename, Date birthday, String gender, String inn, Integer address_id) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.middlename = middlename;
        this.birthday = birthday;
        this.gender = gender;
        this.inn = inn;
        this.address_id = address_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }
}

