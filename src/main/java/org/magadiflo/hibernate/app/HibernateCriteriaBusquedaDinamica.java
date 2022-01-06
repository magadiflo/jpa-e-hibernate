package org.magadiflo.hibernate.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.magadiflo.hibernate.app.entity.Cliente;
import org.magadiflo.hibernate.app.util.JpaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HibernateCriteriaBusquedaDinamica {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Filtro para nombre: ");
        String nombre = sc.nextLine();

        System.out.println("Filtro para el apellido: ");
        String apellido = sc.nextLine();

        System.out.println("Filtro por la forma de pago: ");
        String formaPago = sc.nextLine();

        EntityManager em = JpaUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);
        Root<Cliente> from = query.from(Cliente.class);

        List<Predicate> condiciones = new ArrayList<>();
        if(nombre != null && !nombre.trim().isEmpty()){
            condiciones.add(cb.equal(from.get("nombre"), nombre));
        }

        if(apellido != null && !apellido.trim().isEmpty()){
            condiciones.add(cb.equal(from.get("apellido"), apellido));
        }

        if(formaPago != null && !formaPago.trim().isEmpty()){
            condiciones.add(cb.equal(from.get("formaPago"), formaPago));
        }

        query.select(from).where(cb.and(condiciones.toArray(new Predicate[condiciones.size()])));

        List<Cliente> clientes = em.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        em.close();
    }
}
