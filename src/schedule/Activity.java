package schedule;

import inputdata.FormatActivity;
import main.Parameter;
import main.Utils;
import org.apache.commons.math3.random.EmpiricalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.*;

public class Activity {
    public String wbsSeq;
    public String actSeq;
    public String activityID;
    public String activityName;
    public int plannedDuration;
    public double plannedCost;
    public int[] plannedcpDate = new int[4];
    public int[] plannedfloatValue = new int[2];
    public double quantity;
    public int actualDuration;
    public double actualCost;

    public double costperDay;
    public double productivity;
    public Random rand = new Random(0);

    public HashMap<String, Dependency> sucInfo = new HashMap<>();
    HashMap<String, Dependency> preInfo = new HashMap<>();
    HashSet<Activity> sucActs = new HashSet<>();
    HashSet<Activity> preActs = new HashSet<>();

    public networkInfo net = new networkInfo();
    public actualInfo actual = new actualInfo();

    public LinkedHashMap<Integer, Double> timeseriesCost = new LinkedHashMap();

    public Activity(String id, String name){
        this.activityID = id;
        this.activityName = name;
    }


    public Activity(FormatActivity act){
        this.activityID = act.actID;
        this.activityName = act.actName;
        this.plannedCost = act.plannedCost;
        this.actualCost = act.plannedCost;
        this.plannedDuration = act.plannedDuration;
        this.actualDuration = act.plannedDuration;
        this.quantity = act.quantity;
        this.wbsSeq = act.wbsSeq;
        this.actSeq = act.actSeq;
    }

    public void setDependency(Dependency dep, Schedule schedule){
        System.out.println(dep.preID+" "+dep.sucID+" "+this.activityID);
        if(this.activityID.equals(dep.preID)){
            sucInfo.put(dep.sucID, dep);
            sucActs.add(schedule.getActivity(dep.sucID));
        }else if(this.activityID.equals(dep.sucID)){
            preInfo.put(dep.preID, dep);
            preActs.add(schedule.getActivity(dep.preID));
        }
    }

    public void printActivity(){
        System.out.println(activityID+" "+plannedDuration+" "+plannedCost+" "+plannedcpDate[0]+" "+plannedcpDate[1]+" "+plannedcpDate[2]+" "+plannedcpDate[3]+" "+plannedfloatValue[0]+" "+preActs.size()+" "+sucActs.size());
    }
    public void printActual(){
        System.out.println(activityID+" "+actualDuration+" "+actualCost+" "+net.cpDate[0]+" "+net.cpDate[1]+" "+net.cpDate[2]+" "+net.cpDate[3]+" "+net.floatValue[0]+" "+preActs.size()+" "+sucActs.size());
    }

    public boolean isTime(int time){
        return net.cpDate[0]<time && net.cpDate[1]>time;
    }


    public class networkInfo{
        public int[] cpDate = new int[4];
        public int[] floatValue = new int[2];
        HashSet<Integer> ESInfo = new HashSet<>();
        HashSet<Integer> LFInfo = new HashSet<>();
        int backcalculateCheck=0;
        int forwardcalculateCheck=0;
        boolean startActivityCheck = false;
        boolean finishActivityCheck = false;
        boolean forwardcalculationFinished = false;
        boolean backcalculationFinished = false;

        public networkInfo(){}

        public void reset(){
            cpDate[0] = -100;
            cpDate[1] = -100;
            cpDate[2] = -100;
            cpDate[3] = -100;
            floatValue[0] = -100;
            floatValue[1] = -100;
            ESInfo.clear();
            LFInfo.clear();
            forwardcalculateCheck = 0;
            backcalculateCheck = 0;
            startActivityCheck = false;
            finishActivityCheck = false;
            forwardcalculationFinished = false;
            backcalculationFinished = false;
        }
    }

    public class actualInfo extends EmpiricalDistribution{
        public Parameter.dataType datatype;

        public actualInfo(){

        }

        public void set(ArrayList data){
            this.load(Utils.ListToDoubleArray(data));
        }

        public void set(double[] data){
            this.load(data);
        }


        public void set(int[] data){
            this.load(Utils.doubleToIntArray(data));
        }

        public void resetDuration(){
            double[] d = {0.0};
            this.load(d);
        }

        public double getValue(){
            return this.inverseCumulativeProbability(rand.nextDouble());
        }

        public void setActualData(Activity a){

                if(datatype.equals(Parameter.dataType.cost)){
                    a.actualCost =  Utils.doubleToint(this.getValue());
                    a.costperDay = a.actualCost/a.actualDuration;
                }else if(datatype.equals(Parameter.dataType.duration)){
                    int tempDuration = Utils.doubleToint(this.getValue());
                    int limitDuration = Utils.doubleToint(Parameter.actualDuLimit * a.plannedDuration);
                    if(tempDuration < limitDuration){
                        a.actualDuration = tempDuration;
                    }else{
                        a.actualDuration = limitDuration;
                    }
                }else if(datatype.equals(Parameter.dataType.productivity)){
                    a.productivity = this.getValue();
                    int tempDuration = Utils.doubleToint(a.quantity/a.productivity);
                    int limitDuration = Utils.doubleToint(Parameter.actualDuLimit * a.plannedDuration);
                    if(tempDuration<limitDuration){
                        a.actualDuration = tempDuration;
                    }else{
                        a.actualDuration = limitDuration;
                    }
                }else if(datatype.equals(Parameter.dataType.costperquantity)){
                    a.actualCost =  Utils.doubleToint(this.getValue()*a.quantity);
                    a.costperDay = a.actualCost/a.actualDuration;
                }else if(datatype.equals(Parameter.dataType.rcost)){
                    a.actualCost =  Utils.doubleToint(this.getValue())+a.plannedCost;
                    a.costperDay = a.actualCost/a.actualDuration;
                }else if(datatype.equals(Parameter.dataType.rduration)) {
                    int tempDuration = Utils.doubleToint(this.getValue())+a.plannedDuration;
                    int limitDuration = Utils.doubleToint(Parameter.actualDuLimit * a.plannedDuration);
                    if (tempDuration < limitDuration) {
                        a.actualDuration = tempDuration;
                    } else {
                        a.actualDuration = limitDuration;
                    }
                }else if(datatype.equals(Parameter.dataType.rproductivity)){
                    a.productivity = this.getValue();
                    int tempDuration = Utils.doubleToint(a.quantity/a.productivity)+a.plannedDuration;
                    int limitDuration = Utils.doubleToint(Parameter.actualDuLimit * a.plannedDuration);
                    if(tempDuration<limitDuration){
                        a.actualDuration = tempDuration;
                    }else{
                        a.actualDuration = limitDuration;
                    }
                }else if(datatype.equals(Parameter.dataType.rcostperquantity)){
                    a.actualCost =  Utils.doubleToint(this.getValue()*a.quantity)+a.plannedCost;
                    a.costperDay = a.actualCost/a.actualDuration;
                }

        }
    }
}

