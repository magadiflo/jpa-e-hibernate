package org.magadiflo.hibernate.app.services;

import jakarta.persistence.EntityManager;
import org.magadiflo.hibernate.app.entity.Cliente;
import org.magadiflo.hibernate.app.repositories.ClienteRepository;
import org.magadiflo.hibernate.app.repositories.CrudRepository;

import java.util.List;
import java.util.Optional;

public class ClienteServiceImpl implements ClienteService{

    private EntityManager em;
    private CrudRepository<Cliente> repository;

    public ClienteServiceImpl(EntityManager em) {
        this.em = em;
        this.repository = new ClienteRepository(this.em);
    }

    @Override
    public List<Cliente> listar() {
        return this.repository.listar();
    }

    @Override
    public Optional<Cliente> porId(Long id) {
        //Convertimos un objeto normal (El cliente que es devuelto por el this.repository.porId(id)) en un optional.
        //Y si en caso el objeto sea null es que usamos ofNullable()
        return Optional.ofNullable(this.repository.porId(id));
    }

    @Override
    public void guardar(Cliente cliente) {
        try {
            this.em.getTransaction().begin();
            this.repository.guardar(cliente);
            this.em.getTransaction().commit();
        } catch (Exception e){
            this.em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            this.em.getTransaction().begin();
            this.repository.eliminar(id);
            this.em.getTransaction().commit();
        } catch (Exception e){
            this.em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

}
