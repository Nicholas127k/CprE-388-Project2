package com.example.utilities;

import java.util.ArrayList;
import java.util.List;

public class ClassSectionTimeRange {

    private List<ClassSectionDayEnum> days;
    private String startTime;
    private String endTime;

    public ClassSectionTimeRange(){
        this.days = new ArrayList<>();
        this.startTime = "00:00";
        this.endTime = "23:59";
    }

    public ClassSectionTimeRange(List<ClassSectionDayEnum> days, String startTime, String endTime){
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public List<ClassSectionDayEnum> getDays(){
        return this.days;
    }

    public String getStartTime(){
        return this.startTime;
    }

    public String getEndTime(){
        return this.endTime;
    }

    public void setDays(List<ClassSectionDayEnum> days){
        this.days = days;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    /**
     *
     * The isConflict function is used check if another class would be a conflict
     *
     * @param classSectionTimeRange The class you want to compare times with
     * @return true - there is a conflict, false - there isn't conflict
     */
    public boolean isConflict(ClassSectionTimeRange classSectionTimeRange){
        boolean overlappingDays = false;
        for(int i = 0; i < this.days.size(); ++i){
            for(int j = 0; j < classSectionTimeRange.getDays().size(); ++j){
                if(this.days.get(i) == classSectionTimeRange.getDays().get(j)){
                    overlappingDays = true;
                    break;
                }
            }
            if(overlappingDays){
                break;
            }
        }

        if(!overlappingDays){
            return false;
        }

        int instanceStartTimeHour = Integer.parseInt( this.startTime.split(":")[0] );
        int instanceStartTimeMinute = Integer.parseInt( this.startTime.split(":")[1] );

        int instanceEndTimeHour = Integer.parseInt( this.endTime.split(":")[0] );
        int instanceEndTimeMinute = Integer.parseInt( this.endTime.split(":")[1] );

        int compareStartTimeHour = Integer.parseInt( classSectionTimeRange.getStartTime().split(":")[0] );
        int compareStartTimeMinute = Integer.parseInt( classSectionTimeRange.getStartTime().split(":")[1] );

        int compareEndTimeHour = Integer.parseInt( classSectionTimeRange.getEndTime().split(":")[0] );
        int compareEndTimeMinute = Integer.parseInt( classSectionTimeRange.getEndTime().split(":")[1] );

        boolean instanceStartGreaterThanCompareStart = instanceStartTimeHour >= compareStartTimeHour && instanceStartTimeMinute >= compareStartTimeMinute;
        boolean instanceStartLessThanCompareEnd = instanceStartTimeHour <= compareEndTimeHour && instanceStartTimeMinute <= compareEndTimeMinute;

        boolean instanceEndGreaterThanCompareStart = instanceEndTimeHour >= compareStartTimeHour && instanceEndTimeMinute >= compareStartTimeMinute;
        boolean instanceEndLessThanCompareEnd = instanceEndTimeHour <= compareEndTimeHour && instanceEndTimeMinute <= compareEndTimeMinute;

        if(instanceStartGreaterThanCompareStart || instanceStartLessThanCompareEnd || instanceEndGreaterThanCompareStart || instanceEndLessThanCompareEnd){
            return true;
        }
        return false;
    }
}
