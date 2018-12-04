/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rippletrainingsetepp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Saadeeq
 */
public class RIPPLETrainingSetEPP 
{

    
    public static void main(String[] args) throws IOException 
    {
       BufferedReader br;
       BufferedWriter bw;
       ArrayList<ArrayList<ArrayList<int[]>>> cellLinePromoters = new ArrayList(4);
       ArrayList<ArrayList<ArrayList<int[]>>> cellLineEnhancers = new ArrayList(4);
       ArrayList<ArrayList<ArrayList<Enhancer>>> cellLineEnhancer = new ArrayList(4);
       ArrayList<ArrayList<ArrayList<Promoter>>> cellLinePromoter = new ArrayList(4);
       ArrayList<ArrayList<ArrayList<PrimerPairs>>> primerPairs = new ArrayList(4);
       ArrayList<ArrayList<Primer>> primers = new ArrayList(24);
       ArrayList<ArrayList<Primer>> primersREV = new ArrayList(24);
       ArrayList<ArrayList<EPP>> epp = new ArrayList(4);
       for(int i = 0; i < 24; i++)
       {
           primers.add(new ArrayList<>());
           primersREV.add(new ArrayList<>());
       }
       for(int p = 0; p < 4; p++)
       {
               cellLinePromoters.add(new ArrayList<>());
               cellLineEnhancers.add(new ArrayList<>());
               cellLineEnhancer.add(new ArrayList<>());
               cellLinePromoter.add(new ArrayList<>());
               primerPairs.add(new ArrayList<>());
               epp.add(new ArrayList<>());
               for(int i = 0; i < 24; i++)
               {
                   primerPairs.get(p).add(new ArrayList<>());
                   cellLinePromoters.get(p).add(new ArrayList<>());
                   cellLineEnhancers.get(p).add(new ArrayList<>());
                   cellLineEnhancer.get(p).add(new ArrayList<>());
                   cellLinePromoter.get(p).add(new ArrayList<>());
               }
       }
       for(int i = 0; i < 2; i++)
       {
           String mainDirectory = "C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\RIPPLE Enhancer and Promoters";
           if(i == 0)
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
           if(i == 0)
            br = new BufferedReader(new FileReader(cellLineDirectory + "\\Enhancers.bedgraph"));
           else
             br = new BufferedReader(new FileReader(cellLineDirectory + "\\Promoters.bedgraph"));
           String line = br.readLine();
           String lines[];
               while(line != null)
               {
                  lines = line.split("\t");
                  lines[0] = lines[0].replace("chr", "");
                  //System.out.println(line);
                  if(i == 0)
                  {
                      if(!lines[0].contains("X") && !lines[0].contains("Y"))
                        cellLineEnhancers.get(p).get(Integer.parseInt(lines[0])-1).add(new int[] {Integer.parseInt(lines[1]), Integer.parseInt(lines[2])});
                      else if(lines[0].contains("X"))
                        cellLineEnhancers.get(p).get(22).add(new int[] {Integer.parseInt(lines[1]), Integer.parseInt(lines[2])}); 
                      else if(lines[0].contains("Y"))
                        cellLineEnhancers.get(p).get(23).add(new int[] {Integer.parseInt(lines[1]), Integer.parseInt(lines[2])});  
                  }
                  else
                  {
                      if(!lines[0].contains("X") && !lines[0].contains("Y"))
                        cellLinePromoters.get(p).get(Integer.parseInt(lines[0])-1).add(new int[] {Integer.parseInt(lines[1]), Integer.parseInt(lines[2])});
                      else if(lines[0].contains("X"))
                        cellLinePromoters.get(p).get(22).add(new int[] {Integer.parseInt(lines[1]), Integer.parseInt(lines[2])});
                      else if(lines[0].contains("Y"))
                        cellLinePromoters.get(p).get(23).add(new int[] {Integer.parseInt(lines[1]), Integer.parseInt(lines[2])});
                      
                  }
                  line = br.readLine();
               }
           }
       }
       
       String ChromatinInteractionDataDirectory = "C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\Chromatin interaction Data";
     for(int p = 0; p < 4; p++)
     {
         String cellLineDirectory = null;
         switch(p)
            {
                case 0: 
                {
                  cellLineDirectory = ChromatinInteractionDataDirectory + "\\GM12878\\5C data\\peakList.bed";
                  break;
                }
                case 1:
                {
                   cellLineDirectory = ChromatinInteractionDataDirectory + "\\H1-hESC\\5C data\\peakList.bed"; 
                   break;
                }
                case 2:
                {
                   cellLineDirectory = ChromatinInteractionDataDirectory + "\\Hela-S3\\5C data\\peakList.bed"; 
                   break;
                }
                case 3:
                {
                    cellLineDirectory = ChromatinInteractionDataDirectory + "\\K562\\5C data\\peakList.bed";
                    break;
                }   
            }
            br = new BufferedReader(new FileReader(cellLineDirectory));
            String line = br.readLine();
           String lines[];
           String [] primerPair;
               while(line != null)
               {
                  lines = line.split("\t");
                  lines[0] = lines[0].replace("chr", "");
                  primerPair = lines[3].split("\\.");
                  //System.out.println(primerPair[1]);
                  //System.out.println(lines[0] + "\t" + lines[1] + "\t" + lines[2] + "   " + p);
                      if(!lines[0].contains("X") && !lines[0].contains("Y"))
                        primerPairs.get(p).get(Integer.parseInt(lines[0])-1).add(new PrimerPairs(primerPair[0], primerPair[1]));
                      else if(lines[0].contains("X"))
                        primerPairs.get(p).get(22).add(new PrimerPairs(primerPair[0], primerPair[1])); 
                      else if(lines[0].contains("Y"))
                        primerPairs.get(p).get(23).add(new PrimerPairs(primerPair[0], primerPair[1]));  
                  line = br.readLine();
               }
     }
     for(int p = 0; p < 4; p++)
     {
           switch (p) {
               case 0:
                   br = new BufferedReader(new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\Chromatin interaction Data\\5C primers\\ENm.hg19.forwards"));
                   break;
               case 1:
                   br = new BufferedReader(new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\Chromatin interaction Data\\5C primers\\ENm.hg19.reverses"));
                   break;
               case 2:
                   br = new BufferedReader(new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\Chromatin interaction Data\\5C primers\\ENr.hg19.forwards"));
                   break;
               default:
                   br = new BufferedReader(new FileReader("C:\\Users\\Joan\\Documents\\enhancer review data\\Code\\EPP-review\\Data\\Chromatin interaction Data\\5C primers\\ENr.hg19.reverses"));
                   break;
           }
         String line = br.readLine();
         String lines [];
         String primer[];
         while(line != null)
         {
             lines = line.split("\t");
             primer = lines[3].split("\\|");
             lines[0] = lines[0].replace("chr", "");
             //System.out.println(lines[3]);
             if( p % 2 == 0 )
             {    
             if(!lines[0].contains("X") && !lines[0].contains("Y"))
                primers.get(Integer.parseInt(lines[0]) - 1).add(new Primer(primer[0], new int []{Integer.parseInt(lines[1]), Integer.parseInt(lines[2])}));
             else if(lines[0].contains("X"))
                 primers.get(22).add(new Primer(primer[0], new int []{Integer.parseInt(lines[1]), Integer.parseInt(lines[2])}));
             else if(lines[0].contains("Y"))
                 primers.get(23).add(new Primer(primer[0], new int []{Integer.parseInt(lines[1]), Integer.parseInt(lines[2])}));
             }
             else
             {
                 if(!lines[0].contains("X") && !lines[0].contains("Y"))
                    primersREV.get(Integer.parseInt(lines[0]) - 1).add(new Primer(primer[0], new int []{Integer.parseInt(lines[1]), Integer.parseInt(lines[2])}));
                else if(lines[0].contains("X"))
                    primersREV.get(22).add(new Primer(primer[0], new int []{Integer.parseInt(lines[1]), Integer.parseInt(lines[2])}));
                else if(lines[0].contains("Y"))
                    primersREV.get(23).add(new Primer(primer[0], new int []{Integer.parseInt(lines[1]), Integer.parseInt(lines[2])}));
             }
             line = br.readLine();
         }
     }
     for(int p = 0; p < 4; p++)
     {
         int y = 0;
         for(int i = 0; i < 24; i++)
         {
             for(int k = 0; k < cellLineEnhancers.get(p).get(i).size(); k++)
             {
                 ArrayList<String> primerList = new ArrayList(4);
                 for(int l = 0; l < primers.get(i).size(); l++)
                 {
                    if(!(cellLineEnhancers.get(p).get(i).get(k)[0] > primers.get(i).get(l).range[1] ||  cellLineEnhancers.get(p).get(i).get(k)[1] < primers.get(i).get(l).range[0]))
                    {
                        primerList.add(primers.get(i).get(l).primer);
                    }
                 }
                 if(primerList.size() > 0)
                 {
                     cellLineEnhancer.get(p).get(i).add(new Enhancer(cellLineEnhancers.get(p).get(i).get(k), primerList));
                 }
                 if(primerList.size() > 0)
                     y++;
             }
             
         }
         System.out.println("y " + y);
     }
     for(int p = 0; p < 4; p++)
     {
         int x = 0;
         for(int i = 0; i < 24; i++)
         {
             for(int k = 0; k < cellLinePromoters.get(p).get(i).size(); k++)
             {
                 ArrayList<String> primerList = new ArrayList(5);
                 for(int l = 0; l < primersREV.get(i).size(); l++)
                 {
                    if(!(cellLinePromoters.get(p).get(i).get(k)[0] > primersREV.get(i).get(l).range[1] ||  cellLinePromoters.get(p).get(i).get(k)[1] < primersREV.get(i).get(l).range[0]))
                    {
                        primerList.add(primersREV.get(i).get(l).primer);
                    }
                 }
                 if(primerList.size() > 0)
                    cellLinePromoter.get(p).get(i).add(new Promoter(cellLinePromoters.get(p).get(i).get(k), primerList));
                 if(primerList.size() > 0)
                     x++;
             }
         }
         System.out.println("x " + x);
     }
       for(int p = 0; p < 4; p++)
       {
           for(int i = 0; i < 24; i++)
           {
               for(int q = 0; q < primerPairs.get(p).get(i).size(); q++)
               {
                   ArrayList<Enhancer> enhancers = new ArrayList(4);
                   ArrayList<Promoter> promoters = new ArrayList(4);
                   for(int l = 0; l < cellLineEnhancer.get(p).get(i).size(); l++)
                   {
                       for(int h = 0; h < cellLineEnhancer.get(p).get(i).get(l).primer.size(); h++)
                       { 
                           if(cellLineEnhancer.get(p).get(i).get(l).primer.get(h).contentEquals(primerPairs.get(p).get(i).get(q).primerFor))
                           {
                               enhancers.add(cellLineEnhancer.get(p).get(i).get(l));
                           }    
                       }
                   }
                   for(int l = 0; l < cellLinePromoter.get(p).get(i).size(); l++)
                   {
                       for(int h = 0; h < cellLinePromoter.get(p).get(i).get(l).primer.size(); h++)
                       { 
                           if(cellLinePromoter.get(p).get(i).get(l).primer.get(h).contentEquals(primerPairs.get(p).get(i).get(q).primerRev))
                           {
                               promoters.add(cellLinePromoter.get(p).get(i).get(l));
                           }    
                       }
                   }
                   if(enhancers.size() > 0 && promoters.size() > 0)
                   {
                       for(int f = 0; f < enhancers.size(); f++)
                       {
                           for(int g = 0; g < promoters.size(); g++)
                           {
                               if(Math.max(promoters.get(g).range[0],enhancers.get(f).range[0]) - Math.min(promoters.get(g).range[1], enhancers.get(f).range[1]) < 1000000)
                                epp.get(p).add(new EPP(enhancers.get(f), promoters.get(g)));
                           }
                       }
                   }
               }
           }
           System.out.println(epp.get(p).size());
       }
//     for(int p = 0; p < 4; p++)
//     {
//        for(int i = 0; i < 24; i++)
//        {
//            for(int q = 0; q < possibleEPP.get(p).get(i).size(); q++)
//            {
//                int enhancer[] = new int[2];
//                int promoter[] = new int[2];
//                if(possibleEPP.get(p).get(i).isEmpty())
//                {
//                    System.out.println("skip chr" + i + " for cell Line#" + p);
//                    break;
//                }
//                for(int l = 0; l < cellLineEnhancers.get(p).get(i).size(); l++)
//                {
//                    if(!(cellLineEnhancers.get(p).get(i).get(l)[0] > possibleEPP.get(p).get(i).get(q)[1] ||  cellLineEnhancers.get(p).get(i).get(l)[1] < possibleEPP.get(p).get(i).get(q)[0]))
//                    {
//                        //if(!(enhancer[0] == 0 && enhancer[1] == 0))
//                            //System.out.println("multiple enhancers overlap");
//                        enhancer = cellLineEnhancers.get(p).get(i).get(l);
//                    }
//                }
//                for(int l = 0; l < cellLinePromoters.get(p).get(i).size(); l++)
//                {
//                    if(!(cellLinePromoters.get(p).get(i).get(l)[0] > possibleEPP.get(p).get(i).get(q)[1] ||  cellLinePromoters.get(p).get(i).get(l)[1] < possibleEPP.get(p).get(i).get(q)[0]))
//                    {
//                        //if(!(promoter[0] == 0 && promoter[1] == 0))
//                            //System.out.println("multiple promoters overlap");
//                        promoter = cellLinePromoters.get(p).get(i).get(l);
//                    }
//                }
//                if(!(promoter[0] == 0 && promoter[1] == 0) && !(enhancer[0] == 0 && enhancer[1] == 0))
//                {
//                    epp.get(p).add(new EPP(new Enhancer(enhancer),new Promoter(promoter)));
//                }
//                
//            }
//        }
//       
//        System.out.println("EPP#" + epp.get(p).size() + " cell line#"+p);
//     }   
    }
    
}
