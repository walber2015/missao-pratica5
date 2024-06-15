/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import model.Pessoa;
import model.Produto;

public class PessoaJpaController {

    private EntityManagerFactory emf;

    public PessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public Pessoa findPessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Pessoa> query = em.createQuery("SELECT p FROM Pessoa p WHERE p.idPessoa = :id", Pessoa.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } finally {
            em.close();
        }

    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}
