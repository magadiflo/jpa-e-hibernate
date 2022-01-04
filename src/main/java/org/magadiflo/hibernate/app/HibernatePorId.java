package org.magadiflo.hibernate.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.magadiflo.hibernate.app.entity.Cliente;
import org.magadiflo.hibernate.app.util.JpaUtil;

public class HibernatePorId {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.id = ?1", Cliente.class);
        Long id = 2L;
        query.setParameter(1, id);
        Cliente c = (Cliente) query.getSingleResult();
        System.out.println(c);
    }
}