package com.example.n4u1.test130.models;

public class User {

    private String deviceName, sex, job, uid, email;
    private int age;

    public String getEmail() {
        return email;
    }

    public User () {

    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User(String deviceName, String sex, String job, String uid, String email, int age) {
        this.deviceName = deviceName;
        this.sex = sex;
        this.job = job;
        this.uid = uid;
        this.email = email;
        this.age = age;
    }
}
