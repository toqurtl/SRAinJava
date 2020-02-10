package mcs;

import main.Parameter;
import main.User;
import main.Utils;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import schedule.Activity;
import schedule.Schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.LinkedHashMap;
public class MCSResult extends ArrayList<ScheduleResult> {
    public ArrayList<String> activityIDs = new ArrayList<>();
    DescriptiveStatistics durationStat = new DescriptiveStatistics();
    LinkedHashMap<String, DescriptiveStatistics> actDurationStats = new LinkedHashMap<>();
    LinkedHashMap<String, DescriptiveStatistics> actTFStats = new LinkedHashMap<>();
    DescriptiveStatistics costStat = new DescriptiveStatistics();
    LinkedHashMap<Integer, ArrayList<Double>> costDistribution = new LinkedHashMap<>();
    LinkedHashMap<Integer, ArrayList<Double>> costCumulative = new LinkedHashMap<>();

    public MCSResult(){
    }

    public void initialize(Schedule s){
        for(Activity a : s){
            activityIDs.add(a.activityID);
            actDurationStats.put(a.activityID, new DescriptiveStatistics());
            actTFStats.put(a.activityID, new DescriptiveStatistics());
        }
        for(ScheduleResult ss : this){
            durationStat.addValue(Utils.doubleToint(ss.projectDuration));
            costStat.addValue(ss.projectCost);
        }
        for(String ID : activityIDs){
            for(ScheduleResult ss : this){
                actDurationStats.get(ID).addValue(ss.durationMap.get(ID));
                actTFStats.get(ID).addValue(ss.TFMap.get(ID));
            }
        }

        for(ScheduleResult ss : this){
            for(int t : ss.costperTime.keySet()){
                if(!costDistribution.containsKey(t)){
                    ArrayList<Double> temp = new ArrayList<>();
                    ArrayList<Double> temp2 = new ArrayList<>();
                    temp.add(ss.costperTime.get(t));
                    costDistribution.put(t, temp);
                    temp2.add(ss.costperTimeCumulative.get(t));
                    costCumulative.put(t, temp2);
                }else{
                    try{
                        costDistribution.get(t).add(ss.costperTime.get(t));
                        costCumulative.get(t).add(ss.costperTimeCumulative.get(t));
                    }catch(NullPointerException e){
                        e.printStackTrace();
                        System.out.println(t);
                        System.exit(1);
                    }

                }
            }
        }

    }

    public int getStartDateMean(String ID){
        int sum = 0;
        for(ScheduleResult s : this)
            sum+=s.dateMap.get(ID)[0];
        return sum/this.size();
    }

    public int getFinishDateMean(String ID){
        int sum = 0;
        for(ScheduleResult s : this)
            sum+=s.dateMap.get(ID)[1];
        return sum/this.size();
    }

    public double getDurationMean(){
        return durationStat.getMean();
    }

    public double getDurationMin(){
        return durationStat.getMin();
    }

    public double getDurationMax() {
        return durationStat.getMax();
    }

    public double getDurationVariance() {
        return durationStat.getStandardDeviation();
    }

    public double getCostMean(){
        return costStat.getMean();
    }

    public double getCostMin(){
        return costStat.getMin();
    }

    public double getCostMax(){
        return costStat.getMax();
    }

    public double getCostVariance(){
        return costStat.getStandardDeviation();
    }

    public LinkedHashMap<String, Double> getCIs(){
        LinkedHashMap<String, Double> tempMap = new LinkedHashMap<>();
        for(String ID : activityIDs) {
            if(!ID.equals(Parameter.startActID) && !ID.equals(Parameter.finishActID)) {
                tempMap.put(ID, getCI(ID));
            }
        }
        return tempMap;
    }

    public LinkedHashMap<String, Double> getSIs(){
        LinkedHashMap<String, Double> tempMap = new LinkedHashMap<>();
        for(String ID : activityIDs) {
            if(!ID.equals(Parameter.startActID) && !ID.equals(Parameter.finishActID)) {
                tempMap.put(ID, getSI(ID));
            }
        }
        return tempMap;
    }

    public LinkedHashMap<String, Double> getSSIs(){
        LinkedHashMap<String, Double> tempMap = new LinkedHashMap<>();
        for(String ID : activityIDs) {
            if(!ID.equals(Parameter.startActID) && !ID.equals(Parameter.finishActID)) {
                tempMap.put(ID, getSSI(ID));
            }
        }
        return tempMap;
    }

    public LinkedHashMap<String, Double> getCRIs(){
        LinkedHashMap<String, Double> tempMap = new LinkedHashMap<>();
        for(String ID : activityIDs) {
            if(!ID.equals(Parameter.startActID) && !ID.equals(Parameter.finishActID)) {
                tempMap.put(ID, getCRI(ID));
            }
        }
        return tempMap;
    }

    public double getCI(String ID){
        int sum = 0;
        DescriptiveStatistics des = this.actTFStats.get(ID);
        int size = des.getValues().length;
        for(int i=0;i<size;i++){
            double value = des.getElement(i);
            if(value< User.CPRange+0.01){
                sum+=1;
            }
        }
        return sum*1.0/size;
    }

    public double getSI(String ID){
        double sum = 0;
        DescriptiveStatistics des = this.actDurationStats.get(ID);
        int size = des.getValues().length;
        DescriptiveStatistics des2 = this.actTFStats.get(ID);
        for(int i=0;i<size;i++){
            double SL = des2.getElement(i);
            double AD = des.getElement(i);
            double SD = durationStat.getElement(i);
            double check = 0;
            if((AD+SL)!=0){
//                check = (AD*SD)/((AD+SL)*getDurationMean());
                  check = (AD/(AD+SL))*SD/getDurationMean();
            }else{
                check = 0;
            }
            sum+=check;
        }
        return sum/size;
    }

    public double getSSI(String ID){
        if(getDurationVariance() == 0 ){
            return 0;
        }else{
            return actDurationStats.get(ID).getStandardDeviation()/getDurationVariance();
        }

    }

    public double getCRI(String ID){
        PearsonsCorrelation pear = new PearsonsCorrelation();
        double[] actDurationArray = actDurationStats.get(ID).getValues();
        double[] projectDurationArray = durationStat.getValues();
        double d = pear.correlation(actDurationArray, projectDurationArray);
        if(Double.isNaN(d)){
            return 0;
        }else{
            return Math.abs(d);
        }

    }

    public double getDurationMean(String activityID) {
        return actDurationStats.get(activityID).getMean();
    }


    public double getDurationDevi(String activityID) {
        return actDurationStats.get(activityID).getStandardDeviation();
    }

    public double getTFMean(String activityID) {
        return actTFStats.get(activityID).getMean();
    }


    public double getTFDevi(String activityID) {
        return actTFStats.get(activityID).getStandardDeviation();
    }

    public ArrayList<String> getSorting(LinkedHashMap<String, Double> data){
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(data.keySet());

        Collections.sort(list , new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Object v1 = data.get(o1);
                Object v2 = data.get(o2);
                return ((Comparable) v2).compareTo(v1);
            }
        });

        return list;
    }
}
