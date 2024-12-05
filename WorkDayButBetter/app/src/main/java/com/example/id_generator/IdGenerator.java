package com.example.id_generator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;

public class IdGenerator {

    private static Random randomGenerator = null;

    private static int MAX_ID = Integer.MAX_VALUE;

    public static int generateId(){
        return generateId(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000);
    }

    public static int generateId(long seed){
        if(randomGenerator == null){
            randomGenerator = new Random(seed);
        }

        return randomGenerator.nextInt(MAX_ID);
    }

}
