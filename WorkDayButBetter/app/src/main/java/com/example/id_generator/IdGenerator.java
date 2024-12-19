package com.example.id_generator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
/**
 *generates id maybe i dont why red lines?
 */
public class IdGenerator {

    private static Random randomGenerator = null;

    private static int MAX_ID = Integer.MAX_VALUE;
    /**
     *creates id
     * @return id returns random id
     */
    public static int generateId(){
        return generateId(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000);
    }
    /**
     *generates random id
     * @param seed
     * @return randomly generated id
     */
    public static int generateId(long seed){
        if(randomGenerator == null){
            randomGenerator = new Random();
        }

        return randomGenerator.nextInt(MAX_ID);
    }

}
