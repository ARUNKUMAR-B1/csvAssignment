package com.csvassignmentbysir.csvassignment.entity;

import jakarta.persistence.*;

@Entity
@Table(name="members_table")
public class EmbeddedMemberClass {

    @EmbeddedId
    private MemberClass id;
    @Column(name="Records#")
    private String siNo;
    @Column(name="Education")
    private String education;
    @Column(name="House#")
    private String house;
    @Column(name="Address1")
    private String address1;
    @Column(name="Address2")
    private String address2;
    @Column(name="City")
    private String city;
    @Column(name="Pincode")
    private String pincode;
    @Column(name="Mobile#")
    private String mobile;
    @Column(name="Company")
    private String company;
    @Column(name="MonthlySalary")
    private String monthlysalary;

    public MemberClass getId() {
        return id;
    }

    public void setId(MemberClass id) {
        this.id = id;
    }

    public String getSiNo() {
        return siNo;
    }

    public void setSiNo(String siNo) {
        this.siNo = siNo;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMonthlysalary() {
        return monthlysalary;
    }

    public void setMonthlysalary(String monthlysalary) {
        this.monthlysalary = monthlysalary;
    }
}
