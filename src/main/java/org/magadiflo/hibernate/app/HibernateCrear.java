package org.magadiflo.hibernate.app;

import jakarta.persistence.EntityManager;
import org.magadiflo.hibernate.app.entity.Cliente;
import org.magadiflo.hibernate.app.util.JpaUtil;

public class HibernateCrear {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Cliente cliente = new Cliente();
            cliente.setNombre("Gahella");
            cliente.setApellido("Díaz Ardiles");
            cliente.setFormaPago("credito");

            em.persist(cliente);
            em.getTransaction().commit();

            System.out.println("El id del cliente registrado: " + cliente.getId());
            cliente = em.find(Cliente.class, cliente.getId());
            System.out.println(cliente);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}