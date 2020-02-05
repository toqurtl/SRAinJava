package mcs;

import schedule.Activity;
import schedule.Schedule;

import java.util.LinkedHashMap;

public class ScheduleResult{
    int projectDuration;
    double projectCost;
    LinkedHashMap<String, Integer> durationMap = new LinkedHashMap<>();
    LinkedHashMap<String, Integer> TFMap = new LinkedHashMap<>();
    LinkedHashMap<String, Double> costMap = new LinkedHashMap<>();
    LinkedHashMap<String, int[]> dateMap = new LinkedHashMap<>();
    LinkedHashMap<Integer, Double> costperTime = new LinkedHashMap<>();
    LinkedHashMap<Integer, Double> costperTimeCumulative = new LinkedHashMap<>();

    public ScheduleResult(Schedule s){
        int cost = 0;
        for(Activity a : s){
            durationMap.put(a.activityID, a.actualDuration);
            TFMap.put(a.activityID,a.net.floatValue[0]);
            costMap.put(a.activityID, a.actualCost);
            dateMap.put(a.activityID, a.net.cpDate);
            cost += a.actualCost;
        }

        this.projectCost = cost;
        this.projectDuration = s.actualProjectDuration;

        double sum = 0;
        for(int i=0;i<projectDuration;i++){
            double c = 0;
            for(Activity a : s){
                if(a.isTime(i)){
                    c+=a.actualCost;
                }
                costperTime.put(i, c);
            }
            sum+=c;
            costperTimeCumulative.put(i, sum);
        }


    }
}
