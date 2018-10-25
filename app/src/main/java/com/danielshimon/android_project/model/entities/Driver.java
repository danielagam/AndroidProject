package com.danielshimon.android_project.model.entities;

public class Driver
{
    long   id;
    long   CreditCardNumber;
    String LastName;
    String FirsName;
    String email;
    String phone;
    //region getter and setter
    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirsName() {
        return FirsName;
    }

    public void setFirsName(String firsName) {
        FirsName = firsName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getCreditCardNumber() {
        return CreditCardNumber;
    }

    public void setCreditCardNumber(long creditCardNumber) {
        CreditCardNumber = creditCardNumber;
    }
    //endregion
}