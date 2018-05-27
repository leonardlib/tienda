/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Cliente;
import modelo.Direccion;
import modelo.Usuario;

/**
 *
 * @author leonardolirabecerra
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
        this.em = this.emf.createEntityManager();
        this.utx = this.em.getTransaction();
    }
    private EntityTransaction utx = null;
    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    public EntityManager getEntityManager() {
        return this.em;
    }

    public void create(Cliente cliente) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Direccion idDireccion = cliente.getIdDireccion();
            if (idDireccion != null) {
                idDireccion = em.getReference(idDireccion.getClass(), idDireccion.getId());
                cliente.setIdDireccion(idDireccion);
            }
            Usuario idUsuarioCreacion = cliente.getIdUsuarioCreacion();
            if (idUsuarioCreacion != null) {
                idUsuarioCreacion = em.getReference(idUsuarioCreacion.getClass(), idUsuarioCreacion.getId());
                cliente.setIdUsuarioCreacion(idUsuarioCreacion);
            }
            Usuario idUsuarioModificacion = cliente.getIdUsuarioModificacion();
            if (idUsuarioModificacion != null) {
                idUsuarioModificacion = em.getReference(idUsuarioModificacion.getClass(), idUsuarioModificacion.getId());
                cliente.setIdUsuarioModificacion(idUsuarioModificacion);
            }
            em.persist(cliente);
            if (idDireccion != null) {
                idDireccion.getClienteCollection().add(cliente);
                idDireccion = em.merge(idDireccion);
            }
            if (idUsuarioCreacion != null) {
                idUsuarioCreacion.getClienteCollection().add(cliente);
                idUsuarioCreacion = em.merge(idUsuarioCreacion);
            }
            if (idUsuarioModificacion != null) {
                idUsuarioModificacion.getClienteCollection().add(cliente);
                idUsuarioModificacion = em.merge(idUsuarioModificacion);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            Direccion idDireccionOld = persistentCliente.getIdDireccion();
            Direccion idDireccionNew = cliente.getIdDireccion();
            Usuario idUsuarioCreacionOld = persistentCliente.getIdUsuarioCreacion();
            Usuario idUsuarioCreacionNew = cliente.getIdUsuarioCreacion();
            Usuario idUsuarioModificacionOld = persistentCliente.getIdUsuarioModificacion();
            Usuario idUsuarioModificacionNew = cliente.getIdUsuarioModificacion();
            if (idDireccionNew != null) {
                idDireccionNew = em.getReference(idDireccionNew.getClass(), idDireccionNew.getId());
                cliente.setIdDireccion(idDireccionNew);
            }
            if (idUsuarioCreacionNew != null) {
                idUsuarioCreacionNew = em.getReference(idUsuarioCreacionNew.getClass(), idUsuarioCreacionNew.getId());
                cliente.setIdUsuarioCreacion(idUsuarioCreacionNew);
            }
            if (idUsuarioModificacionNew != null) {
                idUsuarioModificacionNew = em.getReference(idUsuarioModificacionNew.getClass(), idUsuarioModificacionNew.getId());
                cliente.setIdUsuarioModificacion(idUsuarioModificacionNew);
            }
            cliente = em.merge(cliente);
            if (idDireccionOld != null && !idDireccionOld.equals(idDireccionNew)) {
                idDireccionOld.getClienteCollection().remove(cliente);
                idDireccionOld = em.merge(idDireccionOld);
            }
            if (idDireccionNew != null && !idDireccionNew.equals(idDireccionOld)) {
                idDireccionNew.getClienteCollection().add(cliente);
                idDireccionNew = em.merge(idDireccionNew);
            }
            if (idUsuarioCreacionOld != null && !idUsuarioCreacionOld.equals(idUsuarioCreacionNew)) {
                idUsuarioCreacionOld.getClienteCollection().remove(cliente);
                idUsuarioCreacionOld = em.merge(idUsuarioCreacionOld);
            }
            if (idUsuarioCreacionNew != null && !idUsuarioCreacionNew.equals(idUsuarioCreacionOld)) {
                idUsuarioCreacionNew.getClienteCollection().add(cliente);
                idUsuarioCreacionNew = em.merge(idUsuarioCreacionNew);
            }
            if (idUsuarioModificacionOld != null && !idUsuarioModificacionOld.equals(idUsuarioModificacionNew)) {
                idUsuarioModificacionOld.getClienteCollection().remove(cliente);
                idUsuarioModificacionOld = em.merge(idUsuarioModificacionOld);
            }
            if (idUsuarioModificacionNew != null && !idUsuarioModificacionNew.equals(idUsuarioModificacionOld)) {
                idUsuarioModificacionNew.getClienteCollection().add(cliente);
                idUsuarioModificacionNew = em.merge(idUsuarioModificacionNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Direccion idDireccion = cliente.getIdDireccion();
            if (idDireccion != null) {
                idDireccion.getClienteCollection().remove(cliente);
                idDireccion = em.merge(idDireccion);
                em.remove(idDireccion);
            }
            Usuario idUsuarioCreacion = cliente.getIdUsuarioCreacion();
            if (idUsuarioCreacion != null) {
                idUsuarioCreacion.getClienteCollection().remove(cliente);
                idUsuarioCreacion = em.merge(idUsuarioCreacion);
            }
            Usuario idUsuarioModificacion = cliente.getIdUsuarioModificacion();
            if (idUsuarioModificacion != null) {
                idUsuarioModificacion.getClienteCollection().remove(cliente);
                idUsuarioModificacion = em.merge(idUsuarioModificacion);
            }
            em.remove(cliente);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            //em.close();
        }
    }

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            //em.close();
        }
    }
    
    public List<Cliente> buscarPorNombre(String nombre) {
        EntityManager em = this.emf.createEntityManager();
        List<Cliente> lista = null;
        
        utx.begin();
        try {
            String query = "SELECT * FROM cliente c WHERE c.nombre LIKE '%"+ nombre +"%'";
            System.out.println(query);
            Query consulta = em.createNativeQuery(query, Cliente.class);
            lista = consulta.getResultList();
            utx.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            utx.rollback();
        }
        
        return lista;
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
}
