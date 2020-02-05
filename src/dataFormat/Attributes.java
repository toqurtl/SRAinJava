package dataFormat;

import java.util.ArrayList;

public class Attributes extends ArrayList<String> {

    public Attributes(String[] attributes){
        for(String s : attributes)
            this.add(s);
    }

    public int getIndex(String str){
        return this.indexOf(str);
    }

    public void print(){
        for(String s : this){
            System.out.print(s+" ");
        }
        System.out.println();
    }


}
