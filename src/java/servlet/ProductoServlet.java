package servlet;

import controlador.ProductoJpaController;
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
import modelo.Usuario;

/**
 *
 * @author leonardolirabecerra
 */
public class ProductoServlet extends HttpServlet {

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
            //Validar login y sesión
            UsuarioServlet servletUsuario = new UsuarioServlet();
            servletUsuario.validaLogin(request, response);

            emf = Persistence.createEntityManagerFactory("CRUDTiendaPU");
            ProductoJpaController controladorProducto = new ProductoJpaController(emf);

            String accion = request.getParameter("accion");
            HttpSession sesion = request.getSession();
            System.out.println("Acción productos: " + accion);
            List<Producto> listaProductos = controladorProducto.findProductoEntities();

            if (accion.equalsIgnoreCase("eliminar")) {
                int idProducto = Integer.parseInt(request.getParameter("id"));

                try {
                    controladorProducto.destroy(idProducto);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                
                listaProductos = controladorProducto.findProductoEntities();
            } else if (accion.equalsIgnoreCase("agregar") || accion.equalsIgnoreCase("editar")) {
                String nombre = request.getParameter("nombre");
                String descripcion = request.getParameter("descripcion");
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));
                double precio = Double.parseDouble(request.getParameter("precio"));
                Producto producto;

                if (accion.equalsIgnoreCase("agregar")) {
                    producto = new Producto();
                } else {
                    int idProducto = Integer.parseInt(request.getParameter("id"));
                    producto = controladorProducto.findProducto(idProducto);
                }

                producto.setNombre(nombre);
                producto.setDescripcion(descripcion);
                producto.setCantidad(cantidad);
                producto.setPrecio(precio);
                
                Timestamp fechaActual = new Timestamp(new Date().getTime());
                Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

                try {
                    if (accion.equalsIgnoreCase("agregar")) {
                        producto.setFechaCreacion(fechaActual);
                        producto.setIdUsuarioCreacion(usuario);
                        
                        controladorProducto.create(producto);
                    } else {
                        producto.setFechaModificacion(fechaActual);
                        producto.setIdUsuarioModificacion(usuario);
                        
                        controladorProducto.edit(producto);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                
                listaProductos = controladorProducto.findProductoEntities();
            } else if (accion.equalsIgnoreCase("buscar")) {
                String nombre = request.getParameter("buscarProducto");
                
                listaProductos = controladorProducto.buscarPorNombre(nombre);
            }

            sesion.setAttribute("listaProductos", listaProductos);
            RequestDispatcher vista = request.getRequestDispatcher("indexProductos.jsp");
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

}
