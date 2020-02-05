package main;

import java.util.ArrayList;
import java.util.Iterator;

public class Utils {
    public static double[] ListToDoubleArray(ArrayList set){
        int size = set.size();
        double[] tempArray = new double[size];
        Iterator iter = set.iterator();
        int i=0;
        while(iter.hasNext()){
            Object o =  iter.next();
            if(o instanceof Integer) {

                    tempArray[i] = (int) o;

            }else if(o instanceof Double){

                    tempArray[i] = (double) o;
            }
            i++;
        }

        return tempArray;
    }

    public static int doubleToint(double d){
        return (int) Math.round(d);
    }

    public static double[] doubleToIntArray(int[] d){
        int size = d.length;
        double[] tempArray = new double[size];
        for(int i=0;i<size;i++){
            tempArray[i] = doubleToint(d[i]);
        }
        return tempArray;
    }

    public static ArrayList<String> arrToList(String[] strarr){
        ArrayList<String> tempArray = new ArrayList<>();
        for(int i=0;i<strarr.length;i++)
            tempArray.add(strarr[i]);

        return tempArray;
    }
}
