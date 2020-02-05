package mcs;

import inputdata.ActualData;
import main.Parameter;
import schedule.Activity;
import schedule.Networking;
import schedule.Schedule;

public class MCSimulation {

    public static MCSResult simulation(Schedule schedule, ActualData actualData, int numSimulation){
        setting(schedule, actualData);
        MCSResult result = new MCSResult();
        for(int i=0;i< numSimulation;i++){
            System.out.println(i+"th simulation");
            for(Activity act : schedule){
                if(actualData.keySet().contains(act.activityID))
                    act.actual.setActualData(act);
            }

            Networking.networking(schedule);
            result.add(new ScheduleResult(schedule));
        }
        result.initialize(schedule);
        return result;
    }

    private static void setting(Schedule schedule, ActualData actualData){

        for(Activity a : schedule){
            if(actualData.keySet().contains(a.activityID)){
                a.actual.set(actualData.get(a.activityID));
                a.actual.datatype = actualData.type.get(a.activityID);
            }

        }
    }


}
