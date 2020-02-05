package schedule;

import main.Parameter;
import main.User;

import java.util.ArrayList;
import java.util.Collections;

public class Networking {


    public Networking() {

    }

    public static void plannedNetworking(Schedule schedule){

        networking(schedule);
        schedule.plannedProjectDuration = schedule.actualProjectDuration;
        for(Activity act : schedule){
            act.plannedcpDate = act.net.cpDate.clone();
            act.plannedfloatValue = act.net.floatValue.clone();
        }
    }

    public static void networking(Schedule schedule){
        reset(schedule);
        calculaion(schedule);

        /**
        if(hasLoop(schedule)) {
            System.out.println("Loop Error");
            System.exit(1);
        }
         **/
    }

    private static void reset(Schedule schedule){
        for(Activity act : schedule)
            act.net.reset();
    }
    
    

    private static void forwardcalculation(Schedule s) {
        forwardcalculateFloat(s.activityMap.get(Parameter.startActID));
    }

    private static void backcalculation(Schedule s) {
        backcalculateFloat(s.activityMap.get(Parameter.finishActID));
    }

    private static void calculaion(Schedule s) {

        forwardcalculation(s);
        backcalculation(s);
        s.actualProjectDuration = s.getActivity(Parameter.finishActID).net.cpDate[2];
        for(Activity a : s){
            a.net.floatValue[0] = a.net.cpDate[2]-a.net.cpDate[0];
            if(a.net.floatValue[0]<= User.CPRange)
                s.plannedCriticalPaths.add(a);

        }

    }

    private static void forwardcalculateFloat(Activity act){

        if(act.activityID==Parameter.startActID) {
            act.net.cpDate[0] = 0;
            act.net.cpDate[1] = act.net.cpDate[0]+ act.actualDuration - 1;
            act.net.forwardcalculationFinished = true;
            forwardcalculateTrigger(act);
        }else {
            if(act.net.forwardcalculateCheck == act.preActs.size()) {
                act.net.cpDate[0] = Collections.max(act.net.ESInfo);
                act.net.cpDate[1] = act.net.cpDate[0] + act.actualDuration-1;
                for(int i=act.net.cpDate[0];i<act.net.cpDate[1];i++){
                    act.timeseriesCost.put(i, act.costperDay);
                }
                act.net.forwardcalculationFinished = true;
                if(act.activityID==Parameter.finishActID) {

                }else{
                    forwardcalculateTrigger(act);
                }
            }
        }
    }

    private static void backcalculateFloat(Activity act) {
        if(act.activityID==Parameter.finishActID) {
            act.net.cpDate[3] = act.net.cpDate[1];
            act.net.cpDate[2] = act.net.cpDate[3]-act.actualDuration+1;
            backcalculateTrigger(act);
            act.net.backcalculationFinished = true;
        }else {
            if(act.net.backcalculateCheck == act.sucInfo.size()) {
                act.net.cpDate[3] = Collections.min(act.net.LFInfo);
                act.net.cpDate[2] = act.net.cpDate[3]-act.actualDuration+1;
                act.net.backcalculationFinished = true;
                if(act.activityID==Parameter.startActID) {

                }else {
                    backcalculateTrigger(act);
                }
            }
        }
    }

    private static void forwardcalculateTrigger(Activity act) {
        for(Activity sucact : act.sucActs) {
            sucact.net.forwardcalculateCheck++;
            Dependency dep = act.sucInfo.get(sucact.activityID);
            sucact.net.ESInfo.add(forwardcalculation(dep, act, sucact));
            forwardcalculateFloat(sucact);
        }

    }

    private static void backcalculateTrigger(Activity act) {
        for(Activity preact : act.preActs) {
            preact.net.backcalculateCheck++;

            Dependency dep = act.preInfo.get(preact.activityID);
            preact.net.LFInfo.add(backcalculation(dep, preact, act));
            backcalculateFloat(preact);
        }
    }

    private static int forwardcalculation(Dependency dep, Activity preAct, Activity sucAct){
        int check = 0;
        if(dep.relationship.equals("FS")) {	check = preAct.net.cpDate[1]+dep.lagtime+1;}
        else if(dep.relationship.equals("SS")) {check = preAct.net.cpDate[0]+dep.lagtime;}
        else if(dep.relationship.equals("FF")) {check = preAct.net.cpDate[1]+dep.lagtime-sucAct.actualDuration+1;}
        else {check = preAct.net.cpDate[0]+dep.lagtime-sucAct.actualDuration;}
        return check;
    }


    private static int backcalculation(Dependency dep, Activity preAct, Activity sucAct){
        int check = 0;
        if(dep.relationship.equals("FS")) {	check = sucAct.net.cpDate[2]-dep.lagtime-1;}
        else if(dep.relationship.equals("SS")) {check = sucAct.net.cpDate[2]-dep.lagtime+preAct.actualDuration-1;}
        else if(dep.relationship.equals("FF")) {check = sucAct.net.cpDate[3]-dep.lagtime;}
        else {check = sucAct.net.cpDate[3]-dep.lagtime+preAct.actualDuration;}

        return check;
    }


    /**
     * check hasLoop
     */
    private static boolean hasLoop(Schedule s) {
        boolean check = false;
        for(Activity act : s) {
            if(!act.net.forwardcalculationFinished) {
                check = true;
                System.out.println(act.activityID+" hasloop");
                break;
            }
        }
        return false;
    }

    /**
     * check hasLoop
     */
    private static ArrayList<String> LoopActList(Schedule s){
        ArrayList<String> tempList = new ArrayList<>();
        for(Activity act : s) {
            if(!act.net.forwardcalculationFinished) {
                tempList.add(act.activityID);
            }
        }
        return tempList;
    }

    /**
     * check has loop
     */
    private static boolean hasLoop(Activity act) {
        boolean check = false;
        return check;
    }



}
