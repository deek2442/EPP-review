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
public class Feature
    {
        int[] range;
        String name;
        
        Feature(int [] range, String name)
        {
            this.range = range;
            this.name = name;
        }
    }
