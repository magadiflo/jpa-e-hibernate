package org.magadiflo.hibernate.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.magadiflo.hibernate.app.entity.Cliente;
import org.magadiflo.hibernate.app.util.JpaUtil;

public class HibernateFind {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        Long id = 3L;
        Cliente c = em.find(Cliente.class, id); //find(), siempre hace la búsqueda por la PK
        System.out.println(c);
    }
}