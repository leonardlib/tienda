package servlet;

import controlador.TipoUsuarioJpaController;
import controlador.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Producto;
import modelo.TipoUsuario;
import modelo.Usuario;

/**
 *
 * @author leonardolirabecerra
 */
public class UsuarioServlet extends HttpServlet {
    private static EntityManagerFactory emf;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            emf = Persistence.createEntityManagerFactory("CRUDTiendaPU");
            UsuarioJpaController controladorUsuario = new UsuarioJpaController(emf);
            TipoUsuarioJpaController controladorTipo = new TipoUsuarioJpaController(emf);
            
            String accion = request.getParameter("accion");
            HttpSession sesion = request.getSession();
            RequestDispatcher vista = request.getRequestDispatcher("indexUsuarios.jsp");

            if (accion.equalsIgnoreCase("ingresar")) {
                String nombre = request.getParameter("nombre");
                String pass = request.getParameter("password");

                Usuario usuario = null;
                
                try {
                    usuario = controladorUsuario.validarUsuario(nombre, pass);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                
                vista = request.getRequestDispatcher("ProductoServlet");

                if (usuario != null) {
                    //Crear sesión si es valido
                    sesion.setAttribute("nombre", usuario.getNombre());
                    sesion.setAttribute("usuario", usuario);
                    sesion.setAttribute("tipoUsuario", usuario.getIdTipoUsuario().getId());
                }
            } else if (accion.equalsIgnoreCase("eliminar")) {
                int idUsuario = Integer.parseInt(request.getParameter("id"));

                try {
                    controladorUsuario.destroy(idUsuario);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else if (accion.equalsIgnoreCase("agregar") || accion.equalsIgnoreCase("editar")) {
                String nombre = request.getParameter("nombre");
                String password = request.getParameter("password");
                int idTipo = Integer.parseInt(request.getParameter("tipo-usuario"));
                
                Usuario usuario;
                TipoUsuario tipoUsuario = controladorTipo.findTipoUsuario(idTipo);

                if (accion.equalsIgnoreCase("agregar")) {
                    usuario = new Usuario();
                } else {
                    int idUsuario = Integer.parseInt(request.getParameter("id"));
                    usuario = controladorUsuario.findUsuario(idUsuario);
                }

                usuario.setNombre(nombre);
                usuario.setPassword(password);
                usuario.setIdTipoUsuario(tipoUsuario);
                
                Timestamp fechaActual = new Timestamp(new Date().getTime());
                Usuario usuarioModificador = (Usuario) request.getSession().getAttribute("usuario");

                try {
                    if (accion.equalsIgnoreCase("agregar")) {
                        usuario.setFechaCreacion(fechaActual);
                        usuario.setIdUsuarioCreacion(usuarioModificador);
                        
                        controladorUsuario.create(usuario);
                    } else {
                        usuario.setFechaModificacion(fechaActual);
                        usuario.setIdUsuarioModificacion(usuarioModificador);
                        
                        controladorUsuario.edit(usuario);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else if (accion.equalsIgnoreCase("salir")) {
                sesion.removeAttribute("nombre");
                sesion.removeAttribute("usuario");
                sesion.removeAttribute("tipoUsuario");
                vista = request.getRequestDispatcher("index.jsp");
            }

            List<Usuario> listaUsuarios = controladorUsuario.findUsuarioEntities();
            List<TipoUsuario> listaTipos = controladorTipo.findTipoUsuarioEntities();
            sesion.setAttribute("listaUsuarios", listaUsuarios);
            sesion.setAttribute("listaTipos", listaTipos);
            vista.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Método para verificar que se encuentre una sesión activa, en caso de que
     * no, se redirige a la página de inicio de sesión.
     *
     * @param request
     * @param response
     */
    public void validaLogin(HttpServletRequest request, HttpServletResponse response) {
        HttpSession sesion = this.validaSesion(request);

        System.out.println("Nombre sesión: " + sesion.getAttribute("nombre"));
        if (sesion == null || sesion.getAttribute("nombre") == null) {
            try {
                RequestDispatcher vista = request.getRequestDispatcher("index.jsp");
                vista.forward(request, response);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Método para verificar los tiempos de la sesión y decidir si invalidarla o
     * no, se verifica el tiempo transcurrido y el tiempo inactivo.
     *
     * @param request
     * @return
     */
    private HttpSession validaSesion(HttpServletRequest request) {
        HttpSession sesion = request.getSession();

        if (!sesion.isNew()) {
            Date transcurrido = new Date(System.currentTimeMillis() - 2 * 60 * 60 * 1000); //2 horas transcurridas
            Date inactividad = new Date(System.currentTimeMillis() - 1 * 60 * 60 * 1000); //1 hora de inactividad
            Date creada = new Date(sesion.getCreationTime());
            Date accedida = new Date(sesion.getLastAccessedTime());

            if (creada.before(transcurrido) || accedida.before(inactividad)) {
                sesion.invalidate();
                sesion = request.getSession(true);
            }
        }

        return sesion;
    }
}
