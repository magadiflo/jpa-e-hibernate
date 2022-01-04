package org.magadiflo.hibernate.app;

import jakarta.persistence.EntityManager;
import org.magadiflo.hibernate.app.entity.Cliente;
import org.magadiflo.hibernate.app.util.JpaUtil;

import java.util.List;

public class HibernateListar {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c").getResultList();
        clientes.forEach(System.out::println);
        em.close();


    }

}
