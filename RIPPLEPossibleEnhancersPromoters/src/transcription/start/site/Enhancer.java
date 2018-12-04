/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transcription.start.site;

/**
 *
 * @author Joan
 */
public class Enhancer 
{
    int [] range;
    boolean [] featureVector = new boolean[24];
    
    Enhancer(int [] range)
    {
        this.range = range;
    }
    Enhancer(int [] range, boolean [] featureVector)
    {
        this.range = range;
        this.featureVector = featureVector;
    }
}
