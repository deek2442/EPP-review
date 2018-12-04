/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.pettrainingsetepp;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Saadeeq
 */
public class IMPETTrainingSetEPP 
{

    /**
     * @param args the command line arguments
     * @return 
     */
    public static boolean overlap(int start, int end, int start1, int end1)
    {
            return !(start > end1 ||  end < start1);
    }
    public static boolean isNumeric(String str)
        {
            NumberFormat formatter = NumberFormat.getInstance();
            ParsePosition pos = new ParsePosition(0);
            formatter.parse(str, pos);
            return str.length() == pos.getIndex();
        }
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
        ArrayList<ArrayList<ChIAPETInteractions>> chIAPETInteractions = new ArrayList(5);
        ArrayList<ArrayList<ArrayList<Feature>>> p300s = new ArrayList(2);
        ArrayList<ArrayList<ArrayList<Feature>>> enhancers = new ArrayList(2);
        for(int l = 0; l < 2; l++)
        {
            p300s.add(new ArrayList());
            enhancers.add(new ArrayList());
            for(int i = 0; i < 24; i++)
            {
                p300s.get(l).add(new ArrayList());
                enhancers.get(l).add(new ArrayList());
            }
        }
        ArrayList<ArrayList<RNASeqData>> RNAData = new ArrayList(2);
        ArrayList<ArrayList<Promoter>> promoter = new ArrayList(24);
        ArrayList<ArrayList<Promoter>> HelaS3Promoter = new ArrayList(24);
        ArrayList<ArrayList<Promoter>> K562Promoter = new ArrayList(24);
        ArrayList<ArrayList<EPP>> epp = new ArrayList(24);
        ArrayList<TSS> tss = new ArrayList(20000);
        HashMap<String,ArrayList<int[]>> p300Map;
        HashMap<String,ArrayList<int[]>> promoterMap;
        Gson gson = new Gson();
        FileReader fileReader = new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\data\\gencodeV10 annotations\\","gencode.v10.annotation.gtf");
        fileReader.GTF("UTR");
        for(int k = 0; k < fileReader.ListOfJSONObjects.size(); k++)
       {
           tss.add(gson.fromJson(fileReader.ListOfJSONObjects.get(k), TSS.class));
       }
        for(int i = 0; i < 24; i++)
        {
            promoter.add(new ArrayList());
            K562Promoter.add(new ArrayList());
            HelaS3Promoter.add(new ArrayList());
            epp.add(new ArrayList());
        }
        for(int i = 0; i < tss.size(); i++)
        {
            if(!(tss.get(i).chr.contains("X")) && !(tss.get(i).chr.contains("Y")) && !(tss.get(i).chr.contains("M")))
               promoter.get(Integer.parseInt(tss.get(i).chr.replace("chr", "")) - 1).add(new Promoter (new int [] {tss.get(i).start - 2000, tss.get(i).start + 500}, tss.get(i).geneID));
            else if(tss.get(i).chr.contains("X"))
               promoter.get(22).add(new Promoter(new int [] {tss.get(i).start - 2000, tss.get(i).start + 500}, tss.get(i).geneID));
            else if(tss.get(i).chr.contains("Y"))
               promoter.get(23).add(new Promoter(new int [] {tss.get(i).start - 2000, tss.get(i).start + 500}, tss.get(i).geneID)); 
        }
        //fileReader = new FileReader();
        //System.out.println(promoter.get(0).size());
        for(int i = 0; i < 2; i++)
        {
            RNAData.add(new ArrayList());
        switch(i)
        {
            case 0:
            {
                fileReader = new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\RIPPLE features\\Hela-S3\\mRNA", "mRNA.tsv");
                break;
            }
            case 1:
            {
                fileReader = new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\RIPPLE features\\K562\\mRNA", "mRNA.tsv");
                break;
            }
            default:
            {
                fileReader = null;
            }
        }
            fileReader.RNASeq();
            for(int k = 0; k < fileReader.ListOfJSONObjects.size(); k++)
            {
                RNAData.get(i).add(gson.fromJson(fileReader.ListOfJSONObjects.get(k), RNASeqData.class));
            }
            for(int l = 0; l < 24; l++)
            {
                for(int k = 0; k < promoter.get(l).size(); k++)
                {
                    for(int o = 0; o < RNAData.get(i).size(); o++)
                    {
                        if(promoter.get(l).get(k).geneId.contentEquals(RNAData.get(i).get(o).geneId) && i == 0)
                        {
                            HelaS3Promoter.get(l).add(promoter.get(l).get(k));
                        }
                        else if(promoter.get(l).get(k).geneId.contentEquals(RNAData.get(i).get(o).geneId) && i == 1)
                        {
                            K562Promoter.get(l).add(promoter.get(l).get(k));
                        }
                    }
                }
            }
        }
        for(int i = 0; i < 2; i++)
        {
            enhancers.add(new ArrayList());
        switch(i)
        {
            case 0:
            {
                fileReader = new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\Enhancer Data", "HelaS3Segmentation.bed");
                break;
            }
            case 1:
            {
                fileReader = new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\Enhancer Data", "K562Segmentation.bed");
                break;
            }
            default:
            {
                fileReader = null;
            }
        }
            fileReader.BED();
            for(int k = 0; k < fileReader.ListOfJSONObjects.size(); k++)
            {
                Feature enhancer = gson.fromJson(fileReader.ListOfJSONObjects.get(k), Feature.class);
                if(isNumeric(enhancer.chrom.replace("chr", "")))
                    enhancers.get(i).get(Integer.parseInt(enhancer.chrom.replace("chr", ""))- 1).add(enhancer);
                else if(enhancer.chrom.contentEquals("chrX"))
                    enhancers.get(i).get(22).add(enhancer);
                else if(enhancer.chrom.contentEquals("chrY"))
                    enhancers.get(i).get(23).add(enhancer);
            }
        }
        for(int i = 0; i < 2; i++)
        {
        switch(i)
        {
            case 0:
            {
                fileReader = new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\RIPPLE features\\Hela-S3\\Transcription factors\\P300", "P300.narrowPeak");
                break;
            }
            case 1:
            {
                fileReader = new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\RIPPLE features\\K562\\Transcription factors\\P300", "P300.narrowPeak");
                break;
            }
            default:
            {
                fileReader = null;
            }
        }
            fileReader.BED();
            for(int k = 0; k < fileReader.ListOfJSONObjects.size(); k++)
            {
                Feature p300 = gson.fromJson(fileReader.ListOfJSONObjects.get(k), Feature.class);
                if(isNumeric(p300.chrom.replace("chr", "")))
                    p300s.get(i).get(Integer.parseInt(p300.chrom.replace("chr", "")) - 1).add(p300);
                else if(p300.chrom.contentEquals("chrX"))
                    p300s.get(i).get(22).add(p300);
                else if(p300.chrom.contentEquals("chrY"))
                    p300s.get(i).get(23).add(p300);
            }
        }
        
        for(int i = 0; i < 2; i++)
        {
            String cellLine;
            //FileReader fileReader;
            chIAPETInteractions.add(new ArrayList<>());
            switch(i)
            {
//                case 0:
//                {
//                   cellLine = "HCT116";
//                    fileReader = new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\Chromatin interaction Data\\"+ cellLine +"\\ChIA-PET", cellLine + ".cluster.pet2+.txt");
//                    break;
//                }
                case 0:
                {
                   cellLine = "HeLa-S3";
                   fileReader = new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\Chromatin interaction Data\\"+ cellLine +"\\ChIA-PET", cellLine + ".cluster.pet2+.txt");
                   break;
                }
                case 1:
                {
                   cellLine = "K562";
                   fileReader = new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\Chromatin interaction Data\\K562\\ChIA-PET\\pilot\\", cellLine + ".cluster.pet2+.txt");
                   break;
                }
//                case 3:
//                {
//                   cellLine = "MCF7";
//                   fileReader = new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\Chromatin interaction Data\\"+ cellLine + "\\ChIA-PET\\pilot\\", cellLine + ".cluster.pet2+.txt");
//                   break;
//                }
//                case 4:
//                {
//                   cellLine = "NB4";
//                   fileReader = new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\Chromatin interaction Data\\"+ cellLine + "\\ChIA-PET\\", cellLine + ".cluster.pet2+.txt");
//                   break;
//                }   
                default:
                {
                    fileReader = null;
                }
            }
            //FileReader fileReader = new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\Chromatin interaction Data\\HeLa-S3\\ChIA-PET","HeLa-S3.cluster.pet2+.txt");
            //ArrayList<ChIAPETInteractions> chIAPETInteractions = new ArrayList(10000);
            fileReader.PET();
       for(int k = 0; k < fileReader.ListOfJSONObjects.size(); k++)
       {
           chIAPETInteractions.get(i).add(gson.fromJson(fileReader.ListOfJSONObjects.get(k), ChIAPETInteractions.class));
           //if(i == 1)
           //for(int o = 0; o < chIAPETInteractions.size(); o++)
            //System.out.println(chIAPETInteractions.get(i).get(k).PETCount);
       }
        }
            
            for(int i = 0; i < 2; i++)
            {   
                int x = 0;
            for(int o = 0; o < chIAPETInteractions.get(i).size(); o++)
            {
                
                if(!(chIAPETInteractions.get(i).get(o).chromLeft.contentEquals(chIAPETInteractions.get(i).get(o).chromRight) && chIAPETInteractions.get(i).get(o).PETCount >= 5))
                {
                    //System.out.println(chIAPETInteractions.get(i).get(o).startLeft + "  " + chIAPETInteractions.get(i).get(o).chromRight);
                    chIAPETInteractions.get(i).remove(o);
                    o--;
                    //x++;
                //System.out.println(x);
                }
                
            }
                System.out.println(chIAPETInteractions.get(i).size());
            }
         for(int i = 0; i < 2; i++)
         {
             if(i == 0)
                 promoter = HelaS3Promoter;
             else
                 promoter = K562Promoter;
            for(int k = 0; k < chIAPETInteractions.get(i).size(); k++)
            {
                if(isNumeric(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")))
                {
                    for(int l = 0; l < promoter.get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).size(); l++)
                    {
                        boolean promoterP300Overlap = false;
                        if(overlap(chIAPETInteractions.get(i).get(k).startLeft, chIAPETInteractions.get(i).get(k).endLeft, promoter.get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).get(l).range[0], promoter.get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).get(l).range[1]))
                        {
                            for(int w = 0; w < p300s.get(i).get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).size(); w++)
                            {
                                if(overlap(chIAPETInteractions.get(i).get(k).startLeft, chIAPETInteractions.get(i).get(k).endLeft,p300s.get(i).get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).get(w).start, p300s.get(i).get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).get(w).end))
                                {
                                   promoterP300Overlap = true; 
                                }
                            }
                            if(promoterP300Overlap)
                                continue;
                            
                            
                                
                                
                                    for(int w = 0; w < p300s.get(i).get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).size(); w++)
                                    {
                                        if(overlap(chIAPETInteractions.get(i).get(k).startRight, chIAPETInteractions.get(i).get(k).endRight,p300s.get(i).get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).get(w).start, p300s.get(i).get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).get(w).end))
                                        {
                                            for(int o = 0; o < enhancers.get(i).get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).size(); o++)
                                            {
                                                if(overlap(chIAPETInteractions.get(i).get(k).startRight, chIAPETInteractions.get(i).get(k).endRight, enhancers.get(i).get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).get(o).start, enhancers.get(i).get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).get(o).end))
                                                {
                                                    epp.get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).add(new EPP(enhancers.get(i).get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).get(o) ,promoter.get(Integer.parseInt(chIAPETInteractions.get(i).get(k).chromLeft.replace("chr", "")) - 1).get(l)));
                                                }
                                            } 
                                        }
                                    }
                                
                            
                        }
                    }
                }
                else if(chIAPETInteractions.get(i).get(k).chromLeft.contentEquals("chrX"))
                {
                    for(int l = 0; l < promoter.get(22).size(); l++)
                    {
                        
                    }
                }
                else if(chIAPETInteractions.get(i).get(k).chromLeft.contentEquals("chrY"))
                {
                    for(int l = 0; l < promoter.get(23).size(); l++)
                }
            }
         }
         
//System.out.println(chIAPETInteractions.get(i));
    }
    
}
