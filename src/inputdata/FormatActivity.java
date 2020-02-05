package inputdata;

import dataFormat.Attributes;
import dataFormat.Data;
import dataFormat.Dataset;
import log.SRALogger;
import main.Parameter;

import java.util.ArrayList;

public class FormatActivity{
    public String wbsSeq;
    public String actSeq;
    public String actID;
    public String actName;
    public int plannedDuration;
    public double plannedCost;
    public double quantity;

    public FormatActivity(Data d){
        isFitted(d.attributes);
        this.actID = d.getValue(Parameter.pActivityAttributes.get(0));
        this.actName = d.getValue(Parameter.pActivityAttributes.get(1));
        this.wbsSeq = d.getValue(Parameter.pActivityAttributes.get(5));
        this.actSeq = d.getValue(Parameter.pActivityAttributes.get(6));
        try{
            this.plannedDuration = Integer.parseInt(d.getValue(Parameter.pActivityAttributes.get(2)));
            this.plannedCost = Double.parseDouble(d.getValue(Parameter.pActivityAttributes.get(3)));
            this.quantity = Double.parseDouble(d.getValue(Parameter.pActivityAttributes.get(4)));

        }catch(NumberFormatException e){
            e.printStackTrace();
            SRALogger.Logger("inputdata", "FormatActivity", "FormatActivity");
            System.out.println("FormatActivity : "+this.actID+" value is not integer or double - "+d.getValue(Parameter.pActivityAttributes.get(2))+" "+d.getValue(Parameter.pActivityAttributes.get(3))+d.getValue(Parameter.pActivityAttributes.get(4)));
        }

    }

    public void print(){
        System.out.println(actID+" "+plannedDuration+" "+plannedCost+" "+quantity);
    }

    public static void isFitted(Attributes attri){
        if(attri.size() != Parameter.pActivityAttributes.size()){
            SRALogger.Logger("inputdata", "FormatActivity", "isFitted");
            System.out.println("activity file require "+Parameter.pActivityAttributes.size()+" number of attributes");
            attri.print();
        }else{
            ArrayList<String> test = Parameter.pActivityAttributes;
            for(String s : test){
                if(!attri.contains(s)){
                    System.out.println(s.hashCode());
                    System.out.println(attri.get(0).hashCode());
                    System.out.println(attri.get(0).equals(s));
                    System.out.println("activity file require attribute "+s);
                }
            }
        }


    }





}
