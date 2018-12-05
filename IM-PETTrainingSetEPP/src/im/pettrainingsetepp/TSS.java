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
public class TSS 
{
    Integer start;
    Integer end;
    String geneID;
    String chr;
    
    TSS(Integer start, String geneID)
    {
        this.start = start;
        this.geneID = geneID;
    }
    TSS(Integer start, String geneID, Integer end)
    {
        this.start = start;
        this.geneID = geneID;
        this.end = end;
    }
    TSS(Integer start, String geneID, Integer end, String chr)
    {
        this.start = start;
        this.geneID = geneID;
        this.end = end;
        this.chr = chr;
    }
}
