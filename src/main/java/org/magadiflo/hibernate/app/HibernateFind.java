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

        //Si se hacen varias consultas para un mismo id se hará una sola consulta SQL
        //Los resultados se traerán de la sesión de hibernate (como una caché)
        //Si fueran consultas con id diferentes ahí sí se haría consultas a la BD diferentes
        Cliente c2 = em.find(Cliente.class, id);
        System.out.println(c2);
        em.close();
    }
}