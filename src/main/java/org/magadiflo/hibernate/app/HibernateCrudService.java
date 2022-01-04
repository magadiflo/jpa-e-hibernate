package org.magadiflo.hibernate.app;

import jakarta.persistence.EntityManager;
import org.magadiflo.hibernate.app.entity.Cliente;
import org.magadiflo.hibernate.app.services.ClienteService;
import org.magadiflo.hibernate.app.services.ClienteServiceImpl;
import org.magadiflo.hibernate.app.util.JpaUtil;

import java.util.List;
import java.util.Optional;

public class HibernateCrudService {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        ClienteService service = new ClienteServiceImpl(em);

        System.out.println("================ LISTAR ==================");
        List<Cliente> clientes = service.listar();
        clientes.forEach(System.out::println);

        System.out.println("================ OBTENER POR ID ==================");
        Optional<Cliente> optional = service.porId(1L);
        optional.ifPresent(System.out::println);

        System.out.println("================ INSERTAR NUEVO CLIENTE ==================");
        Cliente cliente = new Cliente();
        cliente.setNombre("Hazael");
        cliente.setApellido("Díaz Ardiles");
        cliente.setFormaPago("credito");

        service.guardar(cliente);
        System.out.println("Cliente cuardado con éxito!");
        service.listar().forEach(System.out::println);

        System.out.println("================ EDITAR CLIENTE ==================");
        Long id = cliente.getId();
        optional = service.porId(id);
        optional.ifPresent(c -> {
            c.setFormaPago("mercado pago");
            service.guardar(c);
            System.out.println("Cliente editado con éxito!");
            service.listar().forEach(System.out::println);
        });

        System.out.println("================ ELIMINAR CLIENTE ==================");
        id = cliente.getId();
        optional = service.porId(id);
        optional.ifPresent(c -> {
            service.eliminar(c.getId());
            System.out.println("Cliente eliminado con éxito!");
            service.listar().forEach(System.out::println);
        });

        /* Otra forma de eliminar
        if(optional.isPresent()){
            service.eliminar(id);
        }*/

        //Llamamos fuera del service al método close(), ya que si lo cerramos dentro
        //del service ya no se podrá seguir usando las otras consultas si se requiere,
        //ya que quedaría cerrado
        em.close();
    }

}
