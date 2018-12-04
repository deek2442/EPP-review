/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rippletrainingsetepp;

import java.util.ArrayList;

/**
 *
 * @author Saadeeq
 */
public class Promoter 
{
    int [] range;
    String geneId;
    ArrayList<String> primer;
    boolean [] featureVector = new boolean[24];
    
    Promoter(int [] range, String geneId)
    {
        this.range = range;
        this.geneId = geneId;
    }
    Promoter(int [] range)
    {
        this.range = range;
    }
    Promoter(int [] range, ArrayList<String> primer)
    {
        this.range = range;
        this.primer = primer;
    }
}
