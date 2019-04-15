/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.borimbhstructuring;

import java.io.File;

/**
 *
 * @author bhaduri
 */
public class Driver {
    public static void main (String args[]) {
        String inputDirectory = "/home/bhaduri/MEGA/MBHIndex/DevNagari/input";
        String outputDirectory = "/home/bhaduri/MEGA/MBHIndex/DevNagari/output";
        String inputFilePath="";
        String outputFilePath="";
        File inputFolder = new File (inputDirectory);
        if (inputFolder.isDirectory()) {
            File[] listOfInputFiles = inputFolder.listFiles();
            for (File inputFile:listOfInputFiles) {
                if (inputFile.isFile()) {
                    String prefix  = inputFile.getName().substring(0, 2);
                    int parvaId = Integer.parseInt(prefix);
                    inputFilePath = inputFile.getPath();
                    String inputFileName = inputFile.getName();
                    String outputFileName = inputFileName.replaceAll(".txt", ".csv");
                    outputFilePath = outputDirectory+File.separator+outputFileName;
                    System.out.println(inputFilePath+" "+outputFilePath+" "+parvaId);
                    ProcessText pt = new ProcessText(inputFilePath, outputFilePath, parvaId);
                    pt.processFile();
                    //System.out.println(inputFile.getParent());
                }
            }
        } else {
            System.out.println("Input path is not a folder");
        }
//        ProcessText pt = new ProcessText(inputFile,outputFile,2);
//        pt.processFile();
        
    }
    
}
