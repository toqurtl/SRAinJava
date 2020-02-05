package inputdata;

import dataFormat.Attributes;
import dataFormat.Data;
import main.Parameter;

import java.util.ArrayList;
import java.util.HashSet;

public class FormatActualData {
    String activityID;
    boolean isqty;
    boolean iscost;
    boolean isrisk;
    double value;
    Parameter.dataType dataType;

    public FormatActualData(Data d){
        isFitted(d.attributes);
        this.activityID = d.getValue(Parameter.actualDataAttributes.get(0));
        this.isqty = Boolean.valueOf(d.getValue(Parameter.actualDataAttributes.get(2)));
        this.iscost = Boolean.valueOf(d.getValue(Parameter.actualDataAttributes.get(3)));
        this.isrisk = Boolean.valueOf(d.getValue(Parameter.actualDataAttributes.get(4)));

        try{
            this.value = Double.parseDouble(d.getValue(Parameter.actualDataAttributes.get(1)));
        }catch(NumberFormatException e){
            e.printStackTrace();
            System.out.println("FormatActualData : "+this.activityID+" value is not double - "+d.getValue(Parameter.actualDataAttributes.get(2)));
        }

        setDataType();

    }

    public static void isFitted(Attributes attri) {
        // check whether number of attributes is same as predefined value
        if (attri.size() != Parameter.actualDataAttributes.size()) {
            System.out.println("inputdata.FormatActualData.java isFitted-");
            System.out.println("actual file require "+Parameter.actualDataAttributes.size()+" attributes");
            attri.print();
        } else {
            ArrayList<String> test = Parameter.actualDataAttributes;
            for (String s : test) {
                // check whether input file has right attribute
                if (!attri.contains(s)) {
                    System.out.println("actual file require attribute " + s);
                }
            }
        }
    }

    private void setDataType(){
        if(this.isrisk){
            if(this.isqty){
                if(this.iscost){
                    this.dataType = Parameter.dataType.rcostperquantity;
                }else{
                    this.dataType = Parameter.dataType.rproductivity;
                }
            }else{
                if(this.iscost){
                    this.dataType = Parameter.dataType.rcost;
                }else{
                    this.dataType = Parameter.dataType.rduration;
                }
            }
        }else{
            if(this.isqty){
                if(this.iscost){
                    this.dataType = Parameter.dataType.costperquantity;
                }else{
                    this.dataType = Parameter.dataType.productivity;
                }
            }else{
                if(this.iscost){
                    this.dataType = Parameter.dataType.cost;
                }else{
                    this.dataType = Parameter.dataType.duration;
                }
            }
        }

    }

}
