/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transcription.start.site;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author Saadeeq Duncanson
 */

public class RIPPLEPossibleEnhancersPromoters 
{
    

    public static boolean isNumeric(String str)
        {
            NumberFormat formatter = NumberFormat.getInstance();
            ParsePosition pos = new ParsePosition(0);
            formatter.parse(str, pos);
            return str.length() == pos.getIndex();
        }
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
        String line;
        ArrayList<ArrayList<TSS>> TSSs = new ArrayList(24);
        ArrayList<ArrayList<Enhancer>> possibleEnhancers = new ArrayList(24);
        ArrayList<ArrayList<ArrayList<Enhancer>>> possibleCellLineEnhancers = new ArrayList(4);
        ArrayList<ArrayList<Promoter>> possiblePromoters = new ArrayList(24);
        ArrayList<ArrayList<ArrayList<Promoter>>> possibleCellLinePromoters = new ArrayList(4);
        ArrayList<ArrayList<ArrayList<Feature>>> features = new ArrayList(4);
        ArrayList<ArrayList<boolean []>> featureVector = new ArrayList(4);
        ArrayList<String> TFName = new ArrayList(13);
        ArrayList<String> histoneNames = new ArrayList(8);
        ArrayList<ArrayList<GeneExpression>> expressionLevels = new ArrayList(4);
        //ArrayList<ArrayList<int[]>> featureLocations = new ArrayList(24);
        for(int i = 0; i < 4; i++)
        {
            featureVector.add(new ArrayList<>());
            features.add(new ArrayList<>());
            expressionLevels.add(new ArrayList<>());
            for(int k = 0; k < 24; k++)
            {
                features.get(i).add(new ArrayList<>());
            }
        }
        TFName.add("Cmyc");
        TFName.add("Ctcf");
        TFName.add("Jund");
        TFName.add("Max");
        TFName.add("Nrf1");
        TFName.add("Nrsf");
        TFName.add("P300");
        TFName.add("Pol2");
        TFName.add("Rad21");
        TFName.add("Smc3");
        TFName.add("Taf1");
        TFName.add("Tbp");
        TFName.add("Usf2");
        histoneNames.add("H3k4me2");
        histoneNames.add("H3k4me3");
        histoneNames.add("H3k9ac");
        histoneNames.add("H3k27ac");
        histoneNames.add("H3k27me3");
        histoneNames.add("H3k36me3");
        histoneNames.add("H3k79me2");
        histoneNames.add("H4k20me1");
        int distanceFromTSS = 2500;
        String featureDirectory = "C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\data\\RIPPLE features"; 
        BufferedReader br;
        for(int i = 0; i < 4; i++)
        {
            String cellLineDirectory = null;
            switch(i)
            {
                case 0: 
                {
                  cellLineDirectory = featureDirectory + "\\GM12878";
                  break;
                }
                case 1:
                {
                   cellLineDirectory = featureDirectory + "\\H1-hESC"; 
                   break;
                }
                case 2:
                {
                   cellLineDirectory = featureDirectory + "\\Hela-S3"; 
                   break;
                }
                case 3:
                {
                    cellLineDirectory = featureDirectory + "\\K562";
                    break;
                }   
            }
            for(int k = 0; k < 4; k++)
            {
                String featureTypeDirectory;
                switch(k)
                {
                    case 0:
                    {
                        featureTypeDirectory = cellLineDirectory + "\\Histone Modifications";
                        for(int l = 0; l < histoneNames.size(); l++)
                        {
                            br = new BufferedReader(new FileReader(featureTypeDirectory + "\\" + histoneNames.get(l) + ".narrowPeak"));
                            String peak = br.readLine();
                            String peaks[];
                            while(peak != null)
                            {
                                peaks = peak.split("\t");
                                peaks[0] = peaks[0].replace("chr", "");
                                if(isNumeric(peaks[0]))
                                {
                                    features.get(i).get(Integer.parseInt(peaks[0])-1).add(new Feature(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])}, histoneNames.get(l)));
                                    //featureLocations.get(Integer.parseInt(peaks[0])-1).add(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])});
                                }
                                else if(peaks[0].equals("X"))
                                {
                                    features.get(i).get(22).add(new Feature(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])}, histoneNames.get(l)));
                                    //featureLocations.get(22).add(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])});
                                }
                                else if(peaks[0].equals("Y"))
                                {
                                    features.get(i).get(23).add(new Feature(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])}, histoneNames.get(l)));
                                    //featureLocations.get(23).add(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])});
                                }
                                peak = br.readLine();
                            }
                        }
                        break;
                    }
                    case 1:
                    {
                        featureTypeDirectory = cellLineDirectory + "\\Transcription factors";
                        for(int l = 0; l < TFName.size();l++)
                        {
                            br = new BufferedReader(new FileReader(featureTypeDirectory + "\\" + TFName.get(l) + "\\" + TFName.get(l) + ".narrowPeak"));
                            //System.out.println(featureTypeDirectory + "\\" + TFName.get(l) + "\\" + TFName.get(l) + ".narrowPeak");
                            String peak = br.readLine();
                            String peaks[];
                            while(peak != null)
                            {
                                
                                peaks = peak.split("\t");
                                peaks[0] = peaks[0].replace("chr", "");
                                if(!peaks[0].contains("X") && !peaks[0].contains("Y") && !peaks[0].contains("M"))
                                {
                                    features.get(i).get(Integer.parseInt(peaks[0])-1).add(new Feature(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])}, TFName.get(l)));
                                    //featureLocations.get(Integer.parseInt(peaks[0])-1).add(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])});
                                }
                                else if(peaks[0].equals("X"))
                                {
                                    features.get(i).get(22).add(new Feature(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])}, TFName.get(l)));
                                    //featureLocations.get(22).add(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])});
                                }
                                else if(peaks[0].equals("Y"))
                                {
                                    features.get(i).get(23).add(new Feature(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])}, TFName.get(l)));
                                    //featureLocations.get(23).add(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])});
                                }
                                peak = br.readLine();
                            }
                        }
                        break;
                    }
                    case 2:
                    {
                        featureTypeDirectory = cellLineDirectory + "\\DNase";
                        br = new BufferedReader(new FileReader(featureTypeDirectory + "\\" + "DNaseBroad" + ".bed"));
                            //System.out.println(featureTypeDirectory + "\\" + TFName.get(l) + "\\" + TFName.get(l) + ".narrowPeak");
                            String peak = br.readLine();
                            String peaks[];
                            while(peak != null)
                            {
                                
                                peaks = peak.split("\t");
                                peaks[0] = peaks[0].replace("chr", "");
                                if(!peaks[0].contains("X") && !peaks[0].contains("Y") && !peaks[0].contains("M"))
                                {
                                    features.get(i).get(Integer.parseInt(peaks[0])-1).add(new Feature(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])}, "DNase"));
                                    //featureLocations.get(Integer.parseInt(peaks[0])-1).add(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])});
                                }
                                else if(peaks[0].equals("X"))
                                {
                                    features.get(i).get(22).add(new Feature(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])}, "DNase"));
                                    //featureLocations.get(22).add(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])});
                                }
                                else if(peaks[0].equals("Y"))
                                {
                                    features.get(i).get(23).add(new Feature(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])}, "DNase"));
                                    //featureLocations.get(23).add(new int [] {Integer.parseInt(peaks[1]), Integer.parseInt(peaks[2])});
                                }
                                peak = br.readLine();
                            }
                        break;
                    }
                    case 3:
                    {
                        featureTypeDirectory = cellLineDirectory + "\\mRNA";
                        br = new BufferedReader(new FileReader(featureTypeDirectory + "\\" + "mRNA" + ".tsv"));
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
                                expressionLevels.get(i).add(new GeneExpression(genes[0].split("\\.")[0], Double.parseDouble(genes[6])));
                            gene = br.readLine();
                        }
                        break;
                    }
                }
                
            }
        }
        System.out.println(expressionLevels.get(0).size());
        for(int i =0; i < 4; i++)
        {
            for(int k = 0; k < 24; k++)
            {    
                Collections.sort(features.get(i).get(k), new Comparator<Feature>(){
                    public int compare(Feature a, Feature b)
                    {
                        return a.range[0] - b.range[0];
                    }
                });
            }
        }
        for(int i = 0; i < 24; i++)
        {
            TSSs.add(new ArrayList<>());
        }
        br = new BufferedReader(new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\data\\gencodeV10 annotations\\gencode.v10.annotation.gtf"));
        line = br.readLine();
        String lines[];
        String descriptionLine[];
        String geneId;
        while(line != null)
        {
            lines = line.split("\t");
            if(line.contains("##") || !lines[2].contains("UTR"))
            {
                line = br.readLine();
                continue;
            }
            lines[0] = lines[0].replace("chr", "");
            descriptionLine = lines[8].split(";");
            geneId = descriptionLine[0].replace("gene_id \"", "");
            geneId = geneId.replace("\"", "");
            geneId = geneId.split("\\.")[0];
            if(!lines[0].contains("X") && !lines[0].contains("Y") && !lines[0].contains("M"))
            {
                //System.out.println(line + "  " + lines[3]);
                TSSs.get(Integer.parseInt(lines[0]) - 1).add(new TSS(Integer.parseInt(lines[3]),geneId));
            }
            else if (lines[0].contains("X"))
            {
                TSSs.get(22).add(new TSS(Integer.parseInt(lines[3]),geneId));
                //System.out.println(line + "  " + lines[3]);
            }
            else if(lines[0].contains("Y"))
            {
                TSSs.get(23).add(new TSS(Integer.parseInt(lines[3]),geneId));
                //System.out.println(line + "  " + lines[3]);
            }
            //System.out.println(line + "  " + lines[3]);
            line = br.readLine();
        }
        for(int i = 0; i < TSSs.size(); i++)
        {
            Collections.sort(TSSs.get(i), new Comparator<TSS>(){
                public int compare(TSS a, TSS b)
                {
                    return a.start - b.start;
                }
        });
            for(int k = 0; k < TSSs.get(i).size()-1; k++)
            {
                    //System.out.println(TSSs.get(i).get(k).geneID);
                while(k < TSSs.get(i).size()-1 && Objects.equals(TSSs.get(i).get(k).start, TSSs.get(i).get(k+1).start))
                {
                    //System.out.println(TSSs.get(i).get(k).start + "  " + TSSs.get(i).get(k+1).start);
                    TSSs.get(i).remove(k+1);
                    //System.out.println(TSSs.get(i).get(k+1));
                }
            }
        }
        for(int k = 0; k < 24; k++)
        {
            possibleEnhancers.add(new ArrayList<>());
        }
      
        for(int i = 0; i < 4; i++)
        {
            String mainDirectory = "C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\Enhancer Data";
            switch(i)
            {
                case 0:
                {
                    br = new BufferedReader(new FileReader(mainDirectory + "\\GM12878Segmentation.bed"));
                    break;
                }
                case 1:
                {
                    br = new BufferedReader(new FileReader(mainDirectory + "\\H1-hESCSeqmentation.bed"));
                    break;
                }
                case 2:
                {
                    br = new BufferedReader(new FileReader(mainDirectory + "\\HelaS3Segmentation.bed"));
                    break;
                }
                case 3:
                {
                    br = new BufferedReader(new FileReader(mainDirectory + "\\K562Segmentation.bed"));
                    break;
                }
            }
            line = br.readLine();
            while(line != null)
            {
                lines = line.split("\t");
                lines[0] = lines[0].replace("chr", "");
                if(lines[3].contains("E"))
                {
                   if(isNumeric(lines[0]))
                   {
                        possibleEnhancers.get(Integer.parseInt(lines[0])-1).add(new Enhancer(new int [] {Integer.parseInt(lines[1]), Integer.parseInt(lines[2])}));
                   }
                   else if(lines[0].contains("X"))
                   {
                       possibleEnhancers.get(22).add(new Enhancer(new int [] {Integer.parseInt(lines[1]), Integer.parseInt(lines[2])}));
                   }
                   else if(lines[0].contains("Y"))
                   {
                       possibleEnhancers.get(23).add(new Enhancer(new int [] {Integer.parseInt(lines[1]), Integer.parseInt(lines[2])}));
                   }
                }
                line = br.readLine();
            }
            
        }
        for(int i = 0; i < possibleEnhancers.size(); i++)
        {
            Collections.sort(possibleEnhancers.get(i), new Comparator<Enhancer>(){
                public int compare(Enhancer a, Enhancer b)
                {
                    return a.range[0] - b.range[0];
                }
        });
        }
        int sizes = 0;
        for(int i = 0; i < possibleEnhancers.size(); i++)
        {
            for(int k = 0; k < possibleEnhancers.get(i).size(); k++)
            {
                if(possibleEnhancers.get(i).get(k).range[1] - possibleEnhancers.get(i).get(k).range[0] < 200)
                {
                    possibleEnhancers.get(i).remove(k);
                    k--;
                }
            }
            for(int k = 0; k < possibleEnhancers.get(i).size(); k++)
            {
                while((k+1 < possibleEnhancers.get(i).size())&& possibleEnhancers.get(i).get(k).range[1] > possibleEnhancers.get(i).get(k+1).range[0])
                {
                    if(possibleEnhancers.get(i).get(k).range[1] < possibleEnhancers.get(i).get(k+1).range[1])
                        possibleEnhancers.get(i).get(k).range[1] = possibleEnhancers.get(i).get(k+1).range[1];
                     possibleEnhancers.get(i).remove(k+1);
                }
            }
            
           sizes += possibleEnhancers.get(i).size();
        }
        System.out.println("enhancer size: " + sizes);
//        for(int i = 0; i < 24; i++)
//        {
//            possibleEnhancers.add(new ArrayList());
//            int size = TSSs.get(i).size();
//            for(int k = 0; k < size; k++)
//            {
//                if(k == 0)
//                {
//                    //System.out.println(TSSs.get(i).get(k));
//                    if(TSSs.get(i).get(k).start  >= (distanceFromTSS + 1))
//                        possibleEnhancers.get(i).add(new int[] {0,TSSs.get(i).get(k).start - (distanceFromTSS + 1)});
//                    //System.out.println( "Start: "+ possibleEnhancers.get(i).get(k)[0] + " End: " + possibleEnhancers.get(i).get(k)[1]);
//                }
//                else if(k == size - 1)
//                {
//                    //System.out.println( "k-1 " + TSSs.get(i).get(k-1) + " k " + TSSs.get(i).get(k));
//                    if((TSSs.get(i).get(k).start - TSSs.get(i).get(k-1).start) >= (distanceFromTSS + 1)*2)
//                        possibleEnhancers.get(i).add(new int [] {TSSs.get(i).get(k-1).start + (distanceFromTSS + 1), TSSs.get(i).get(k).start - (distanceFromTSS + 1)});
//                    possibleEnhancers.get(i).add(new int[] {TSSs.get(i).get(k).start + (distanceFromTSS + 1),Integer.MAX_VALUE});
//                    //System.out.println( possibleEnhancers.get(i).get(k)[0] + " " + possibleEnhancers.get(i).get(possibleEnhancers.get)[0])
//                    //System.out.println( "Start: "+ possibleEnhancers.get(i).get(possibleEnhancers.get(i).size() -1)[0] + " End: " + possibleEnhancers.get(i).get(possibleEnhancers.get(i).size() -1)[1]);
//                }
//                else
//                {
//                    //System.out.println( "k-1 " + TSSs.get(i).get(k-1) + " k " + TSSs.get(i).get(k));
//                    if((TSSs.get(i).get(k).start - TSSs.get(i).get(k-1).start) >= (distanceFromTSS + 1)*2)
//                        possibleEnhancers.get(i).add(new int [] {TSSs.get(i).get(k-1).start + (distanceFromTSS + 1), TSSs.get(i).get(k).start - (distanceFromTSS + 1)});
//                    //System.out.println( "Start: "+ possibleEnhancers.get(i).get(k)[0] + " End: " + possibleEnhancers.get(i).get(k)[1] + " distance: " + (TSSs.get(i).get(k) - TSSs.get(i).get(k-1)));
//                }
//            }
//            //for(int l = 0; l < possibleEnhancers.get(i).size(); l++)
//                //if(l == 0)
//                    //System.out.println( "Start: "+ possibleEnhancers.get(i).get(l)[0] + " End: " + possibleEnhancers.get(i).get(l)[1] );
//                //else
//                    //System.out.println( "Start: "+ possibleEnhancers.get(i).get(l)[0] + " End: " + possibleEnhancers.get(i).get(l)[1] + " distance: " + (TSSs.get(i).get(l) - TSSs.get(i).get(l-1)));
//        }
        for(int i = 0; i < 24; i++)
        {
            possiblePromoters.add(new ArrayList());
            int size = TSSs.get(i).size();
            for(int k = 0; k < size; k++)
            {
                if(k==0)
                {
                    if(TSSs.get(i).get(k).start - (distanceFromTSS-1) > 0)
                        possiblePromoters.get(i).add(new Promoter(new int [] {TSSs.get(i).get(k).start - (distanceFromTSS-1),TSSs.get(i).get(k).start + (distanceFromTSS-1)}, TSSs.get(i).get(k).geneID));
                    else
                        possiblePromoters.get(i).add(new Promoter(new int [] {0,TSSs.get(i).get(k).start + (distanceFromTSS-1)}, TSSs.get(i).get(k).geneID));
                }
                else
                    possiblePromoters.get(i).add(new Promoter(new int [] {TSSs.get(i).get(k).start - (distanceFromTSS-1),TSSs.get(i).get(k).start + (distanceFromTSS-1)}, TSSs.get(i).get(k).geneID));
            }
            //for(int l = 0; l < possiblePromoters.get(i).size(); l++)
            //System.out.println( "Start: "+ possiblePromoters.get(i).get(l)[0] + " End: " + possiblePromoters.get(i).get(l)[1] );
        }
        //System.out.println("Enhancer size " + possibleEnhancers.get(1).size() + " Promoter size " + possiblePromoters.get(1).size() + " removing overlap");
        for(int i = 0; i < possiblePromoters.size();i++)
        {
            
            for(int k = 0; k < possiblePromoters.get(i).size(); k++)
            {
//                if(possiblePromoters.get(i).get(k)[0] < possiblePromoters.get(i).get(k+1)[0] && possiblePromoters.get(i).get(k)[1] > possiblePromoters.get(i).get(k+1)[1] )
//                {
//                    System.out.println("start " + possiblePromoters.get(i).get(k+1)[0] + " End " + possiblePromoters.get(i).get(k+1)[1]);
//                    possiblePromoters.get(i).remove(k+1);
//                }
                //while((k+1) < possiblePromoters.get(i).size() && possiblePromoters.get(i).get(k).range[0] < possiblePromoters.get(i).get(k+1).range[0] && possiblePromoters.get(i).get(k).range[1] > possiblePromoters.get(i).get(k+1).range[0] && possiblePromoters.get(i).get(k).geneId.equals(possiblePromoters.get(i).get(k+1).geneId))
                while((k+1) < possiblePromoters.get(i).size() && possiblePromoters.get(i).get(k).range[0] < possiblePromoters.get(i).get(k+1).range[0] && possiblePromoters.get(i).get(k).range[1] > possiblePromoters.get(i).get(k+1).range[0])
                {
                    //System.out.println("start " + possiblePromoters.get(i).get(k+1)[0] + " End " + possiblePromoters.get(i).get(k+1)[1]);
                    possiblePromoters.get(i).get(k).range[1] = possiblePromoters.get(i).get(k+1).range[1];
                    possiblePromoters.get(i).remove(k+1);
                }
//                else if(possiblePromoters.get(i).get(k)[0] < possiblePromoters.get(i).get(k+1)[1] && possiblePromoters.get(i).get(k)[1] > possiblePromoters.get(i).get(k+1)[1])
//                {
//                    System.out.println("start " + possiblePromoters.get(i).get(k+1)[0] + " End " + possiblePromoters.get(i).get(k+1)[1]);
//                    possiblePromoters.get(i).get(k)[0] = possiblePromoters.get(i).get(k+1)[0];
//                    possiblePromoters.get(i).remove(k+1);
//                }
                   
            }
             //for(int l = 0; l < possiblePromoters.get(i).size(); l++)
                //System.out.println( "Start: "+ possiblePromoters.get(i).get(l)[0] + " End: " + possiblePromoters.get(i).get(l)[1] );
        }
        int size = 0;
       for(int i = 0; i < 24; i++)
       {
           size += possiblePromoters.get(i).size();
                //System.out.println("Enhancer size " + possibleEnhancers.get(i).size() + " Promoter size " + possiblePromoters.get(i).size());     
       }
       System.out.println("promoter size " + size);
       for(int i = 0; i < 4; i++)
       {
           possibleCellLineEnhancers.add(new ArrayList<>());
           possibleCellLinePromoters.add(new ArrayList<>());
           for(int k = 0; k < 24; k++)
           {
               possibleCellLineEnhancers.get(i).add(new ArrayList<>());
               possibleCellLinePromoters.get(i).add(new ArrayList<>());
               for(int q = 0; q < possiblePromoters.get(k).size(); q++)
               {
                   possibleCellLinePromoters.get(i).get(k).add(new Promoter(new int [] {possiblePromoters.get(k).get(q).range[0],possiblePromoters.get(k).get(q).range[1]}, possiblePromoters.get(k).get(q).geneId));
               }
               for(int l = 0; l < possibleEnhancers.get(k).size(); l++)
               {
                   possibleCellLineEnhancers.get(i).get(k).add(new Enhancer(new int [] {possibleEnhancers.get(k).get(l).range[0],possibleEnhancers.get(k).get(l).range[1]}));
               }
           }
           
       }
       for(int p = 0; p < 4; p++)
       {    
        for(int i = 0; i < 24; i++)
        {
           System.out.println(   " possible Enhancers " + possibleCellLineEnhancers.get(p).get(i).size() + " possible Promoters " + possibleCellLinePromoters.get(p).get(i).size() + " cell line# " + p);
           for(int k = 0; k < possibleCellLineEnhancers.get(p).get(i).size(); k++)
           {
               boolean overlap = false;
               for(int l = 0; l < features.get(p).get(i).size(); l++)
               {
                   if(!(features.get(p).get(i).get(l).range[0] > possibleCellLineEnhancers.get(p).get(i).get(k).range[1] ||  features.get(p).get(i).get(l).range[1] < possibleCellLineEnhancers.get(p).get(i).get(k).range[0]))
                   {
                        //System.out.println("feature coordinates " + features.get(p).get(i).get(l).range[0] + "-" + features.get(p).get(i).get(l).range[1] + "  Enhancer coordinates " + possibleEnhancers.get(i).get(k)[0] + "-" + possibleEnhancers.get(i).get(k)[1]);
                        overlap = true;
                        break;
                   }
               }
               if(overlap == false)
               {
                  //System.out.println(possibleEnhancers.get(i).get(k)[0]);
                  possibleCellLineEnhancers.get(p).get(i).remove(k);
                  k--;
               }
           }
           for(int q = 0; q < possibleCellLinePromoters.get(p).get(i).size(); q++)
           {
               boolean overlap = false;
               for(int l = 0; l < expressionLevels.get(p).size(); l++)
               {
                   if(possibleCellLinePromoters.get(p).get(i).get(q).geneId.equals(expressionLevels.get(p).get(l).gene))
                   {
                        //System.out.println("feature coordinates " + features.get(p).get(i).get(l).range[0] + "-" + features.get(p).get(i).get(l).range[1] + "  Enhancer coordinates " + possibleEnhancers.get(i).get(k)[0] + "-" + possibleEnhancers.get(i).get(k)[1]);
                        overlap = true;
                        break;
                   }
               }
               for(int l = 0; l < features.get(p).get(i).size(); l++)
               {
                   if(overlap == true)
                       break;
                   if(!(features.get(p).get(i).get(l).range[0] > possibleCellLinePromoters.get(p).get(i).get(q).range[1] ||  features.get(p).get(i).get(l).range[1] < possibleCellLinePromoters.get(p).get(i).get(q).range[0]))
                   {
                        //System.out.println("feature coordinates " + features.get(p).get(i).get(l).range[0] + "-" + features.get(p).get(i).get(l).range[1] + "  Enhancer coordinates " + possibleEnhancers.get(i).get(k)[0] + "-" + possibleEnhancers.get(i).get(k)[1]);
                        overlap = true;
                        break;
                   }
               }
               if(overlap == false)
               {
                  //System.out.println(possibleEnhancers.get(i).get(k)[0]);
                  possibleCellLinePromoters.get(p).get(i).remove(q);
                  q--;
               }
           }
           System.out.println(" possible Enhancers " + possibleCellLineEnhancers.get(p).get(i).size() + " possible Promoters " + possibleCellLinePromoters.get(p).get(i).size() + " cell line# " + p + "  nonCell line specific promoters " + possiblePromoters.get(i).size() + "  nonCell line specific promoters " + possibleEnhancers.get(i).size() );
        }
       }
       //String mainDirectory = "C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\RIPPLE Enhancer and Promoters";
       BufferedWriter bw;
       for(int q = 0; q < 2; q++)
       {
           String mainDirectory = "C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\RIPPLE Enhancer and Promoters";
           if(q == 0)
               mainDirectory = mainDirectory + "\\Enhancers";
           else
               mainDirectory = mainDirectory + "\\Promoters";
       for(int p = 0; p < 4; p++)
       {
           String cellLineDirectory = null;
           switch(p)
            {
                case 0: 
                {
                  cellLineDirectory = mainDirectory + "\\GM12878";
                  break;
                }
                case 1:
                {
                   cellLineDirectory = mainDirectory + "\\H1-hESC"; 
                   break;
                }
                case 2:
                {
                   cellLineDirectory = mainDirectory + "\\HelaS3"; 
                   break;
                }
                case 3:
                {
                    cellLineDirectory = mainDirectory + "\\K562";
                    break;
                }   
            }
           if(q == 0)
            bw = new BufferedWriter(new FileWriter(cellLineDirectory + "\\Enhancers.bedgraph"));
           else
               bw = new BufferedWriter(new FileWriter(cellLineDirectory + "\\Promoters.bedgraph"));
           for(int i = 0; i < 24; i++)
           {
              if(q == 0)
              {
                for(int k = 0; k < possibleCellLineEnhancers.get(p).get(i).size(); k++) 
                {
                  if(i < 22)
                    bw.write("chr" + (i+1) + "\t" + possibleCellLineEnhancers.get(p).get(i).get(k).range[0] + "\t" + possibleCellLineEnhancers.get(p).get(i).get(k).range[1]);
                  else if(i == 22)
                    bw.write("chrX" + "\t" + possibleCellLineEnhancers.get(p).get(i).get(k).range[0] + "\t" + possibleCellLineEnhancers.get(p).get(i).get(k).range[1]);
                  else if(i == 23)
                    bw.write("chrY" + "\t" + possibleCellLineEnhancers.get(p).get(i).get(k).range[0] + "\t" + possibleCellLineEnhancers.get(p).get(i).get(k).range[1]);
                  bw.newLine();
                }
              }
              else
              {
                for(int k = 0; k < possibleCellLinePromoters.get(p).get(i).size(); k++) 
                {
                  if(i < 22)
                    bw.write("chr" + (i+1) + "\t" + possibleCellLinePromoters.get(p).get(i).get(k).range[0] + "\t" + possibleCellLinePromoters.get(p).get(i).get(k).range[1]);
                  else if(i == 22)
                    bw.write("chrX" + "\t" + possibleCellLinePromoters.get(p).get(i).get(k).range[0] + "\t" + possibleCellLinePromoters.get(p).get(i).get(k).range[1]);
                  else if(i == 23)
                    bw.write("chrY" + "\t" + possibleCellLinePromoters.get(p).get(i).get(k).range[0] + "\t" + possibleCellLinePromoters.get(p).get(i).get(k).range[1]);
                  bw.newLine();
                }
              }
           }
           bw.flush();
       }
        }
    }
    
}
