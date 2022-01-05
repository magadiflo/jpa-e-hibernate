package org.magadiflo.hibernate.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.magadiflo.hibernate.app.dominio.ClienteDTO;
import org.magadiflo.hibernate.app.entity.Cliente;
import org.magadiflo.hibernate.app.util.JpaUtil;

import java.util.Arrays;
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
        Long id = (Long) campos[0];
        String nombre = (String) campos[1];
        String apellido = (String) campos[2];
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

        System.out.println("=============== CONSULTAR CON NOMBRES DE CLIENTES ===============");
        List<String> nombres = em.createQuery("SELECT c.nombre FROM Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("=============== CONSULTAR CON NOMBRES ÚNICOS DE CLIENTES ===============");
        nombres = em.createQuery("SELECT DISTINCT(c.nombre) FROM Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("=============== CONSULTAR CON FORMAS DE PAGOS ÚNICAS ===============");
        List<String> formasPago = em.createQuery("SELECT DISTINCT(c.formaPago) FROM Cliente AS c", String.class).getResultList();
        formasPago.forEach(System.out::println);

        System.out.println("=============== CONSULTAR CON NÚMERO DE FORMAS DE PAGO ÚNICAS ===============");
        Long cantFormasPago = em.createQuery("SELECT COUNT(DISTINCT(c.formaPago)) FROM Cliente AS c", Long.class).getSingleResult();
        System.out.println("Número de formas de pago: " + cantFormasPago);

        System.out.println("=============== CONSULTAR CON NOMBRE Y APELLIDOS CONCATENADOS - FORMA 1 ===============");
        nombres = em.createQuery("SELECT CONCAT(c.nombre, ' ', c.apellido) AS nombreCompleto FROM Cliente AS c", String.class).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("=============== CONSULTAR CON NOMBRE Y APELLIDOS CONCATENADOS - FORMA 2 ===============");
        nombres = em.createQuery("SELECT c.nombre || ' ' || c.apellido AS nombreCompleto FROM Cliente AS c", String.class).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("=============== CONSULTAR CON NOMBRE Y APELLIDOS CONCATENADOS EN MAYÚSCULA ===============");
        nombres = em.createQuery("SELECT UPPER(CONCAT(c.nombre, ' ', c.apellido)) AS nombreCompleto FROM Cliente AS c", String.class).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("=============== CONSULTAR PARA BUSCAR POR NOMBRE  ===============");
        String param = "lic";
        clientes = em.createQuery("SELECT c FROM Cliente AS c WHERE c.nombre LIKE :parametro", Cliente.class)
                .setParameter("parametro", "%" + param + "%")
                .getResultList();
        clientes.forEach(System.out::println);

        System.out.println("=============== CONSULTAS POR RANGOS ===============");
        clientes = em.createQuery("SELECT c FROM Cliente AS c WHERE c.id BETWEEN 2 AND 5", Cliente.class).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("=============== CONSULTAS CON ORDEN ===============");
        clientes = em.createQuery("SELECT c FROM Cliente AS c ORDER BY c.nombre DESC", Cliente.class).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("=============== CONSULTA DEL TOTAL DE REGISTROS DE LA TABLA ===============");
        Long total = em.createQuery("SELECT COUNT(c) AS total FROM Cliente AS c", Long.class).getSingleResult();
        System.out.println("Total de registros: " + total);

        System.out.println("=============== CONSULTA DEL VALOR MÍNIMO DEL ID ===============");
        Long minimo = em.createQuery("SELECT MIN(c.id) AS minimo FROM Cliente AS c", Long.class).getSingleResult();
        System.out.println("Id mínimo: " + minimo);

        System.out.println("=============== CONSULTA DEL VALOR MÁXIMO DEL ID ===============");
        Long maximo = em.createQuery("SELECT MAX(c.id) AS maximo FROM Cliente AS c", Long.class).getSingleResult();
        System.out.println("Id máximo: " + maximo);

        System.out.println("=============== CONSULTA CON NOMBRE Y SU LARGO ===============");
        registros = em.createQuery("SELECT c.nombre, LENGTH(c.nombre)  FROM Cliente AS c", Object[].class).getResultList();
        registros.forEach(reg -> {
            System.out.println("nombre: " + reg[0] + ", largo: " + reg[1]);
        });

        System.out.println("=============== CONSULTA CON NOMBRE MÁS CORTO ===============");
        Integer min = em.createQuery("SELECT MIN(LENGTH(c.nombre))  FROM Cliente AS c", Integer.class).getSingleResult();
        System.out.println("El nombre más corto tiene " + min + " caracteres");

        System.out.println("=============== CONSULTA CON NOMBRE MÁS LARGO ===============");
        Integer max = em.createQuery("SELECT MAX(LENGTH(c.nombre))  FROM Cliente AS c", Integer.class).getSingleResult();
        System.out.println("El nombre más corto tiene " + max + " caracteres");

        System.out.println("=============== CONSULTAS RESUMEN FUNCIONES AGREGACIONES COUNT, MIN, MAX, AVG, SUM ===============");
        Object[] estadisticas = em.createQuery("SELECT MIN(c.id), MAX(c.id), SUM(c.id), COUNT(c.id), AVG(LENGTH(c.nombre)) FROM Cliente AS c", Object[].class).getSingleResult();
        System.out.println("Min: "+ estadisticas[0] + ", max: " + estadisticas[1] + ", sum: " + estadisticas[2] + ", count: " + estadisticas[3] + ", promedio: " + estadisticas[4]);

        System.out.println("=============== CONSULTA CON NOMBRE MÁS CORTO Y SU LARGO ===============");
        registros = em.createQuery("SELECT c.nombre, LENGTH(c.nombre) FROM Cliente AS c WHERE LENGTH(c.nombre) = (SELECT MIN(LENGTH(c.nombre)) FROM Cliente AS c)", Object[].class).getResultList();
        registros.forEach(reg -> {
            System.out.println("nombre: " + reg[0] + ", largo: " + reg[1]);
        });

        System.out.println("=============== CONSULTA PARA OBTENER EL ÚLTIMO REGISTRO ===============");
        Cliente ultimoCliente = em.createQuery("SELECT c FROM Cliente AS c WHERE c.id = (SELECT MAX(c.id) FROM Cliente AS c)", Cliente.class).getSingleResult();
        System.out.println(ultimoCliente);

        System.out.println("=============== CONSULTA WHERE IN ===============");
        clientes = em.createQuery("SELECT c FROM Cliente AS c WHERE c.id IN :ids", Cliente.class)
                .setParameter("ids", Arrays.asList(1L,3L,5L))
                .getResultList();
        clientes.forEach(System.out::println);

        em.close();
    }
}
