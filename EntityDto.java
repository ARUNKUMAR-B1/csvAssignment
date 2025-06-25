package com.csvassignmentbysir.csvassignment;

public class EntityDto {
        private String dob;
        private String fname;
        private String gender;
        private String lname;
        private String address1;
        private String address2;
        private String city;
        private String company;
        private String education;
        private String houseNo;
        private String mobile;
        private String monthlySalary;
        private String pincode;
        private String recordNo;

    public EntityDto() {
    }

    public EntityDto(String dob, String fname, String gender, String lname, String address1, String address2, String city, String company, String education, String houseNo, String mobile, String monthlySalary, String pincode, String recordNo) {
        this.dob = dob;
        this.fname = fname;
        this.gender = gender;
        this.lname = lname;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.company = company;
        this.education = education;
        this.houseNo = houseNo;
        this.mobile = mobile;
        this.monthlySalary = monthlySalary;
        this.pincode = pincode;
        this.recordNo = recordNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(String monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }
}
