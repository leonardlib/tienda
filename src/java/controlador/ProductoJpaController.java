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
import modelo.Producto;
import modelo.Usuario;

/**
 *
 * @author leonardolirabecerra
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
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

    public void create(Producto producto) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario idUsuarioCreacion = producto.getIdUsuarioCreacion();
            if (idUsuarioCreacion != null) {
                idUsuarioCreacion = em.getReference(idUsuarioCreacion.getClass(), idUsuarioCreacion.getId());
                producto.setIdUsuarioCreacion(idUsuarioCreacion);
            }
            Usuario idUsuarioModificacion = producto.getIdUsuarioModificacion();
            if (idUsuarioModificacion != null) {
                idUsuarioModificacion = em.getReference(idUsuarioModificacion.getClass(), idUsuarioModificacion.getId());
                producto.setIdUsuarioModificacion(idUsuarioModificacion);
            }
            em.persist(producto);
            if (idUsuarioCreacion != null) {
                idUsuarioCreacion.getProductoCollection().add(producto);
                idUsuarioCreacion = em.merge(idUsuarioCreacion);
            }
            if (idUsuarioModificacion != null) {
                idUsuarioModificacion.getProductoCollection().add(producto);
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

    public void edit(Producto producto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            Usuario idUsuarioCreacionOld = persistentProducto.getIdUsuarioCreacion();
            Usuario idUsuarioCreacionNew = producto.getIdUsuarioCreacion();
            Usuario idUsuarioModificacionOld = persistentProducto.getIdUsuarioModificacion();
            Usuario idUsuarioModificacionNew = producto.getIdUsuarioModificacion();
            if (idUsuarioCreacionNew != null) {
                idUsuarioCreacionNew = em.getReference(idUsuarioCreacionNew.getClass(), idUsuarioCreacionNew.getId());
                producto.setIdUsuarioCreacion(idUsuarioCreacionNew);
            }
            if (idUsuarioModificacionNew != null) {
                idUsuarioModificacionNew = em.getReference(idUsuarioModificacionNew.getClass(), idUsuarioModificacionNew.getId());
                producto.setIdUsuarioModificacion(idUsuarioModificacionNew);
            }
            producto = em.merge(producto);
            if (idUsuarioCreacionOld != null && !idUsuarioCreacionOld.equals(idUsuarioCreacionNew)) {
                idUsuarioCreacionOld.getProductoCollection().remove(producto);
                idUsuarioCreacionOld = em.merge(idUsuarioCreacionOld);
            }
            if (idUsuarioCreacionNew != null && !idUsuarioCreacionNew.equals(idUsuarioCreacionOld)) {
                idUsuarioCreacionNew.getProductoCollection().add(producto);
                idUsuarioCreacionNew = em.merge(idUsuarioCreacionNew);
            }
            if (idUsuarioModificacionOld != null && !idUsuarioModificacionOld.equals(idUsuarioModificacionNew)) {
                idUsuarioModificacionOld.getProductoCollection().remove(producto);
                idUsuarioModificacionOld = em.merge(idUsuarioModificacionOld);
            }
            if (idUsuarioModificacionNew != null && !idUsuarioModificacionNew.equals(idUsuarioModificacionOld)) {
                idUsuarioModificacionNew.getProductoCollection().add(producto);
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
                Integer id = producto.getId();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuarioCreacion = producto.getIdUsuarioCreacion();
            if (idUsuarioCreacion != null) {
                idUsuarioCreacion.getProductoCollection().remove(producto);
                idUsuarioCreacion = em.merge(idUsuarioCreacion);
            }
            Usuario idUsuarioModificacion = producto.getIdUsuarioModificacion();
            if (idUsuarioModificacion != null) {
                idUsuarioModificacion.getProductoCollection().remove(producto);
                idUsuarioModificacion = em.merge(idUsuarioModificacion);
            }
            em.remove(producto);
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

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            //em.close();
        }
    }
    
    public List<Producto> buscarPorNombre(String nombre) {
        EntityManager em = this.emf.createEntityManager();
        List<Producto> lista = null;
        
        utx.begin();
        try {
            String query = "SELECT * FROM producto p WHERE p.nombre LIKE '%"+ nombre +"%'";
            System.out.println(query);
            Query consulta = em.createNativeQuery(query, Producto.class);
            lista = consulta.getResultList();
            utx.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            utx.rollback();
        }
        
        return lista;
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
}
