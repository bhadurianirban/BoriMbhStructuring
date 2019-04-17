/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.borimbhstructuring.DAO;

import javax.persistence.EntityManagerFactory;
import org.dgrf.borimbhstructuring.JPA.MaintextJpaController;

/**
 *
 * @author bhaduri
 */
public class MainTextDAO extends MaintextJpaController{

    public MainTextDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
}
