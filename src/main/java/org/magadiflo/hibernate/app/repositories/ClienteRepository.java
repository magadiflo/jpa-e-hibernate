package org.magadiflo.hibernate.app.repositories;

import jakarta.persistence.EntityManager;
import org.magadiflo.hibernate.app.entity.Cliente;

import java.util.List;

public class ClienteRepository implements CrudRepository<Cliente> {

    private EntityManager em;

    public ClienteRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Cliente> listar() {
        return this.em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    @Override
    public Cliente porId(Long id) {
        return this.em.find(Cliente.class, id);
    }

    @Override
    public void guardar(Cliente cliente) {
        if (cliente.getId() != null && cliente.getId() > 0) {
            this.em.merge(cliente);
        } else {
            this.em.persist(cliente);
        }
    }

    @Override
    public void eliminar(Long id) {
        Cliente cliente = this.porId(id);
        this.em.remove(cliente);
    }

}
