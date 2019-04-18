/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.borimbhstructuring.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.dgrf.borimbhstructuring.JPA.MaintextJpaController;

/**
 *
 * @author bhaduri
 */
public class MainTextDAO extends MaintextJpaController {

    public MainTextDAO(EntityManagerFactory emf) {
        super(emf);
    }

    public List<Object[]> getShlokaLines() {
        EntityManager em = getEntityManager();
        Query q = em.createNativeQuery("SELECT  GROUP_CONCAT(shlokatext  ORDER BY shlokaline SEPARATOR ' ') as s  from maintext where parva_id = ?1 GROUP by adhyayid, shlokanum ");
        q.setParameter(1, 1);
        List<Object[]> shlokaLines = q.getResultList();

        for (Object[] shlokaLine : shlokaLines) {
            System.out.println(shlokaLine);
        }
        return shlokaLines;
    }

}

