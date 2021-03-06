package org.magadiflo.hibernate.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.magadiflo.hibernate.app.entity.Cliente;
import org.magadiflo.hibernate.app.util.JpaUtil;

public class HibernateSingleResultWhere {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.formaPago = ?1", Cliente.class);
        query.setParameter(1, "debito");
        query.setMaxResults(1);//Con esto nos aseguramos que traiga ├║nicamente el primer resultado que encuentre
        Cliente c = (Cliente) query.getSingleResult();
        System.out.println(c);
        em.close();
    }
}