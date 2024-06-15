/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.UsuarioOperador;

public class UsuarioOperadorJpaController {
    private EntityManagerFactory emf = null;

    public UsuarioOperadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public UsuarioOperador findUsuario(String login, String senha){
         EntityManager em = getEntityManager();
         try {
             TypedQuery<UsuarioOperador> query = em.createQuery(
                    "SELECT u FROM UsuarioOperador u WHERE u.nome = :nome AND u.senha = :senha", UsuarioOperador.class);
            query.setParameter("nome", login);
            query.setParameter("senha", senha);

            try {
                return query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        } finally {
            em.close();
        }
         
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
