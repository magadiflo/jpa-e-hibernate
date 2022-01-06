package org.magadiflo.hibernate.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.magadiflo.hibernate.app.entity.Cliente;
import org.magadiflo.hibernate.app.util.JpaUtil;

import java.util.Arrays;
import java.util.List;

public class HibernateCriteria {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();

        System.out.println("========================== LISTAR CLIENTES ==========================");
        CriteriaBuilder criteria = em.getCriteriaBuilder();
        CriteriaQuery<Cliente> query = criteria.createQuery(Cliente.class);
        Root<Cliente> from = query.from(Cliente.class);
        query.select(from);
        List<Cliente> clientes = em.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========================== LISTAR WHERE EQUALS ==========================");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        ParameterExpression<String> nombreParam = criteria.parameter(String.class, "formaPago");
        query.select(from).where(criteria.equal(from.get("formaPago"), nombreParam));
        clientes = em.createQuery(query).setParameter("formaPago", "credito").getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========================== USANDO WHERE LIKE PARA BUSCAR CLIENTES POR NOMBRE ==========================");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        ParameterExpression<String> nombreParamLike = criteria.parameter(String.class, "nombreParam");
        query.select(from).where(criteria.like(criteria.upper(from.get("nombre")), criteria.upper(nombreParamLike)));
        clientes = em.createQuery(query).setParameter("nombreParam", "%ar%").getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========================== USANDO WHERE BETWEEN PARA RANGOS ==========================");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).where(criteria.between(from.get("id"), 2L, 6L));
        clientes = em.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========================== CONSULTA WHERE IN ==========================");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        ParameterExpression<List> listParam = criteria.parameter(List.class, "nombres");
        query.select(from).where(from.get("nombre").in(listParam));
        clientes = em.createQuery(query).setParameter("nombres", Arrays.asList("Alicia", "Gabriel", "Raúl")).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========================== FILTRAR USANDO PREDICADOS MAYOR QUE O MAYOR IGUAL QUE ==========================");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).where(criteria.ge(from.get("id"), 3L)); //id >= 3L
        clientes = em.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========================== FILTRAR USANDO PREDICADOS MAYOR QUE ==========================");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).where(criteria.gt(criteria.length(from.get("nombre")), 5L)); //longitud > 5L
        clientes = em.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========================== FILTRAR USANDO PREDICADOS MENOR IGUAL QUE ==========================");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).where(criteria.le(criteria.length(from.get("nombre")), 5L)); //longitud <= 4L
        clientes = em.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========================== FILTRAR USANDO PREDICADOS MENOR QUE ==========================");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).where(criteria.lt(criteria.length(from.get("nombre")), 4L)); //longitud < 4L
        clientes = em.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========================== CONSULTA CON LOS PREDICADOS CONJUNCIÓN AND Y DISYUNCIÓN OR ==========================");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        Predicate porNombre = criteria.equal(from.get("nombre"), "Alicia");
        Predicate porFormaPago = criteria.equal(from.get("formaPago"), "credito");
        Predicate p3 = criteria.ge(from.get("id"), 4L);
        query.select(from).where(criteria.and(p3, criteria.or(porNombre, porFormaPago)));
        clientes = em.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========================== CONSULTA CON ORDER BY ASC - DESC ==========================");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).orderBy(criteria.asc(from.get("nombre")), criteria.desc(from.get("apellido")));
        clientes = em.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========================== CONSULTA POR ID ==========================");
        query = criteria.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        ParameterExpression<Long> idParam = criteria.parameter(Long.class, "id");
        query.select(from).where(criteria.equal(from.get("id"), idParam));
        Cliente cliente = em.createQuery(query).setParameter("id", 3L).getSingleResult();
        System.out.println(cliente);

        System.out.println("========================== CONSULTA SOLO EL NOMBRE DE LOS CLIENTES ==========================");
        CriteriaQuery<String> queryString = criteria.createQuery(String.class);
        from = queryString.from(Cliente.class);
        queryString.select(from.get("nombre"));
        List<String> nombres = em.createQuery(queryString).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("========================== CONSULTA SOLO EL NOMBRE (EN MAYÚSCULA) DE LOS CLIENTES ÚNICOS CON DISTINCT ==========================");
        queryString = criteria.createQuery(String.class);
        from = queryString.from(Cliente.class);
        queryString.select(criteria.upper(from.get("nombre"))).distinct(true);
        nombres = em.createQuery(queryString).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("========================== CONSULTA POR NOMBRES Y APELLIDOS CONCATENADOS ==========================");
        queryString = criteria.createQuery(String.class);
        from = queryString.from(Cliente.class);
        queryString.select(criteria.concat(criteria.concat(from.get("nombre"), " "), from.get("apellido")));
        nombres = em.createQuery(queryString).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("========================== CONSULTA DE CAMPOS PERSONALIZADOS DEL ENTITY CLIENTE CON WHERE ==========================");
        CriteriaQuery<Object[]> queryObject = criteria.createQuery(Object[].class);
        from = queryObject.from(Cliente.class);
        queryObject.multiselect(from.get("id"), from.get("nombre"), from.get("apellido")).where(criteria.like(from.get("nombre"), "%al%"));
        List<Object[]> registros = em.createQuery(queryObject).getResultList();
        registros.forEach(reg -> {
            System.out.println("id: " + reg[0] + ", nombre: " + reg[1] + ", apellido: " + reg[2]);
        });

        System.out.println("========================== CONSULTA DE CAMPOS PERSONALIZADOS DEL ENTITY CLIENTE CON WHERE ID ==========================");
        queryObject = criteria.createQuery(Object[].class);
        from = queryObject.from(Cliente.class);
        queryObject.multiselect(from.get("id"), from.get("nombre"), from.get("apellido")).where(criteria.equal(from.get("id"), 1L));
        Object[] registro = em.createQuery(queryObject).getSingleResult();
        System.out.println("id: " + registro[0] + ", nombre: " + registro[1] + ", apellido: " + registro[2]);

        System.out.println("========================== CONTRAR REGISTROS DE LA CONSULTA CON COUNT ==========================");
        CriteriaQuery<Long> queryLong = criteria.createQuery(Long.class);
        from = queryLong.from(Cliente.class);
        queryLong.select(criteria.count(from.get("id")));
        Long count = em.createQuery(queryLong).getSingleResult();
        System.out.println("cantidad: " + count);

        System.out.println("========================== SUMAR DATOS DE ALGÚN DATO DE LA TABLA ==========================");
        queryLong = criteria.createQuery(Long.class);
        from = queryLong.from(Cliente.class);
        queryLong.select(criteria.sum(from.get("id")));
        Long sum = em.createQuery(queryLong).getSingleResult();
        System.out.println("Suma: " + sum);

        System.out.println("========================== CONSULTA CON EL MÁXIMO ID ==========================");
        queryLong = criteria.createQuery(Long.class);
        from = queryLong.from(Cliente.class);
        queryLong.select(criteria.max(from.get("id")));
        Long max = em.createQuery(queryLong).getSingleResult();
        System.out.println("Máximo: " + max);

        System.out.println("========================== EJEMPLO VARIOS RESULTADOS DE FUNCIONES DE AGREGACIÓN EN UNA SOLA CONSULTA ==========================");
        queryObject = criteria.createQuery(Object[].class);
        from = queryObject.from(Cliente.class);
        queryObject.multiselect(
                criteria.count(from.get("id")),
                criteria.sum(from.get("id")),
                criteria.max(from.get("id")),
                criteria.min(from.get("id")));
        registro = em.createQuery(queryObject).getSingleResult();
        System.out.println("count: " + registro[0] + ", sum: " + registro[1] + ", max: " + registro[2] + ", min: " + registro[3]);

        em.close();
    }

}
