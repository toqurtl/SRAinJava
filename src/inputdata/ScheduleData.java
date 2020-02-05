package inputdata;

import dataFormat.Dataset;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class ScheduleData {
    public LinkedHashMap<String, FormatActivity> activityInfo = new LinkedHashMap<>();
    public HashSet<FormatDependency> dependencyInfo = new HashSet<>();

    public ScheduleData(String addArray[]){
        if(addArray.length==2){
            setActivityInfo(addArray[0]);
            setDependencyInfo(addArray[1]);
        }else{
            System.out.println("ScheduleData Class - ScheduleData");

        }
    }

    public int getDuration(String ID){
        return activityInfo.get(ID).plannedDuration;
    }

    public double getCost(String ID){
        return activityInfo.get(ID).plannedCost;
    }

    public double getProductivity(String ID){
        return activityInfo.get(ID).plannedDuration/activityInfo.get(ID).quantity;
    }

    public double getCostperQty(String ID){
        return activityInfo.get(ID).plannedDuration/activityInfo.get(ID).quantity;
    }

    public void printActivityInfo(){
        for(String key : activityInfo.keySet())
            activityInfo.get(key).print();
    }

    public void printDependency(){
        for(FormatDependency dep : dependencyInfo)
            dep.print();
    }

    public void setActivityInfo(String activityInfoAdd){

        try{
            Dataset dataset = new Dataset(activityInfoAdd);
            FormatActivity.isFitted(dataset.attributes);
            for(int i=0;i<dataset.size();i++){
                FormatActivity act = new FormatActivity(dataset.get(i));
                this.activityInfo.put(act.actID, act);
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void setDependencyInfo(String dependencyInfoAdd){
        try{
            Dataset dataset = new Dataset(dependencyInfoAdd);
            FormatDependency.isFitted(dataset.attributes);
            for(int i=0;i<dataset.size();i++){
                FormatDependency dep = new FormatDependency(dataset.get(i));
                this.dependencyInfo.add(dep);
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }



}
