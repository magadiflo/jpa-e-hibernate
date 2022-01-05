package org.magadiflo.hibernate.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.magadiflo.hibernate.app.dominio.ClienteDTO;
import org.magadiflo.hibernate.app.entity.Cliente;
import org.magadiflo.hibernate.app.util.JpaUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class HibernateQL {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        System.out.println("=============== CONSULTAR TODOS ===============");
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("=============== CONSULTAR POR ID ===============");
        Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.id = :id", Cliente.class);
        query.setParameter("id", 3L);
        Cliente cliente = (Cliente) query.getSingleResult();
        System.out.println(cliente);

        System.out.println("=============== CONSULTAR SOLO EL NOMBRE DEL CLIENTE POR EL ID ===============");
        String nombreCliente = em.createQuery("SELECT c.nombre FROM Cliente c WHERE c.id = :id", String.class)
                .setParameter("id", 3L)
                .getSingleResult();
        System.out.println(nombreCliente);

        System.out.println("=============== CONSULTAR POR CAMPOS PERSONALIZADOS ===============");
        Object campos[] = em.createQuery("SELECT c.id, c.nombre, c.apellido FROM Cliente c WHERE c.id = :id", Object[].class)
                .setParameter("id", 3L)
                .getSingleResult();
        AtomicReference<Long> id = new AtomicReference<>((Long) campos[0]);
        String nombre = (String)campos[1];
        String apellido = (String)campos[2];
        System.out.println("id: " + id + ", nombre: " + nombre + ", apellido: " + apellido);

        System.out.println("=============== CONSULTAR POR CAMPOS PERSONALIZADOS LISTA ===============");
        List<Object[]> registros = em.createQuery("SELECT c.id, c.nombre, c.apellido FROM Cliente c", Object[].class)
                .getResultList();
        registros.forEach(reg -> {
            System.out.println("id: " + reg[0] + ", nombre: " + reg[1] + ", apellido: " + reg[2]);
        });

        System.out.println("=============== CONSULTAR POR CLIENTE Y FORMA DE PAGO ===============");
        registros = em.createQuery("SELECT c, c.formaPago FROM Cliente c", Object[].class)
                        .getResultList();
        registros.forEach(reg -> {
            Cliente c = (Cliente) reg[0];
            String formaPago = (String) reg[1];
            System.out.println("Forma pago: " + formaPago + "," + c);
        });

        System.out.println("=============== CONSULTAR QUE PUEBLA Y DEVUELVE OBJETO ENTITY DE CLASE PERSONALIZADA ===============");
        clientes = em.createQuery("SELECT new Cliente(c.nombre, c.apellido) FROM Cliente c", Cliente.class)
                .getResultList();
        clientes.forEach(System.out::println);

        //Importante agregar toda la ruta de la clase ClienteDTO, si no lanzará un error, ya que el ClienteDTO
        //como no es clase entity no lo va a encontrar en el contexto de persistencia
        System.out.println("=============== CONSULTAR QUE PUEBLA Y DEVUELVE OBJETO DTO DE CLASE PERSONALIZADA ===============");
        List<ClienteDTO> clientesDTO = em.createQuery("SELECT new org.magadiflo.hibernate.app.dominio.ClienteDTO(c.nombre, c.apellido) FROM Cliente c", ClienteDTO.class)
                .getResultList();
        clientesDTO.forEach(System.out::println);

        em.close();
    }
}
