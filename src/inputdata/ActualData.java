package inputdata;

import dataFormat.Dataset;
import main.Parameter;
import main.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ActualData extends LinkedHashMap<String, ArrayList> {

    public LinkedHashMap<String, Parameter.dataType> type = new LinkedHashMap<>();

    public ActualData(ScheduleData sData, String add){
        initialize(sData, add);
    }

    //if isqty is true, formatactivity have to get double value.
    //if not, int value
    private void initialize(ScheduleData sData, String add){
        try{
            Dataset dataset = new Dataset(add);
            FormatActualData.isFitted(dataset.attributes);
            for(int i=0;i<dataset.size();i++){
                FormatActualData act = new FormatActualData(dataset.get(i));
                //if instance 'ActualData' already contain activityID, put new historical value in the arraylist of activityID
                if(this.keySet().contains(act.activityID)){
                    if(act.isqty){
                        this.get(act.activityID).add(getQtyValue(sData,act));
                    }else{
                        this.get(act.activityID).add(getNormalValue(sData,act));
                    }
                    //if not, generate new arraylist and put value in the arraylist of activityID
                }else{
                    if(act.isqty){
                        this.type.put(act.activityID, act.dataType);
                        this.put(act.activityID, new ArrayList<Double>());
                        this.get(act.activityID).add(getQtyValue(sData,act));
                    }else{
                        this.type.put(act.activityID, act.dataType);
                        this.put(act.activityID,  new ArrayList<Integer>());
                        this.get(act.activityID).add(getNormalValue(sData,act));
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    private double getQtyValue(ScheduleData sData, FormatActualData act){
        if(act.isrisk){
            if(act.iscost){
                return sData.getCostperQty(act.activityID)+act.value;
            }else{
                return sData.getProductivity(act.activityID)+act.value;
            }
        }else{
            return act.value;
        }
    }

    private double getNormalValue(ScheduleData sData, FormatActualData act){
        if(act.isrisk){
            if(act.iscost){
                return Utils.doubleToint(sData.getCost(act.activityID)+act.value);
            }else{
                return Utils.doubleToint(sData.getDuration(act.activityID)+act.value);
            }
        }else{
            return Utils.doubleToint(act.value);
        }
    }

}
