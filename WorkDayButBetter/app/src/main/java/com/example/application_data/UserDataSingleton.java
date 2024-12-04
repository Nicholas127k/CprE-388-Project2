package com.example.application_data;

import com.example.data_classes.User;

public class UserDataSingleton {

    private static User user = null;

    private UserDataSingleton(){}

    public static User getInstance(){
        if(user == null){
            user = new User();
        }

        return user;
    }

    public static User setInstance(User userData){
        user = userData;
        return user;
    }

}
