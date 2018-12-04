/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package im.pettrainingsetepp;

/**
 *
 * @author Saadeeq
 */
public class RNASeqData 
{
    String geneId;
    double fpkm;
    
    RNASeqData(String geneId, double fpkm)
    {
        this.geneId = geneId;
        this.fpkm = fpkm;
    }
}
