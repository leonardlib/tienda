package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Cliente;
import modelo.Producto;
import modelo.TipoUsuario;
import modelo.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-26T20:07:59")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, Date> fechaModificacion;
    public static volatile SingularAttribute<Usuario, Usuario> idUsuarioCreacion;
    public static volatile SingularAttribute<Usuario, Usuario> idUsuarioModificacion;
    public static volatile CollectionAttribute<Usuario, Usuario> usuarioCollection;
    public static volatile CollectionAttribute<Usuario, Producto> productoCollection1;
    public static volatile SingularAttribute<Usuario, String> nombre;
    public static volatile SingularAttribute<Usuario, String> password;
    public static volatile SingularAttribute<Usuario, TipoUsuario> idTipoUsuario;
    public static volatile SingularAttribute<Usuario, Date> fechaCreacion;
    public static volatile SingularAttribute<Usuario, Integer> id;
    public static volatile CollectionAttribute<Usuario, Producto> productoCollection;
    public static volatile CollectionAttribute<Usuario, Cliente> clienteCollection1;
    public static volatile CollectionAttribute<Usuario, Cliente> clienteCollection;
    public static volatile CollectionAttribute<Usuario, Usuario> usuarioCollection1;

}