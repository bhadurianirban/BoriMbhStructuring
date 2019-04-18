/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.borimbhstructuring;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.dgrf.borimbhstructuring.DAO.MainTextDAO;

/**
 *
 * @author bhaduri
 */
public class DumpToFiles {
    EntityManagerFactory emf;

    public DumpToFiles() {
        emf = Persistence.createEntityManagerFactory("org.dgrf_BoriMbhStructuring_jar_1.0-SNAPSHOTPU");
    }
    
    public void dumpParva() {
        MainTextDAO mainTextDAO = new MainTextDAO(emf);
        List<Object[]> shlokaLines = mainTextDAO.getShlokaLines();
    }
}
