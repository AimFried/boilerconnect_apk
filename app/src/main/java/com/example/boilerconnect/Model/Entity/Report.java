package com.example.boilerconnect.Model.Entity;

public class Report {

    private int id;

    private String name;
    private String surname;
    private String address;

    private String brand;
    private String boiler;
    private String dateEntryService;
    private String dateIntervention;

    private String serialNumber;

    private String description;

    private String duration;


    public Report(int id, String name, String surname, String address, String brand,
                  String boiler, String dateEntryService, String dateIntervention,
                  String serialNumber, String description, String duration) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.brand = brand;
        this.boiler = boiler;
        this.dateEntryService = dateEntryService;
        this.dateIntervention = dateIntervention;
        this.serialNumber = serialNumber;
        this.description = description;
        this.duration = duration;
    }

    public Report(int id, String name) {
        this.id = id;
        this.name = name;
        this.surname = "";
        this.address = "";
        this.brand = "";
        this.boiler = "";
        this.dateEntryService = "";
        this.dateIntervention = "";
        this.serialNumber = "";
        this.description = "";
        this.duration = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBoiler() {
        return boiler;
    }

    public void setBoiler(String boiler) {
        this.boiler = boiler;
    }

    public String getDateEntryService() {
        return dateEntryService;
    }

    public void setDateEntryService(String dateEntryService) {
        this.dateEntryService = dateEntryService;
    }

    public String getDateIntervention() {
        return dateIntervention;
    }

    public void setDateIntervention(String dateIntervention) {
        this.dateIntervention = dateIntervention;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
