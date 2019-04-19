/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.borimbhstructuring;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.dgrf.borimbhstructuring.DAO.MainTextDAO;
import org.dgrf.borimbhstructuring.DAO.ParvaDAO;
import org.dgrf.borimbhstructuring.entities.Parva;

/**
 *
 * @author bhaduri
 */
public class DumpToFiles {
    EntityManagerFactory emf;
    PrintWriter writer;
    String flatTextDirectory;
    public DumpToFiles() {
        emf = Persistence.createEntityManagerFactory("org.dgrf_BoriMbhStructuring_jar_1.0-SNAPSHOTPU");
        flatTextDirectory = "/home/dgrfi/MEGA/MBHIndex/DevNagari/flattext/";
    }
    
    public void dumpParva() {
        MainTextDAO mainTextDAO = new MainTextDAO(emf);
        ParvaDAO parvaDAO = new ParvaDAO(emf);
        List<Parva> parvaList = parvaDAO.findParvaEntities();
        for (Parva parva : parvaList) {
            try {
                String parvaIdForFileName = String.format("%02d", parva.getId());
                String flatTextFilePath = flatTextDirectory+parvaIdForFileName+".txt";
                System.out.println(flatTextFilePath);
                writer = new PrintWriter(flatTextFilePath,"UTF-8");
                List<String>shlokaTextLines = mainTextDAO.getShlokaLines(parva.getId());
                for (String shlokaTextLine:shlokaTextLines) {
                    writer.println(shlokaTextLine+"।");
                }
                writer.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DumpToFiles.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(DumpToFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
        
//        for (Object[] shlokaLine:shlokaLines) {
//            String gheu = shlokaLine[0].toString();
//            System.out.println(gheu);
//        }
    }
}
