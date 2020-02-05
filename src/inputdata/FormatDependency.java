package inputdata;

import dataFormat.Attributes;
import dataFormat.Data;
import dataFormat.Dataset;
import main.Parameter;

import java.util.ArrayList;

public class FormatDependency {
    public String preID;
    public String sucID;
    public String relationship;
    public int lagtime;

    public FormatDependency(Data d){
        isFitted(d.attributes);
        this.preID = d.getValue(Parameter.dependencyAttributes.get(0));
        this.sucID = d.getValue(Parameter.dependencyAttributes.get(1));
        this.relationship = d.getValue(Parameter.dependencyAttributes.get(2));
        try{
            this.lagtime = Integer.parseInt(d.getValue(Parameter.dependencyAttributes.get(3)));
        }catch(NumberFormatException e){
            e.printStackTrace();
            System.out.println("FormatDependency : lagtime of "+this.preID+", "+this.sucID+" is not integer -"+d.getValue(Parameter.dependencyAttributes.get(3)));

        }

    }

    public void print(){
        System.out.println(preID+" "+sucID+" "+relationship+" "+lagtime);
    }

    public static void isFitted(Attributes attri) {
        if (attri.size() != Parameter.dependencyAttributes.size()) {
            System.out.println("dependency file require "+Parameter.dependencyAttributes.size()+" attributes");
            attri.print();
            System.exit(1);
        } else {
            ArrayList<String> test = Parameter.dependencyAttributes;
            for (String s : test) {
                if (!attri.contains(s)) {
                    System.out.println("dependency file require attribute " + s);
                }
            }
        }
    }

}
