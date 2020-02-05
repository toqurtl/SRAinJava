package mcs;

import calendar.CalendarConfig;
import calendar.ScheduleCalendar;
import schedule.Activity;
import schedule.Schedule;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Ganttchart {
    String standardDate;
    ScheduleCalendar cal;
    MCSResult result;
    Schedule schedule;
    LinkedHashMap<String, gActivity> activityMap = new LinkedHashMap<>();
    ArrayList<gActivity> activityList = new ArrayList<>();
    public Ganttchart(String standardDate, Schedule schedule, MCSResult result){
        this.standardDate = standardDate;
        this.schedule = schedule;
        this.result = result;
        this.cal = new ScheduleCalendar(standardDate, CalendarConfig.Workday.FIVE);
        for(String s : result.activityIDs){
            Activity a = schedule.getActivity(s);
            gActivity ga = new gActivity(a);
            activityMap.put(a.activityID, ga);
            activityList.add(ga);
        }
    }

    public class gActivity{

        String id;
        String name;
        String wbsSeq;
        String actSeq;
        int plannedDuration;
        String plannedStartDate;
        String plannedFinishDate;
        double analyzedDuration;
        String analyzedStartDate;
        String analyzedFinishDate;

        public gActivity(Activity a){
            int tempPSD = a.plannedcpDate[0];
            int tempPFD = a.plannedcpDate[1];
            int tempASD = result.getStartDateMean(a.activityID);
            int tempAFD = result.getFinishDateMean(a.activityID);

            this.id = a.activityID;
            this.name = a.activityName;
            this.wbsSeq = a.wbsSeq;
            this.actSeq = a.actSeq;
            this.plannedDuration = a.plannedDuration;
            this.plannedStartDate = cal.get7weekDate(tempPSD);
            this.analyzedDuration = result.getDurationMean(a.activityID);
            this.analyzedStartDate = cal.get7weekDate(tempASD);
            if(tempPSD>tempPFD){
                this.plannedFinishDate = this.plannedStartDate;
            }else{
                this.plannedFinishDate = cal.get7weekDate(tempPFD);
            }
            if(tempASD>tempAFD){
                this.analyzedFinishDate = this.analyzedStartDate;
            }else{
                this.analyzedFinishDate = cal.get7weekDate(tempAFD);
            }

        }

        public String getCSV(){
            String rgx = ",";
            return this.id+rgx+this.name+rgx+this.wbsSeq+rgx+this.actSeq+rgx+this.plannedDuration+rgx+this.plannedStartDate+rgx+this.plannedFinishDate+rgx+this.analyzedDuration+rgx+this.analyzedStartDate+rgx+this.analyzedFinishDate+"\n";
        }
    }
}
