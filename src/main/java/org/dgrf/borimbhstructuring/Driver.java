/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.borimbhstructuring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bhaduri
 */
public class Driver {

    public static void main(String args[]) {
        String inputDirectory = "/home/dgrfi/MEGA/MBHIndex/DevNagari/input";
        String outputDirectory = "/home/dgrfi/MEGA/MBHIndex/DevNagari/output";
        String inputFilePath = "";
        String outputFilePath = "";
        String errorFilePath = outputDirectory + File.separator + "error.txt";
        PrintWriter errorWriter;
        try {
            errorWriter = new PrintWriter(errorFilePath, "UTF-8");

            File inputFolder = new File(inputDirectory);
            if (inputFolder.isDirectory()) {
                File[] listOfInputFiles = inputFolder.listFiles();
                for (File inputFile : listOfInputFiles) {
                    if (inputFile.isFile()) {
                        String prefix = inputFile.getName().substring(0, 2);
                        int parvaId = Integer.parseInt(prefix);
                        inputFilePath = inputFile.getPath();
                        String inputFileName = inputFile.getName();
                        String outputFileName = inputFileName.replaceAll(".txt", ".csv");
                        outputFilePath = outputDirectory + File.separator + outputFileName;

                        System.out.println(inputFilePath + " " + outputFilePath + " " + parvaId);
                        ProcessText pt = new ProcessText(inputFilePath, outputFilePath, errorWriter, parvaId);
                        pt.processFile();
                        //System.out.println(inputFile.getParent());
                    }
                }
            } else {
                System.out.println("Input path is not a folder");
            }
            errorWriter.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
//        ProcessText pt = new ProcessText(inputFile,outputFile,2);
//        pt.processFile();

    }

}
