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
public class Feature 
{
    Integer start;
    Integer end;
    String chrom;
    
    Feature(Integer start, Integer end, String chrom)
    {
        this.start = start;
        this.end = end;
        this.chrom = chrom;
    }
}
