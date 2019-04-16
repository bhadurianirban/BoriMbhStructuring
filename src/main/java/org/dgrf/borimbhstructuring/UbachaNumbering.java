/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.borimbhstructuring;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.dgrf.borimbhstructuring.DAO.UbachaDAO;
import org.dgrf.borimbhstructuring.entities.Ubacha;

/**
 *
 * @author bhaduri
 */
public class UbachaNumbering {
    public void UbachaNumbering() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.dgrf_BoriMbhStructuring_jar_1.0-SNAPSHOTPU");
        UbachaDAO ubachaDAO = new UbachaDAO(emf);
        Ubacha  ubacha = ubachaDAO.getUbachaByName("अश्मोवाच");
        System.out.println(ubacha.getId());
    }
    
}
