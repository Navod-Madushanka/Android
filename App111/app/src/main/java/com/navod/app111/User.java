package com.navod.app111;

import java.util.ArrayList;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String mobile;
    private String company;
    public User() {
    }
    public User(int id, String firstName, String lastName, String mobile, String company) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public  String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCompany() {
        return company;
    }

    public static ArrayList getSampleUserList(){
        ArrayList<User> sampleUserList = new ArrayList();
        sampleUserList.add(new User(1, "Hasitha", "Navod", "+94 77 296 7997", "Google"));
        sampleUserList.add(new User(2, "Navod", "Madushanka", "+94 77 296 7997", "Amazon"));
        sampleUserList.add(new User(3, "Madushanka", "Maithripala", "+94 77 296 7997", "Microsoft"));
        sampleUserList.add(new User(4, "Maithripala", "Hasitha", "+94 77 296 7997", "Apple"));
        sampleUserList.add(new User(5, "batman", "DarkNight", "+94 77 296 7997", "Gothom"));
        return sampleUserList;
    }
}
