package schedule;

import inputdata.FormatActivity;
import inputdata.FormatDependency;
import inputdata.ScheduleData;
import main.Parameter;

import java.util.HashSet;
import java.util.LinkedHashMap;

public class BuildSchedule {
    public static Schedule build(ScheduleData sData){
        Schedule schedule = new Schedule();
        setActivities(schedule, sData);
        setDependencies(schedule, sData);
        Networking.plannedNetworking(schedule);
        return schedule;
    }

    private static void setActivities(Schedule schedule, ScheduleData sData){
        LinkedHashMap<String, FormatActivity> activityInfo = sData.activityInfo;
        for(String s : activityInfo.keySet())
            schedule.add(new Activity(activityInfo.get(s)));
        for(Activity a : schedule)
            schedule.activityMap.put(a.activityID, a);

    }

    private static void setDependencies(Schedule schedule, ScheduleData sData){
        HashSet<FormatDependency> dependentSet = sData.dependencyInfo;
        for(FormatDependency d : dependentSet) {
            Dependency dep = new Dependency(d);
            Activity preact = schedule.getActivity(d.preID);
            Activity sucact = schedule.getActivity(d.sucID);
            preact.sucActs.add(sucact);
            preact.sucInfo.put(sucact.activityID, dep);
            sucact.preActs.add(preact);
            sucact.preInfo.put(preact.activityID, dep);
        }
        setStartFinish(schedule);
    }

    private static void setStartFinish(Schedule schedule){
        Activity sAct = schedule.getActivity(Parameter.startActID);
        Activity fAct = schedule.getActivity(Parameter.finishActID);
        for(Activity a : schedule){
            if(!(a.activityID.equals(Parameter.startActID) || a.activityID.equals(Parameter.finishActID))){
                if(a.preInfo.size()==0){
                    Dependency d = new Dependency(Parameter.startActID, a.activityID, "FS", 0);
                    a.preInfo.put(sAct.activityID, d);
                    a.preActs.add(sAct);
                    sAct.sucInfo.put(a.activityID, d);
                    sAct.sucActs.add(a);
                }
                if(a.sucInfo.size()==0){
                    Dependency d = new Dependency(a.activityID ,Parameter.finishActID, "FS", 0);
                    a.sucInfo.put(fAct.activityID, d);
                    a.sucActs.add(fAct);
                    fAct.preInfo.put(a.activityID, d);
                    fAct.preActs.add(a);
                }
            }

        }
    }


}
