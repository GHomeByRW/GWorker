package com.ghomebyrw.gworker.models;

public class FieldWorker {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public FieldWorker() {}

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "FieldWorker{" +
                "email=" + email +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", phoneNumber=" + phoneNumber +
                "}";
    }
}