package com.oopclass.packageApp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String receiver_name;
    private String phone_number;
    private String address;
    private Boolean export_status;

    public Package() {
    }

    public Package(String receiver_name, String phone_number, String address) {
        this.receiver_name = receiver_name;
        this.phone_number = phone_number;
        this.address = address;
        this.export_status = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getExport_status() {
        return export_status;
    }

    public void setExport_status(Boolean export_status) {
        this.export_status = export_status;
    }
}