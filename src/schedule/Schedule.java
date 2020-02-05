package schedule;

import main.Parameter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Schedule extends ArrayList<Activity> {
    LinkedHashMap<String, Activity> activityMap = new LinkedHashMap<>();
    HashSet<Activity> plannedCriticalPaths = new HashSet<>();
    public int plannedProjectDuration;

    public int actualProjectDuration;

    public Schedule(){
        Activity startActivity = new Activity(Parameter.startActID, Parameter.startActName);
        Activity finishActivity = new Activity(Parameter.finishActID, Parameter.finishActName);
        this.add(startActivity);
        this.add(finishActivity);
        activityMap.put(startActivity.activityID, startActivity);
        activityMap.put(finishActivity.activityID, finishActivity);

    }

    public Activity getActivity(String ID){
        if(this.activityMap.keySet().contains(ID)){
            return activityMap.get(ID);
        }else{
            //System.out.println(ID);
            //System.exit(1);
            return null;

        }

    }

    public void printSchedule(){
        for(Activity a : this){
            a.printActivity();
        }
    }

    public void printactualSchedule(){
        for(Activity a : this){
            a.printActual();
        }
    }

    public void printCriticalPath(){
        for(Activity a : plannedCriticalPaths){
            a.printActivity();
        }
    }




}
