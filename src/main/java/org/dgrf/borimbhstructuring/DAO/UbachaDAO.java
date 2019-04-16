/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.borimbhstructuring.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.dgrf.borimbhstructuring.JPA.UbachaJpaController;
import org.dgrf.borimbhstructuring.entities.Ubacha;

/**
 *
 * @author bhaduri
 */
public class UbachaDAO extends UbachaJpaController{

    public UbachaDAO(EntityManagerFactory emf) {
        super(emf);
    }
    public Ubacha getUbachaByName(String name) {
        EntityManager em = getEntityManager();
        TypedQuery<Ubacha> query = em.createNamedQuery("Ubacha.findByName", Ubacha.class);
        query.setParameter("name", name);
        List<Ubacha> ubachaList = query.getResultList();
        return ubachaList.get(0);
    }
    
}
