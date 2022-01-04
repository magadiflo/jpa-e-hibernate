package org.magadiflo.hibernate.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.magadiflo.hibernate.app.entity.Cliente;
import org.magadiflo.hibernate.app.util.JpaUtil;

import java.util.List;

public class HibernateResultListWhere {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.formaPago = ?1", Cliente.class);
        query.setParameter(1, "debito");
        List<Cliente> clientes = query.getResultList();
        System.out.println(clientes);
        em.close();
    }
}