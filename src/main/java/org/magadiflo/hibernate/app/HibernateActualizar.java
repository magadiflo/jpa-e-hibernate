package org.magadiflo.hibernate.app;

import jakarta.persistence.EntityManager;
import org.magadiflo.hibernate.app.entity.Cliente;
import org.magadiflo.hibernate.app.util.JpaUtil;

public class HibernateActualizar {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Cliente cliente = new Cliente();
            cliente.setId(6L);
            cliente.setNombre("Hazael");
            cliente.setApellido("Díaz Ardiles");
            cliente.setFormaPago("debito");

            em.merge(cliente);
            em.getTransaction().commit();

            cliente = em.find(Cliente.class, cliente.getId());
            System.out.println("Cliente actualizado: " + cliente);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
