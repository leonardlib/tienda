/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.TipoUsuario;
import modelo.Usuario;
import modelo.Cliente;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import modelo.Producto;

/**
 *
 * @author leonardolirabecerra
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
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

    public void create(Usuario usuario) throws RollbackFailureException, Exception {
        if (usuario.getClienteCollection() == null) {
            usuario.setClienteCollection(new ArrayList<Cliente>());
        }
        if (usuario.getClienteCollection1() == null) {
            usuario.setClienteCollection1(new ArrayList<Cliente>());
        }
        if (usuario.getUsuarioCollection() == null) {
            usuario.setUsuarioCollection(new ArrayList<Usuario>());
        }
        if (usuario.getUsuarioCollection1() == null) {
            usuario.setUsuarioCollection1(new ArrayList<Usuario>());
        }
        if (usuario.getProductoCollection() == null) {
            usuario.setProductoCollection(new ArrayList<Producto>());
        }
        if (usuario.getProductoCollection1() == null) {
            usuario.setProductoCollection1(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoUsuario idTipoUsuario = usuario.getIdTipoUsuario();
            if (idTipoUsuario != null) {
                idTipoUsuario = em.getReference(idTipoUsuario.getClass(), idTipoUsuario.getId());
                usuario.setIdTipoUsuario(idTipoUsuario);
            }
            Usuario idUsuarioCreacion = usuario.getIdUsuarioCreacion();
            if (idUsuarioCreacion != null) {
                idUsuarioCreacion = em.getReference(idUsuarioCreacion.getClass(), idUsuarioCreacion.getId());
                usuario.setIdUsuarioCreacion(idUsuarioCreacion);
            }
            Usuario idUsuarioModificacion = usuario.getIdUsuarioModificacion();
            if (idUsuarioModificacion != null) {
                idUsuarioModificacion = em.getReference(idUsuarioModificacion.getClass(), idUsuarioModificacion.getId());
                usuario.setIdUsuarioModificacion(idUsuarioModificacion);
            }
            Collection<Cliente> attachedClienteCollection = new ArrayList<Cliente>();
            for (Cliente clienteCollectionClienteToAttach : usuario.getClienteCollection()) {
                clienteCollectionClienteToAttach = em.getReference(clienteCollectionClienteToAttach.getClass(), clienteCollectionClienteToAttach.getId());
                attachedClienteCollection.add(clienteCollectionClienteToAttach);
            }
            usuario.setClienteCollection(attachedClienteCollection);
            Collection<Cliente> attachedClienteCollection1 = new ArrayList<Cliente>();
            for (Cliente clienteCollection1ClienteToAttach : usuario.getClienteCollection1()) {
                clienteCollection1ClienteToAttach = em.getReference(clienteCollection1ClienteToAttach.getClass(), clienteCollection1ClienteToAttach.getId());
                attachedClienteCollection1.add(clienteCollection1ClienteToAttach);
            }
            usuario.setClienteCollection1(attachedClienteCollection1);
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : usuario.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getId());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            usuario.setUsuarioCollection(attachedUsuarioCollection);
            Collection<Usuario> attachedUsuarioCollection1 = new ArrayList<Usuario>();
            for (Usuario usuarioCollection1UsuarioToAttach : usuario.getUsuarioCollection1()) {
                usuarioCollection1UsuarioToAttach = em.getReference(usuarioCollection1UsuarioToAttach.getClass(), usuarioCollection1UsuarioToAttach.getId());
                attachedUsuarioCollection1.add(usuarioCollection1UsuarioToAttach);
            }
            usuario.setUsuarioCollection1(attachedUsuarioCollection1);
            Collection<Producto> attachedProductoCollection = new ArrayList<Producto>();
            for (Producto productoCollectionProductoToAttach : usuario.getProductoCollection()) {
                productoCollectionProductoToAttach = em.getReference(productoCollectionProductoToAttach.getClass(), productoCollectionProductoToAttach.getId());
                attachedProductoCollection.add(productoCollectionProductoToAttach);
            }
            usuario.setProductoCollection(attachedProductoCollection);
            Collection<Producto> attachedProductoCollection1 = new ArrayList<Producto>();
            for (Producto productoCollection1ProductoToAttach : usuario.getProductoCollection1()) {
                productoCollection1ProductoToAttach = em.getReference(productoCollection1ProductoToAttach.getClass(), productoCollection1ProductoToAttach.getId());
                attachedProductoCollection1.add(productoCollection1ProductoToAttach);
            }
            usuario.setProductoCollection1(attachedProductoCollection1);
            em.persist(usuario);
            if (idTipoUsuario != null) {
                idTipoUsuario.getUsuarioCollection().add(usuario);
                idTipoUsuario = em.merge(idTipoUsuario);
            }
            if (idUsuarioCreacion != null) {
                idUsuarioCreacion.getUsuarioCollection().add(usuario);
                idUsuarioCreacion = em.merge(idUsuarioCreacion);
            }
            if (idUsuarioModificacion != null) {
                idUsuarioModificacion.getUsuarioCollection().add(usuario);
                idUsuarioModificacion = em.merge(idUsuarioModificacion);
            }
            for (Cliente clienteCollectionCliente : usuario.getClienteCollection()) {
                Usuario oldIdUsuarioCreacionOfClienteCollectionCliente = clienteCollectionCliente.getIdUsuarioCreacion();
                clienteCollectionCliente.setIdUsuarioCreacion(usuario);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
                if (oldIdUsuarioCreacionOfClienteCollectionCliente != null) {
                    oldIdUsuarioCreacionOfClienteCollectionCliente.getClienteCollection().remove(clienteCollectionCliente);
                    oldIdUsuarioCreacionOfClienteCollectionCliente = em.merge(oldIdUsuarioCreacionOfClienteCollectionCliente);
                }
            }
            for (Cliente clienteCollection1Cliente : usuario.getClienteCollection1()) {
                Usuario oldIdUsuarioModificacionOfClienteCollection1Cliente = clienteCollection1Cliente.getIdUsuarioModificacion();
                clienteCollection1Cliente.setIdUsuarioModificacion(usuario);
                clienteCollection1Cliente = em.merge(clienteCollection1Cliente);
                if (oldIdUsuarioModificacionOfClienteCollection1Cliente != null) {
                    oldIdUsuarioModificacionOfClienteCollection1Cliente.getClienteCollection1().remove(clienteCollection1Cliente);
                    oldIdUsuarioModificacionOfClienteCollection1Cliente = em.merge(oldIdUsuarioModificacionOfClienteCollection1Cliente);
                }
            }
            for (Usuario usuarioCollectionUsuario : usuario.getUsuarioCollection()) {
                Usuario oldIdUsuarioCreacionOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getIdUsuarioCreacion();
                usuarioCollectionUsuario.setIdUsuarioCreacion(usuario);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
                if (oldIdUsuarioCreacionOfUsuarioCollectionUsuario != null) {
                    oldIdUsuarioCreacionOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
                    oldIdUsuarioCreacionOfUsuarioCollectionUsuario = em.merge(oldIdUsuarioCreacionOfUsuarioCollectionUsuario);
                }
            }
            for (Usuario usuarioCollection1Usuario : usuario.getUsuarioCollection1()) {
                Usuario oldIdUsuarioModificacionOfUsuarioCollection1Usuario = usuarioCollection1Usuario.getIdUsuarioModificacion();
                usuarioCollection1Usuario.setIdUsuarioModificacion(usuario);
                usuarioCollection1Usuario = em.merge(usuarioCollection1Usuario);
                if (oldIdUsuarioModificacionOfUsuarioCollection1Usuario != null) {
                    oldIdUsuarioModificacionOfUsuarioCollection1Usuario.getUsuarioCollection1().remove(usuarioCollection1Usuario);
                    oldIdUsuarioModificacionOfUsuarioCollection1Usuario = em.merge(oldIdUsuarioModificacionOfUsuarioCollection1Usuario);
                }
            }
            for (Producto productoCollectionProducto : usuario.getProductoCollection()) {
                Usuario oldIdUsuarioCreacionOfProductoCollectionProducto = productoCollectionProducto.getIdUsuarioCreacion();
                productoCollectionProducto.setIdUsuarioCreacion(usuario);
                productoCollectionProducto = em.merge(productoCollectionProducto);
                if (oldIdUsuarioCreacionOfProductoCollectionProducto != null) {
                    oldIdUsuarioCreacionOfProductoCollectionProducto.getProductoCollection().remove(productoCollectionProducto);
                    oldIdUsuarioCreacionOfProductoCollectionProducto = em.merge(oldIdUsuarioCreacionOfProductoCollectionProducto);
                }
            }
            for (Producto productoCollection1Producto : usuario.getProductoCollection1()) {
                Usuario oldIdUsuarioModificacionOfProductoCollection1Producto = productoCollection1Producto.getIdUsuarioModificacion();
                productoCollection1Producto.setIdUsuarioModificacion(usuario);
                productoCollection1Producto = em.merge(productoCollection1Producto);
                if (oldIdUsuarioModificacionOfProductoCollection1Producto != null) {
                    oldIdUsuarioModificacionOfProductoCollection1Producto.getProductoCollection1().remove(productoCollection1Producto);
                    oldIdUsuarioModificacionOfProductoCollection1Producto = em.merge(oldIdUsuarioModificacionOfProductoCollection1Producto);
                }
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

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            TipoUsuario idTipoUsuarioOld = persistentUsuario.getIdTipoUsuario();
            TipoUsuario idTipoUsuarioNew = usuario.getIdTipoUsuario();
            Usuario idUsuarioCreacionOld = persistentUsuario.getIdUsuarioCreacion();
            Usuario idUsuarioCreacionNew = usuario.getIdUsuarioCreacion();
            Usuario idUsuarioModificacionOld = persistentUsuario.getIdUsuarioModificacion();
            Usuario idUsuarioModificacionNew = usuario.getIdUsuarioModificacion();
            Collection<Cliente> clienteCollectionOld = persistentUsuario.getClienteCollection();
            Collection<Cliente> clienteCollectionNew = usuario.getClienteCollection();
            Collection<Cliente> clienteCollection1Old = persistentUsuario.getClienteCollection1();
            Collection<Cliente> clienteCollection1New = usuario.getClienteCollection1();
            Collection<Usuario> usuarioCollectionOld = persistentUsuario.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = usuario.getUsuarioCollection();
            Collection<Usuario> usuarioCollection1Old = persistentUsuario.getUsuarioCollection1();
            Collection<Usuario> usuarioCollection1New = usuario.getUsuarioCollection1();
            Collection<Producto> productoCollectionOld = persistentUsuario.getProductoCollection();
            Collection<Producto> productoCollectionNew = usuario.getProductoCollection();
            Collection<Producto> productoCollection1Old = persistentUsuario.getProductoCollection1();
            Collection<Producto> productoCollection1New = usuario.getProductoCollection1();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteCollectionOldCliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(clienteCollectionOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteCollectionOldCliente + " since its idUsuarioCreacion field is not nullable.");
                }
            }
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioCollectionOldUsuario + " since its idUsuarioCreacion field is not nullable.");
                }
            }
            for (Producto productoCollectionOldProducto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Producto " + productoCollectionOldProducto + " since its idUsuarioCreacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTipoUsuarioNew != null) {
                idTipoUsuarioNew = em.getReference(idTipoUsuarioNew.getClass(), idTipoUsuarioNew.getId());
                usuario.setIdTipoUsuario(idTipoUsuarioNew);
            }
            if (idUsuarioCreacionNew != null) {
                idUsuarioCreacionNew = em.getReference(idUsuarioCreacionNew.getClass(), idUsuarioCreacionNew.getId());
                usuario.setIdUsuarioCreacion(idUsuarioCreacionNew);
            }
            if (idUsuarioModificacionNew != null) {
                idUsuarioModificacionNew = em.getReference(idUsuarioModificacionNew.getClass(), idUsuarioModificacionNew.getId());
                usuario.setIdUsuarioModificacion(idUsuarioModificacionNew);
            }
            Collection<Cliente> attachedClienteCollectionNew = new ArrayList<Cliente>();
            for (Cliente clienteCollectionNewClienteToAttach : clienteCollectionNew) {
                clienteCollectionNewClienteToAttach = em.getReference(clienteCollectionNewClienteToAttach.getClass(), clienteCollectionNewClienteToAttach.getId());
                attachedClienteCollectionNew.add(clienteCollectionNewClienteToAttach);
            }
            clienteCollectionNew = attachedClienteCollectionNew;
            usuario.setClienteCollection(clienteCollectionNew);
            Collection<Cliente> attachedClienteCollection1New = new ArrayList<Cliente>();
            for (Cliente clienteCollection1NewClienteToAttach : clienteCollection1New) {
                clienteCollection1NewClienteToAttach = em.getReference(clienteCollection1NewClienteToAttach.getClass(), clienteCollection1NewClienteToAttach.getId());
                attachedClienteCollection1New.add(clienteCollection1NewClienteToAttach);
            }
            clienteCollection1New = attachedClienteCollection1New;
            usuario.setClienteCollection1(clienteCollection1New);
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getId());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            usuario.setUsuarioCollection(usuarioCollectionNew);
            Collection<Usuario> attachedUsuarioCollection1New = new ArrayList<Usuario>();
            for (Usuario usuarioCollection1NewUsuarioToAttach : usuarioCollection1New) {
                usuarioCollection1NewUsuarioToAttach = em.getReference(usuarioCollection1NewUsuarioToAttach.getClass(), usuarioCollection1NewUsuarioToAttach.getId());
                attachedUsuarioCollection1New.add(usuarioCollection1NewUsuarioToAttach);
            }
            usuarioCollection1New = attachedUsuarioCollection1New;
            usuario.setUsuarioCollection1(usuarioCollection1New);
            Collection<Producto> attachedProductoCollectionNew = new ArrayList<Producto>();
            for (Producto productoCollectionNewProductoToAttach : productoCollectionNew) {
                productoCollectionNewProductoToAttach = em.getReference(productoCollectionNewProductoToAttach.getClass(), productoCollectionNewProductoToAttach.getId());
                attachedProductoCollectionNew.add(productoCollectionNewProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            usuario.setProductoCollection(productoCollectionNew);
            Collection<Producto> attachedProductoCollection1New = new ArrayList<Producto>();
            for (Producto productoCollection1NewProductoToAttach : productoCollection1New) {
                productoCollection1NewProductoToAttach = em.getReference(productoCollection1NewProductoToAttach.getClass(), productoCollection1NewProductoToAttach.getId());
                attachedProductoCollection1New.add(productoCollection1NewProductoToAttach);
            }
            productoCollection1New = attachedProductoCollection1New;
            usuario.setProductoCollection1(productoCollection1New);
            usuario = em.merge(usuario);
            if (idTipoUsuarioOld != null && !idTipoUsuarioOld.equals(idTipoUsuarioNew)) {
                idTipoUsuarioOld.getUsuarioCollection().remove(usuario);
                idTipoUsuarioOld = em.merge(idTipoUsuarioOld);
            }
            if (idTipoUsuarioNew != null && !idTipoUsuarioNew.equals(idTipoUsuarioOld)) {
                idTipoUsuarioNew.getUsuarioCollection().add(usuario);
                idTipoUsuarioNew = em.merge(idTipoUsuarioNew);
            }
            if (idUsuarioCreacionOld != null && !idUsuarioCreacionOld.equals(idUsuarioCreacionNew)) {
                idUsuarioCreacionOld.getUsuarioCollection().remove(usuario);
                idUsuarioCreacionOld = em.merge(idUsuarioCreacionOld);
            }
            if (idUsuarioCreacionNew != null && !idUsuarioCreacionNew.equals(idUsuarioCreacionOld)) {
                idUsuarioCreacionNew.getUsuarioCollection().add(usuario);
                idUsuarioCreacionNew = em.merge(idUsuarioCreacionNew);
            }
            if (idUsuarioModificacionOld != null && !idUsuarioModificacionOld.equals(idUsuarioModificacionNew)) {
                idUsuarioModificacionOld.getUsuarioCollection().remove(usuario);
                idUsuarioModificacionOld = em.merge(idUsuarioModificacionOld);
            }
            if (idUsuarioModificacionNew != null && !idUsuarioModificacionNew.equals(idUsuarioModificacionOld)) {
                idUsuarioModificacionNew.getUsuarioCollection().add(usuario);
                idUsuarioModificacionNew = em.merge(idUsuarioModificacionNew);
            }
            for (Cliente clienteCollectionNewCliente : clienteCollectionNew) {
                if (!clienteCollectionOld.contains(clienteCollectionNewCliente)) {
                    Usuario oldIdUsuarioCreacionOfClienteCollectionNewCliente = clienteCollectionNewCliente.getIdUsuarioCreacion();
                    clienteCollectionNewCliente.setIdUsuarioCreacion(usuario);
                    clienteCollectionNewCliente = em.merge(clienteCollectionNewCliente);
                    if (oldIdUsuarioCreacionOfClienteCollectionNewCliente != null && !oldIdUsuarioCreacionOfClienteCollectionNewCliente.equals(usuario)) {
                        oldIdUsuarioCreacionOfClienteCollectionNewCliente.getClienteCollection().remove(clienteCollectionNewCliente);
                        oldIdUsuarioCreacionOfClienteCollectionNewCliente = em.merge(oldIdUsuarioCreacionOfClienteCollectionNewCliente);
                    }
                }
            }
            for (Cliente clienteCollection1OldCliente : clienteCollection1Old) {
                if (!clienteCollection1New.contains(clienteCollection1OldCliente)) {
                    clienteCollection1OldCliente.setIdUsuarioModificacion(null);
                    clienteCollection1OldCliente = em.merge(clienteCollection1OldCliente);
                }
            }
            for (Cliente clienteCollection1NewCliente : clienteCollection1New) {
                if (!clienteCollection1Old.contains(clienteCollection1NewCliente)) {
                    Usuario oldIdUsuarioModificacionOfClienteCollection1NewCliente = clienteCollection1NewCliente.getIdUsuarioModificacion();
                    clienteCollection1NewCliente.setIdUsuarioModificacion(usuario);
                    clienteCollection1NewCliente = em.merge(clienteCollection1NewCliente);
                    if (oldIdUsuarioModificacionOfClienteCollection1NewCliente != null && !oldIdUsuarioModificacionOfClienteCollection1NewCliente.equals(usuario)) {
                        oldIdUsuarioModificacionOfClienteCollection1NewCliente.getClienteCollection1().remove(clienteCollection1NewCliente);
                        oldIdUsuarioModificacionOfClienteCollection1NewCliente = em.merge(oldIdUsuarioModificacionOfClienteCollection1NewCliente);
                    }
                }
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    Usuario oldIdUsuarioCreacionOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getIdUsuarioCreacion();
                    usuarioCollectionNewUsuario.setIdUsuarioCreacion(usuario);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                    if (oldIdUsuarioCreacionOfUsuarioCollectionNewUsuario != null && !oldIdUsuarioCreacionOfUsuarioCollectionNewUsuario.equals(usuario)) {
                        oldIdUsuarioCreacionOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
                        oldIdUsuarioCreacionOfUsuarioCollectionNewUsuario = em.merge(oldIdUsuarioCreacionOfUsuarioCollectionNewUsuario);
                    }
                }
            }
            for (Usuario usuarioCollection1OldUsuario : usuarioCollection1Old) {
                if (!usuarioCollection1New.contains(usuarioCollection1OldUsuario)) {
                    usuarioCollection1OldUsuario.setIdUsuarioModificacion(null);
                    usuarioCollection1OldUsuario = em.merge(usuarioCollection1OldUsuario);
                }
            }
            for (Usuario usuarioCollection1NewUsuario : usuarioCollection1New) {
                if (!usuarioCollection1Old.contains(usuarioCollection1NewUsuario)) {
                    Usuario oldIdUsuarioModificacionOfUsuarioCollection1NewUsuario = usuarioCollection1NewUsuario.getIdUsuarioModificacion();
                    usuarioCollection1NewUsuario.setIdUsuarioModificacion(usuario);
                    usuarioCollection1NewUsuario = em.merge(usuarioCollection1NewUsuario);
                    if (oldIdUsuarioModificacionOfUsuarioCollection1NewUsuario != null && !oldIdUsuarioModificacionOfUsuarioCollection1NewUsuario.equals(usuario)) {
                        oldIdUsuarioModificacionOfUsuarioCollection1NewUsuario.getUsuarioCollection1().remove(usuarioCollection1NewUsuario);
                        oldIdUsuarioModificacionOfUsuarioCollection1NewUsuario = em.merge(oldIdUsuarioModificacionOfUsuarioCollection1NewUsuario);
                    }
                }
            }
            for (Producto productoCollectionNewProducto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewProducto)) {
                    Usuario oldIdUsuarioCreacionOfProductoCollectionNewProducto = productoCollectionNewProducto.getIdUsuarioCreacion();
                    productoCollectionNewProducto.setIdUsuarioCreacion(usuario);
                    productoCollectionNewProducto = em.merge(productoCollectionNewProducto);
                    if (oldIdUsuarioCreacionOfProductoCollectionNewProducto != null && !oldIdUsuarioCreacionOfProductoCollectionNewProducto.equals(usuario)) {
                        oldIdUsuarioCreacionOfProductoCollectionNewProducto.getProductoCollection().remove(productoCollectionNewProducto);
                        oldIdUsuarioCreacionOfProductoCollectionNewProducto = em.merge(oldIdUsuarioCreacionOfProductoCollectionNewProducto);
                    }
                }
            }
            for (Producto productoCollection1OldProducto : productoCollection1Old) {
                if (!productoCollection1New.contains(productoCollection1OldProducto)) {
                    productoCollection1OldProducto.setIdUsuarioModificacion(null);
                    productoCollection1OldProducto = em.merge(productoCollection1OldProducto);
                }
            }
            for (Producto productoCollection1NewProducto : productoCollection1New) {
                if (!productoCollection1Old.contains(productoCollection1NewProducto)) {
                    Usuario oldIdUsuarioModificacionOfProductoCollection1NewProducto = productoCollection1NewProducto.getIdUsuarioModificacion();
                    productoCollection1NewProducto.setIdUsuarioModificacion(usuario);
                    productoCollection1NewProducto = em.merge(productoCollection1NewProducto);
                    if (oldIdUsuarioModificacionOfProductoCollection1NewProducto != null && !oldIdUsuarioModificacionOfProductoCollection1NewProducto.equals(usuario)) {
                        oldIdUsuarioModificacionOfProductoCollection1NewProducto.getProductoCollection1().remove(productoCollection1NewProducto);
                        oldIdUsuarioModificacionOfProductoCollection1NewProducto = em.merge(oldIdUsuarioModificacionOfProductoCollection1NewProducto);
                    }
                }
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
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cliente> clienteCollectionOrphanCheck = usuario.getClienteCollection();
            for (Cliente clienteCollectionOrphanCheckCliente : clienteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Cliente " + clienteCollectionOrphanCheckCliente + " in its clienteCollection field has a non-nullable idUsuarioCreacion field.");
            }
            Collection<Usuario> usuarioCollectionOrphanCheck = usuario.getUsuarioCollection();
            for (Usuario usuarioCollectionOrphanCheckUsuario : usuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Usuario " + usuarioCollectionOrphanCheckUsuario + " in its usuarioCollection field has a non-nullable idUsuarioCreacion field.");
            }
            Collection<Producto> productoCollectionOrphanCheck = usuario.getProductoCollection();
            for (Producto productoCollectionOrphanCheckProducto : productoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Producto " + productoCollectionOrphanCheckProducto + " in its productoCollection field has a non-nullable idUsuarioCreacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoUsuario idTipoUsuario = usuario.getIdTipoUsuario();
            if (idTipoUsuario != null) {
                idTipoUsuario.getUsuarioCollection().remove(usuario);
                idTipoUsuario = em.merge(idTipoUsuario);
            }
            Usuario idUsuarioCreacion = usuario.getIdUsuarioCreacion();
            if (idUsuarioCreacion != null) {
                idUsuarioCreacion.getUsuarioCollection().remove(usuario);
                idUsuarioCreacion = em.merge(idUsuarioCreacion);
            }
            Usuario idUsuarioModificacion = usuario.getIdUsuarioModificacion();
            if (idUsuarioModificacion != null) {
                idUsuarioModificacion.getUsuarioCollection().remove(usuario);
                idUsuarioModificacion = em.merge(idUsuarioModificacion);
            }
            Collection<Cliente> clienteCollection1 = usuario.getClienteCollection1();
            for (Cliente clienteCollection1Cliente : clienteCollection1) {
                clienteCollection1Cliente.setIdUsuarioModificacion(null);
                clienteCollection1Cliente = em.merge(clienteCollection1Cliente);
            }
            Collection<Usuario> usuarioCollection1 = usuario.getUsuarioCollection1();
            for (Usuario usuarioCollection1Usuario : usuarioCollection1) {
                usuarioCollection1Usuario.setIdUsuarioModificacion(null);
                usuarioCollection1Usuario = em.merge(usuarioCollection1Usuario);
            }
            Collection<Producto> productoCollection1 = usuario.getProductoCollection1();
            for (Producto productoCollection1Producto : productoCollection1) {
                productoCollection1Producto.setIdUsuarioModificacion(null);
                productoCollection1Producto = em.merge(productoCollection1Producto);
            }
            em.remove(usuario);
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

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            //em.close();
        }
    }
    
    public Usuario validarUsuario(String nombre, String password) throws NotSupportedException, SystemException {
        EntityManager em = emf.createEntityManager();
        Usuario usuario = null;

        utx.begin();
        try {
            String query = "SELECT * FROM usuario u WHERE u.nombre = '" + nombre + "' AND u.password = '" + password + "'";
            System.out.println(query);
            Query consulta = em.createNativeQuery(query, Usuario.class);
            List<Usuario> lista = consulta.getResultList();
            usuario = lista.get(0);
            utx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            utx.rollback();
        }

        return usuario;
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
}
