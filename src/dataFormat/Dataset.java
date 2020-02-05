package dataFormat;

import java.io.*;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.stream.Collectors;

public class Dataset extends ArrayList<Data> implements Serializable {
    public Attributes attributes;
    public int numAttributes;

    public Dataset(String filename) throws IOException {
        initialize(filename);
    }

    //for deepcopy
    public Dataset(Dataset dataset) {
        dataset.attributes.forEach(x -> this.attributes.add(x));
        dataset.forEach(x -> this.add(x));
        this.numAttributes = attributes.size();

    }

    public Dataset(ArrayList<String> attributes) {
        attributes.forEach(x -> this.attributes.add(x));
        this.numAttributes = attributes.size();
    }

    public Dataset InterquartileRange(double OF, double EVF) {
        return null;
    }


    public List<String> getColumnValuesList(int num) {
        return this.stream().map(d -> d.get(num)).collect(Collectors.toList());
    }

    public void saveFile(String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName + ".csv");
        BufferedWriter bw = new BufferedWriter(fw);
        for (String s : attributes) {
            bw.write(s);
            bw.write(",");
        }
        bw.write("\n");
        for (Data d : this) {
            for (String v : d) {
                bw.write(v);
                bw.write(",");
            }
            bw.write("\n");
        }
        bw.close();
        fw.close();
    }

    public Dataset clone() {
        return new Dataset(this);
    }


    private void initialize(String filename) throws IOException {
        FileInputStream input = new FileInputStream(new File(filename));
        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
        decoder.onMalformedInput(CodingErrorAction.IGNORE);
        InputStreamReader reader = new InputStreamReader(input, decoder);
        BufferedReader bf = new BufferedReader(reader);
        String[] tempAttri = bf.readLine().split(",");
        this.attributes = new Attributes(tempAttri);
        this.numAttributes = attributes.size();
        String strLine;
        while ((strLine = bf.readLine()) != null) {

            String[] tempArray = strLine.split(",");
            this.add(new Data(this.attributes, tempArray));


        }
    }
}