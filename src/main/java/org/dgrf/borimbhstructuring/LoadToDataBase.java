/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.borimbhstructuring;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.dgrf.borimbhstructuring.DAO.MainTextDAO;
import org.dgrf.borimbhstructuring.DAO.UbachaDAO;
import org.dgrf.borimbhstructuring.entities.Maintext;
import org.dgrf.borimbhstructuring.entities.MaintextPK;
import org.dgrf.borimbhstructuring.entities.Parva;
import org.dgrf.borimbhstructuring.entities.Ubacha;
import org.dgrf.borimbhstructuring.entities.Words;
import org.dgrf.borimbhstructuring.entities.WordsPK;

/**
 *
 * @author bhaduri
 */
public class LoadToDataBase {

    UbachaDAO ubachaDAO;
    EntityManagerFactory emf;

    public LoadToDataBase() {
        emf = Persistence.createEntityManagerFactory("org.dgrf_BoriMbhStructuring_jar_1.0-SNAPSHOTPU");
    }

    public void LoadMaintext() {

        MainTextDAO mainTextDAO = new MainTextDAO(emf);

        String csvFile = "/home/dgrfi/MEGA/MBHIndex/DevNagari/output/Mahabharat.csv";

        BufferedReader br = null;
        String line = "";

        int rowCount = 0;
        EntityManager em = mainTextDAO.getEntityManager();
        em.getTransaction().begin();
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                try {
                    Maintext maintext = createMainTextEntity(line);

                    em.persist(maintext);

                    if (rowCount % 1000 == 0) {
                        System.out.println("Committing " + rowCount);
                        em.flush();
                        em.clear();

                    }
                    if (rowCount > 152340) {
                        System.out.println(maintext.toString());
                    }
                } catch (Exception ex) {
                    Logger.getLogger(LoadToDataBase.class.getName()).log(Level.SEVERE, null, ex);
                }
                rowCount++;
                //System.out.println(parvaId+","+adhyayId+","+ubachaId+","+shlokaNum+","+shlokaLine+","+shlokaText+","+firstChar+","+endChar);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        em.flush();
        em.clear();
        em.getTransaction().commit();

    }

    public Maintext createMainTextEntity(String line) {
        String cvsSplitBy = ",";
        ubachaDAO = new UbachaDAO(emf);
        Date createDate = new Date();
        Ubacha ubacha;
        String parvaId;
        String adhyayId;
        int ubachaId;
        String ubachaName;
        String shlokaNum;
        String shlokaLine;
        String firstChar;
        String shlokaText;
        String endChar;
        String[] shlokaCSVLine = line.split(cvsSplitBy);
        parvaId = shlokaCSVLine[0];
        adhyayId = shlokaCSVLine[1];
        ubachaName = shlokaCSVLine[2];
        ubacha = ubachaDAO.getUbachaByName(ubachaName);
        ubachaId = ubacha.getId();
        shlokaNum = shlokaCSVLine[3];
        shlokaLine = shlokaCSVLine[4];
        shlokaText = shlokaCSVLine[5];
        firstChar = shlokaText.substring(0, 1);
        endChar = shlokaCSVLine[6];
        Maintext maintext = new Maintext();
        Parva parva = new Parva();
        MaintextPK maintextPK = new MaintextPK();
        maintextPK.setParvaId(Integer.parseInt(parvaId));
        maintextPK.setAdhyayid(Integer.parseInt(adhyayId));
        maintextPK.setShlokanum(Integer.parseInt(shlokaNum));
        maintextPK.setShlokaline(Integer.parseInt(shlokaLine));
        parva.setId(Integer.parseInt(parvaId));
        maintext.setParva(parva);
        maintext.setMaintextPK(maintextPK);
        maintext.setEndchar(endChar);
        maintext.setFirstchar(firstChar);
        maintext.setShlokatext(shlokaText);
        maintext.setUbachaId(ubacha);
        maintext.setLastupdatedts(createDate);
        return maintext;
    }

    public void loadWords() {
        MainTextDAO mainTextDAO = new MainTextDAO(emf);
        // start experiment
//        MaintextPK maintextPK = new MaintextPK(16, 7, 23, 1);
//        Maintext m = mainTextDAO.findMaintext(maintextPK);
//        List<Maintext> maintextAll = new ArrayList<>();
//        maintextAll.add(m);
        // end experiment
        List<Maintext> maintextAll = mainTextDAO.findMaintextEntities();
        EntityManager em = mainTextDAO.getEntityManager();
        em.getTransaction().begin();
        int rowCount = 0;
        for (Maintext maintext : maintextAll) {
            List<Words> wordsList = createWordsEntity(maintext);
            for (Words words : wordsList) {
                em.persist(words);
                if (rowCount % 1000 == 0) {
                    System.out.println("words commit " + rowCount);
                   
                    em.flush();
                    em.clear();
                    em.getTransaction().commit();
                    em.getTransaction().begin();

                }
//                if (rowCount >685000 && rowCount >685500) {
//                    System.out.println(words.toString());
//                }
                rowCount++;
            }
        }
        em.flush();
        em.clear();
        em.getTransaction().commit();
    }

    private List<Words> createWordsEntity(Maintext maintext) {

        String shloka = maintext.getShlokatext().trim();
        String[] shlokaWords = shloka.split("\\s+");
        List<Words> wordsList = new ArrayList<>();
        for (int i = 0; i < shlokaWords.length; i++) {

            Words words = new Words();
            WordsPK wordsPK = new WordsPK();

            wordsPK.setMaintextParvaId(maintext.getMaintextPK().getParvaId());
            wordsPK.setMaintextAdhyayid(maintext.getMaintextPK().getAdhyayid());
            wordsPK.setMaintextShlokanum(maintext.getMaintextPK().getShlokanum());
            wordsPK.setMaintextShlokaline(maintext.getMaintextPK().getShlokaline());
            wordsPK.setWordnum(i);
            words.setWordsPK(wordsPK);
            words.setMaintext(maintext);
            words.setFirstchar(Character.toString(shlokaWords[i].charAt(0)));
            words.setWordtext(shlokaWords[i]);

            wordsList.add(words);
        }
        return wordsList;
    }
}
