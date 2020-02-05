package main;

import java.util.ArrayList;

public class Parameter {
    //for scheduling
    public static String startActID = "Start";
    public static String finishActID = "Finish";
    public static String startActName = "Start Activity";
    public static String finishActName = "Finish Activity";


    //for dataInput
    private static String[] actArr = {"activityID", "activityName", "duration", "quantity", "cost", "wbsSeq", "actSeq"};
    private static String[] depArr = {"predecessorID", "successorID", "relationship", "lagtime"};
    private static String[] actualArr = {"activityID", "value", "qty", "cost", "risk"};
    public static ArrayList<String> pActivityAttributes = Utils.arrToList(actArr);
    public static ArrayList<String> dependencyAttributes = Utils.arrToList(depArr);
    public static ArrayList<String> actualDataAttributes = Utils.arrToList(actualArr);

    //for actual data connection
    public enum dataType{
        cost, duration, productivity, costperquantity, rcost, rduration, rproductivity, rcostperquantity
    }

    public static double actualDuLimit = 3.0;
    public static double actualCoLimit = 3.0;



}
