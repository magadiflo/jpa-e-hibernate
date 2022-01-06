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


        em.close();
    }

}
