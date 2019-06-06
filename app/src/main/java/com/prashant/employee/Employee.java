package com.prashant.employee;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Employee implements Parcelable {
    @SerializedName("id")
    private long id;
    @SerializedName("employee_name")
    private String name;

    @SerializedName("employee_salary")
    private double salary;

    @SerializedName("age")
    private int age;
    @SerializedName("profile_image")
    private String profileImage;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeDouble(salary);
        dest.writeInt(age);
        dest.writeString(profileImage);
    }

    public Employee(){}
    public Employee(Parcel in){

        id = in.readLong();
        name = in.readString();
        salary = in.readDouble();
        age = in.readInt();
        profileImage = in.readString();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public Employee(long id, String name, double salary, int age) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
    }
}
