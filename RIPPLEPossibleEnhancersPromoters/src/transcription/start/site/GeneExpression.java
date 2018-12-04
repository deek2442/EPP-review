/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transcription.start.site;

/**
 *
 * @author Saadeeq Duncanson
 */
public class GeneExpression 
{
    String gene;
    double FPKM;
    
    GeneExpression(String gene, double FPKM)
    {
        this.gene = gene;
        this.FPKM = FPKM;
    }
}
