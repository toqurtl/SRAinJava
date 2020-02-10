package mcs;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import calendar.CalendarConfig;
import calendar.ScheduleCalendar;
import main.User;
import schedule.Activity;
import schedule.Dependency;
import schedule.Schedule;

public class MCSResultOutput {
    MCSResult result;
    Schedule schedule;
    String standardDate;
    ScheduleCalendar cal;
    public MCSResultOutput(Schedule schedule, MCSResult result, String standardDate) {
        this.result = result;
        this.schedule = schedule;
        this.standardDate = standardDate;
        this.cal = new ScheduleCalendar(standardDate, CalendarConfig.Workday.FIVE);
    }

    public void generalInfoOutput(String filename){
        try{
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("durationMean,"+result.getDurationMean()+"\n");
            bw.write("durationDevi,"+result.getDurationVariance()+"\n");
            bw.write("durationMax,"+result.getDurationMax()+"\n");
            bw.write("durationMin,"+result.getDurationMin()+"\n");
            bw.write("costMean,"+result.getCostMean()+"\n");
            bw.write("costDevi,"+result.getCostVariance()+"\n");
            bw.write("costMax,"+result.getCostMax()+"\n");
            bw.write("costmin,"+result.getCostMin()+"\n");
            bw.write("duration, cost\n");
            for(int i = 0; i< User.simulationNum; i++){
                bw.write(result.durationStat.getValues()[i]+","+result.costStat.getValues()[i]+"\n");
            }

            bw.close();
            fw.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void activityInfoOutput(String filename){
        try{
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("activityID, wbsSeq, actSeq, durationMean, durationDeviation, TFMean, TFDeviation, CI, SI, SSI, CRI\n");
            for(String s : result.activityIDs) {
                Activity a = schedule.getActivity(s);
                bw.write(s+","+a.wbsSeq+","+a.actSeq+","+result.getDurationMean(s)+","+result.getDurationDevi(s)+","+result.getTFMean(s)+","+result.getTFDevi(s)
                        +","+result.getCI(s)+","+result.getSI(s)+","+result.getSSI(s)+","+result.getCRI(s)+","+a.plannedfloatValue[0]+"\n");
            }

            bw.close();
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void costperTimeOutput(String filename){
        try{
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("time, max,mean,min\n");
            for(int t : result.costCumulative.keySet()) {
                double max = Collections.max(result.costCumulative.get(t));
                double mean = result.costCumulative.get(t).stream().mapToDouble(Double::doubleValue).average().getAsDouble();
                double min = Collections.min(result.costCumulative.get(t));
                bw.write(t+","+max+","+mean+","+min+"\n");
            }

            bw.close();
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void sortedCI(String filename) throws IOException{
        FileWriter fw = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("activityID, CI\n");
        ArrayList<String> sorted = result.getSorting(result.getCIs());
        for(String s : sorted) {
            bw.write(s+","+result.getCI(s)+"\n");
        }
        bw.close();
        fw.close();
    }

    public void sortedSI(String filename) throws IOException{
        FileWriter fw = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("activityID, SI\n");
        ArrayList<String> sorted = result.getSorting(result.getSIs());
        for(String s : sorted) {
            bw.write(s+","+result.getSI(s)+"\n");
        }
        bw.close();
        fw.close();
    }

    public void sortedSSI(String filename) throws IOException{
        FileWriter fw = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("activityID, SSI\n");
        ArrayList<String> sorted = result.getSorting(result.getSSIs());
        for(String s : sorted) {
            bw.write(s+","+result.getSSI(s)+"\n");
        }
        bw.close();
        fw.close();
    }

    public void sortedCRI(String filename) throws IOException{
        FileWriter fw = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("activityID, CRI\n");
        ArrayList<String> sorted = result.getSorting(result.getCRIs());
        for(String s : sorted) {
            bw.write(s+","+result.getCRI(s)+"\n");
        }
        bw.close();
        fw.close();
    }

    public void ganttChartData(String filename){
        try{
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("activityID, activityName, wbsSeq, actSeq, plannedDuration, plannedStartDate, plannedfinishDate, analyzedDuration, analyzedStartDate, analyzedFinishDate\n");
            Ganttchart gchart = new Ganttchart(standardDate, schedule, result);
            for(Ganttchart.gActivity ga : gchart.activityList){
                bw.write(ga.getCSV());
            }
            bw.write("dependency\n");
            bw.write("predecessorID, successorID, relationship, lagtime\n");
            for(String s : result.activityIDs){
                Activity a = schedule.getActivity(s);
                for(String sucID : a.sucInfo.keySet()){
                    Dependency dep = a.sucInfo.get(sucID);
                    bw.write(s+","+sucID+","+dep.relationship+","+dep.lagtime+"\n");
                }
            }
            bw.close();
            fw.close();

        }catch(IOException e){
            e.printStackTrace();

        }
    }


}
