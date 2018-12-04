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
public class ChIAPETInteractions 
{
    String chromLeft;
    int startLeft;
    int endLeft;
    String chromRight;
    int startRight;
    int endRight;
    int PETCount;
    double qValue;
    double pValue;
    
    int chromNumToZeroIndex(String chromNum)
    {
        return Integer.parseInt(chromNum.replace("chr","")) - 1;
    }
}
