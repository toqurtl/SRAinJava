package dataFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


public class Data extends ArrayList<String> implements Serializable {
    public Attributes attributes;
    public int numAttributes;


    public Data(Data d) {
        this.attributes = d.attributes;
        d.forEach(x->this.add(x));
        this.numAttributes = attributes.size();
    }

    //emptyData
    public Data(Attributes attri) {
        this.attributes = attri;
    }

    public Data(Attributes attri, String[] data) {
        this.attributes = attri;
        for(int i=0;i<data.length;i++)
            this.add(data[i]);
    }

    public int getIndex(String str){
        return this.attributes.indexOf(str);
    }

    public String getValue(String attr){
        return this.get(this.getIndex(attr));
    }

    public Data clone() {
        return new Data(this);
    }
    public int getNumAttributes(){
        return attributes.size();
    }




}