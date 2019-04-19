/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.borimbhstructuring.DAO;

import javax.persistence.EntityManagerFactory;
import org.dgrf.borimbhstructuring.JPA.ParvaJpaController;

/**
 *
 * @author dgrfi
 */
public class ParvaDAO extends ParvaJpaController{

    public ParvaDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
}
