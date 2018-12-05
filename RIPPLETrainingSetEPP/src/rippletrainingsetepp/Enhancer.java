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
public class Enhancer 
{
    int [] range;
    boolean [] featureVector = new boolean[24];
    ArrayList<String> primer;
    
    Enhancer(int [] range, ArrayList<String> primer)
    {
        this.range = range;
        this.primer = primer;
    }
    Enhancer(int [] range)
    {
        this.range = range;
    }
}
