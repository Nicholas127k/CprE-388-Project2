package com.example.application_data;

import com.example.data_classes.Institution;

public class UserInstitutionSingleton {

    private static Institution institution = null;

    private UserInstitutionSingleton() {}

    public static Institution getInstance(){

        if(institution == null){
            institution = new Institution();
        }

        return institution;
    }

    public static Institution setInstance(Institution institutionData){
        institution = institutionData;
        return institution;
    }

}
