/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transcription.start.site;

/**
 *
 * @author Saadeeq
 */
public class Promoter 
{
    int [] range;
    String geneId;
    boolean [] featureVector = new boolean[24];
    
    Promoter(int [] range, String geneId)
    {
        this.range = range;
        this.geneId = geneId;
    }
}
