package dataFormat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;

public class Util {


    public static Object getArrayListMember(int i, ArrayList list){
        return list.get(i);
    }

    public static void saveFile(String filename, Object obj) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.close();
    }

    public static void inifiniteCheck(int num){
        if(num>50000){
            System.out.println("nextGeneration Error");
            System.exit(1);
        }
    }

    public static void printDoubleArray(double[] arr){
        for(int i=0;i<arr.length;i++){
            print(arr[i]+", ");
        }
    }

    public static void printDoubleArray(double[] arr, int k){
        for(int i=0;i<arr.length;i++){
            print(pointPrint(arr[i], k)+", ");
        }
    }

    public static ArrayList deepCopy(ArrayList list) {
        ArrayList temp = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            temp.add(list.get(0));
        }
        return temp;
    }
    public static ArrayList<String> arrToListStr(String[] arr){
        ArrayList<String> temp = new ArrayList<String>();
        for(String s : arr)
            temp.add(s);
        return temp;
    }
    public static double[] listToArrayDouble(ArrayList<Double> list){
        double[] tempArray = new double[list.size()];
        for(int i=0;i<tempArray.length;i++)
            tempArray[i] = list.get(i);

        return tempArray;
    }

    public static double pointPrint(double value, int k) {
        return Math.round(value*Math.pow(10, k))/(Math.pow(10, k)*1.0);
    }

    private static void println(String str){System.out.println(str);}
    private static void print(String str){System.out.print(str);}
    public static void printList(List<Double> list){
        for(int i=0;i<list.size();i++){
            System.out.print(pointPrint(list.get(i), 4)+" ");
        }
    }

    public static void println(Object obj){
        System.out.println(obj);
    }
    public static void print(Object obj){
        System.out.print(obj);
    }
    public static void space(){
        System.out.println();
    }


}
