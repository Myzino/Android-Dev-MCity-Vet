package com.troubleshooters.mcityvet;

import com.google.gson.annotations.SerializedName;

public class Appointment {

    @SerializedName("_id")
    private String id;

    @SerializedName("schedule")
    private String schedule;

    @SerializedName("technicianName")
    private String technicianName;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    @SerializedName("services")
    private String services;

    @SerializedName("address")
    private String address;

    @SerializedName("age")
    private int age;

    @SerializedName("numberOfHeads")
    private int numberOfHeads;

    @SerializedName("landmark")
    private String landmark;



    @SerializedName("patient")
    private String patient;

    @SerializedName("status")
    private String status;

    @SerializedName("archive")
    private String archive;






    public Appointment(String schedule, String firstName, String lastName, String patient, String address, String landmark, String email, String phone, String technicianName, int age, int numberOfHeads, String services) {
        this.schedule = schedule;
        this.technicianName = technicianName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.address = address;
        this.landmark = landmark;
        this.patient = patient;
        this.services = services;
        this.numberOfHeads = numberOfHeads;
        this.status = status;
        this.archive = archive;

    }

    public String getId() {
        return id;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getAge() {
        return age;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public int getNumberOfHeads() {
        return numberOfHeads;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getServices() {
        return services;
    }

    public String getArchive() {
        return archive;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getPatient() {
        return patient;
    }



    public String getSchedule() {
        return schedule;
    }
    public String getStatus() {
        return status;
    }


}