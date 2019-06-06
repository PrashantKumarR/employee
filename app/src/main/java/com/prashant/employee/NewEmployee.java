package com.prashant.employee;

import com.google.gson.annotations.SerializedName;

public class NewEmployee {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;

    @SerializedName("salary")
    private double salary;

    @SerializedName("age")
    private int age;

    public NewEmployee(long id, String name, double salary, int age) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

    public NewEmployee(String name, double salary, int age) {
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }
}
