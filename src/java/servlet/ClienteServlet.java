/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controlador.ClienteJpaController;
import controlador.DireccionJpaController;
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
import modelo.Cliente;
import modelo.Direccion;
import modelo.Usuario;

/**
 *
 * @author leonardolirabecerra
 */
public class ClienteServlet extends HttpServlet {
    
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
            ClienteJpaController controladorCliente = new ClienteJpaController(emf);
            DireccionJpaController controladorDireccion = new DireccionJpaController(emf);
            
            String accion = request.getParameter("accion");
            HttpSession sesion = request.getSession();
            System.out.println("Acción clientes: " + accion);
            
            if (accion.equalsIgnoreCase("eliminar")) {
                int idCliente = Integer.parseInt(request.getParameter("id"));

                try {
                    controladorCliente.destroy(idCliente);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else if (accion.equalsIgnoreCase("agregar") || accion.equalsIgnoreCase("editar")) {
                String nombre = request.getParameter("nombre");
                String apellidos = request.getParameter("apellidos");
                String telefono = request.getParameter("telefono");
                String correo = request.getParameter("correo");
                String calle = request.getParameter("calle");
                String numero = request.getParameter("numero");
                int codigoPostal = Integer.parseInt(request.getParameter("cp"));
                String colonia = request.getParameter("colonia");
                String ciudad = request.getParameter("ciudad");
                String estado = request.getParameter("estado");
                
                Cliente cliente;
                Direccion direccion;

                if (accion.equalsIgnoreCase("agregar")) {
                    cliente = new Cliente();
                    direccion = new Direccion();
                } else {
                    int idCliente = Integer.parseInt(request.getParameter("id"));
                    cliente = controladorCliente.findCliente(idCliente);
                    direccion = cliente.getIdDireccion();
                }

                direccion.setCalle(calle);
                direccion.setNumero(numero);
                direccion.setCodigoPostal(codigoPostal);
                direccion.setColonia(colonia);
                direccion.setCiudad(ciudad);
                direccion.setEstado(estado);
                
                cliente.setNombre(nombre);
                cliente.setApellidos(apellidos);
                cliente.setTelefono(telefono);
                cliente.setCorreo(correo);
                
                
                Timestamp fechaActual = new Timestamp(new Date().getTime());
                Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

                try {
                    if (accion.equalsIgnoreCase("agregar")) {
                        controladorDireccion.create(direccion);
                        
                        cliente.setFechaCreacion(fechaActual);
                        cliente.setIdUsuarioCreacion(usuario);
                        cliente.setIdDireccion(direccion);
                        
                        controladorCliente.create(cliente);
                    } else {
                        controladorDireccion.edit(direccion);
                        
                        cliente.setFechaModificacion(fechaActual);
                        cliente.setIdUsuarioModificacion(usuario);
                        cliente.setIdDireccion(direccion);
                        
                        controladorCliente.edit(cliente);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

            List<Cliente> listaClientes = controladorCliente.findClienteEntities();
            sesion.setAttribute("listaClientes", listaClientes);
            RequestDispatcher vista = request.getRequestDispatcher("indexClientes.jsp");
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
