/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.pettrainingsetepp;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;


/**
 *
 * @author Saadeeq
 */
public class FileReader 
{
    BufferedReader br;
    String rootDirectory;
    String subDirectory;
    String JSONObject;
    ArrayList<String> ListOfJSONObjects = new ArrayList(100);
    
    public static boolean isNumeric(String str)
        {
            NumberFormat formatter = NumberFormat.getInstance();
            ParsePosition pos = new ParsePosition(0);
            formatter.parse(str, pos);
            return str.length() == pos.getIndex();
        }
    FileReader(String rootDirectory, String subDirectory) throws FileNotFoundException
    {
        this.rootDirectory = rootDirectory;
        this.subDirectory = subDirectory;
        br = new BufferedReader(new java.io.FileReader(rootDirectory + "\\" + subDirectory));
        
    }
    FileReader(BufferedReader br)
    {
        this.br = br;
    }
    
    void PET() throws IOException
    {
        String line = br.readLine();
        String columns[];
        while(line != null)
        {
            //System.out.println(line);
            columns = line.split("\t");
//            String chromLeft = columns[3].split("\\-")[0].substring(0, 5).replace(":", "");
//            String startLeft = columns[3].split("\\-")[0].split("\\:")[1].split("\\.\\.")[0];
//            String endLeft = columns[3].split("\\-")[0].split("\\:")[1].split("\\.\\.")[1];
//            String chromRight = columns[3].split("\\-")[1].substring(0, 5).replace(":", "");
//            String startRight = columns[3].split("\\-")[1].split("\\:")[1].split("\\.\\.")[0];
//            String endRight = columns[3].split("\\-")[1].split("\\:")[1].split("\\.\\.")[1].split(",")[0];
//            String PET = columns[3].split(",")[1];
            String chromLeft = columns[0];
            String startLeft = columns[1];
            String endLeft = columns[2];
            String chromRight = columns[3];
            String startRight = columns[4];
            String endRight = columns[5];
            String PET = columns[6];
            if(columns[0].contains("chrom"))
            {
                line = br.readLine();
                continue;
            }
            JSONObject = "{" + "\"chromLeft\"" + ":"+ "\"" + chromLeft + "\"" + "," + "\"startLeft\":" + "\"" + startLeft +"\"" + "," + "\"endLeft\":" + "\"" + endLeft +"\"" + "," + "\"chromRight\":" + "\"" + chromRight +"\"" + "," + "\"startRight\":" + "\"" + startRight +"\"" + "," + "\"endRight\":" + "\"" + endRight +"\"" + ","+ "\"PETCount\":" + "\"" + PET +"\""  + "}";
            //System.out.println(JSONObject);
            ListOfJSONObjects.add(JSONObject);
            line = br.readLine();
        }
    }
    void GTF(String feature) throws IOException
    {
        String line = br.readLine();
        String lines[];
        String descriptionLine[];
        String geneId;
        while(line != null)
        {
            lines = line.split("\t");
            if(line.contains("##") || !lines[2].contains(feature))
            {
                line = br.readLine();
                continue;
            }
            lines[0] = lines[0].replace("chr", "");
            descriptionLine = lines[8].split(";");
            geneId = descriptionLine[0].replace("gene_id \"", "");
            geneId = geneId.replace("\"", "");
            geneId = geneId.split("\\.")[0];
            JSONObject = "{" + "\"chr\"" + ":"+ "\"" + lines[0] + "\"" + "," + "\"start\":" + "\"" + lines[3] +"\"" + "," + "\"end\":" + "\"" + lines[4] +"\"" + "," + "\"geneId\":" + "\"" + geneId +"\"" + "}";
            //System.out.println(JSONObject);
            ListOfJSONObjects.add(JSONObject);
            line = br.readLine();
        }
        
    }
    void RNASeq() throws IOException
    {
        String gene = br.readLine();
        String genes[];
        while(gene != null)
        {
            genes = gene.split("\t");
            if(genes[0].contains("gene_id"))
            {
                gene = br.readLine();
                continue;
            }
            //System.out.println(genes[0].split("\\.")[0] + " FPKM" + genes[6] + "  " + gene);
            if(Double.parseDouble(genes[6]) > 0.0)
            {
               JSONObject = "{" + "\"geneId\"" + ":"+ "\"" + genes[0].split("\\.")[0] + "\"" + "," + "\"fpkm\":" + "\"" + genes[6] +"\"" + "}";
               System.out.println(JSONObject);
               ListOfJSONObjects.add(JSONObject);
            }
            gene = br.readLine();
        }
    }
    void BED() throws IOException
    {
        String line = br.readLine();
        String lines[];
        while(line != null)
        {
            lines = line.split("\t");
            if(!isNumeric(lines[1]))
            {
                line = br.readLine();
                continue;
            }
            JSONObject = "{" + "\"chrom\"" + ":"+ "\"" + lines[0] + "\"" + "," + "\"start\":" + "\"" + lines[1] +"\"" + "," +"\"end\":" + "\"" + lines[2] + "\"" + "}";
            System.out.println(JSONObject);
            ListOfJSONObjects.add(JSONObject);
            line = br.readLine();
            
        }
    }
//    void load(ArrayList array, FileReader fileReader)
//    {
//        Gson gson =  new Gson();
//        for(int k = 0; k < fileReader.ListOfJSONObjects.size(); k++)
//       {
//           array.add(gson.fromJson(fileReader.ListOfJSONObjects.get(k), TSS.class));
//       }
//    }

}
