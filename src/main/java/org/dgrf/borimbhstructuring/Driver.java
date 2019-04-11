/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.borimbhstructuring;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bhaduri
 */
public class Driver {
    public static void main (String args[]) {
        String inputFile = "/home/dgrfi/MEGA/MBHIndex/DevNagari/VishwaParva.txt";
        String outputFile = "/home/dgrfi/MEGA/MBHIndex/DevNagari/VishwaParvaOut.txt";
        ProcessText pt = new ProcessText(inputFile,outputFile);
        //boolean b = pt.isShlokaEndLine("सूक्ष्मार्थन्याययुक्तस्य वेदार्थैर्भूषितस्य च ||१६||");
        //System.out.println(b);
        pt.processFile();
        
    }
    
}
