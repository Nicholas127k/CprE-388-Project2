package com.example.workdaybutbetter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.utilities.ClassSectionDayEnum;
import com.example.utilities.ClassSectionTimeRange;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ClassSectionTimeRangeTest {

    @Test
    public void classSectionTimeRangeConflict0(){
        List<ClassSectionDayEnum> classDays = new ArrayList<>();
        classDays.add(ClassSectionDayEnum.MONDAY);
        classDays.add(ClassSectionDayEnum.WEDNESDAY);
        classDays.add(ClassSectionDayEnum.FRIDAY);

        List<ClassSectionDayEnum> classDays2 = new ArrayList<>();
        classDays2.add(ClassSectionDayEnum.TUESDAY);
        classDays2.add(ClassSectionDayEnum.THURSDAY);

        String classStartTime = "15:20";
        String classEndTime = "16:10";

        ClassSectionTimeRange timeRange1 = new ClassSectionTimeRange(classDays, classStartTime, classEndTime);
        ClassSectionTimeRange timeRange2 = new ClassSectionTimeRange(classDays2, classStartTime, classEndTime);

        boolean isConflict = timeRange1.isConflict(timeRange2);
        assertFalse(isConflict);
    }

    @Test
    public void classSectionTimeRangeConflict1(){
        List<ClassSectionDayEnum> classDays = new ArrayList<>();
        classDays.add(ClassSectionDayEnum.MONDAY);
        classDays.add(ClassSectionDayEnum.WEDNESDAY);
        classDays.add(ClassSectionDayEnum.FRIDAY);

        String classStartTime = "15:20";
        String classEndTime = "16:10";

        ClassSectionTimeRange timeRange1 = new ClassSectionTimeRange(classDays, classStartTime, classEndTime);
        ClassSectionTimeRange timeRange2 = new ClassSectionTimeRange(classDays, classStartTime, classEndTime);

        boolean isConflict = timeRange1.isConflict(timeRange2);
        assertTrue(isConflict);
    }

    @Test
    public void classSectionTimeRangeConflict2(){
        List<ClassSectionDayEnum> classDays = new ArrayList<>();
        classDays.add(ClassSectionDayEnum.MONDAY);
        classDays.add(ClassSectionDayEnum.WEDNESDAY);
        classDays.add(ClassSectionDayEnum.FRIDAY);

        String classStartTime = "15:20";
        String classEndTime = "16:10";

        String classStartTime2 = "15:30";
        String classEndTime2 = "16:20";

        ClassSectionTimeRange timeRange1 = new ClassSectionTimeRange(classDays, classStartTime, classEndTime);
        ClassSectionTimeRange timeRange2 = new ClassSectionTimeRange(classDays, classStartTime2, classEndTime2);

        boolean isConflict = timeRange1.isConflict(timeRange2);
        assertTrue(isConflict);
    }

    @Test
    public void classSectionTimeRangeConflict3(){
        List<ClassSectionDayEnum> classDays = new ArrayList<>();
        classDays.add(ClassSectionDayEnum.MONDAY);
        classDays.add(ClassSectionDayEnum.WEDNESDAY);
        classDays.add(ClassSectionDayEnum.FRIDAY);

        String classStartTime = "15:20";
        String classEndTime = "16:10";

        String classStartTime2 = "14:30";
        String classEndTime2 = "15:30";

        ClassSectionTimeRange timeRange1 = new ClassSectionTimeRange(classDays, classStartTime, classEndTime);
        ClassSectionTimeRange timeRange2 = new ClassSectionTimeRange(classDays, classStartTime2, classEndTime2);

        boolean isConflict = timeRange1.isConflict(timeRange2);
        assertTrue(isConflict);
    }
}
