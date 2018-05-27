<%-- 
    Document   : indexClientes
    Created on : 26/05/2018, 01:23:16 PM
    Author     : leonardolirabecerra
--%>

<%@page import="java.util.List"%>
<%@page import="modelo.Cliente"%>
<%@page import="servlet.UsuarioServlet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    UsuarioServlet servletUsuario = new UsuarioServlet();
    servletUsuario.validaLogin(request, response);

    List<Cliente> listaClientes = (List<Cliente>) request.getSession().getAttribute("listaClientes");
%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="resources/css/bootstrap.css">
        <link rel="stylesheet" href="resources/css/main.css">

        <title>Clientes</title>
    </head>
    <body>
        <jsp:include page="navbar.jsp" />
        <div class="jumbotron text-center">
            <h1>Clientes</h1>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-sm-2">
                    <button class="btn btn-info" type="button" 
                            onclick="abrirModal('agregar', '', '', '', '', '', '', '', '', '', '', '')">
                        Agregar cliente
                    </button>
                </div>
                <div class="col-sm-10">
                    <form class="form-inline" action="ClienteServlet" method="post">
                        <input type="hidden" name="accion" value="buscar" />
                        <label class="sr-only" for="buscarCliente">Nombre</label>
                        <input type="text" class="form-control" id="buscarCliente" 
                               placeholder="Nombre" name="buscarCliente">
                        &nbsp;
                        &nbsp;
                        <button class="btn btn-primary" type="submit">
                            Buscar
                        </button>
                    </form>
                </div>
            </div>
            <div class="row contenedor">
                <div class="col-sm-12">
                    <div class="table-responsive">
                        <table class="table">
                            <thead class="thead-light">
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Nombre</th>
                                    <th scope="col">Apellidos</th>
                                    <th scope="col">Teléfono</th>
                                    <th scope="col">Correo</th>
                                    <th scope="col">Dirección</th>
                                    <th scope="col"></th>
                                    <th scope="col"></th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Cliente cliente : listaClientes) {%>
                                <tr>
                                    <th scope="row"><%= cliente.getId()%></th>
                                    <td><%= cliente.getNombre()%></td>
                                    <td><%= cliente.getApellidos()%></td>
                                    <td><%= cliente.getTelefono()%></td>
                                    <td><%= cliente.getCorreo()%></td>
                                    <td><%= cliente.getDireccionCompleta() %></td>
                                    <td class="text-center">
                                        <button class="btn btn-secondary" type="button" 
                                                onclick="abrirModal(
                                                    'editar',
                                                    '<%= cliente.getNombre()%>',
                                                    '<%= cliente.getApellidos()%>',
                                                    '<%= cliente.getTelefono()%>',
                                                    '<%= cliente.getCorreo()%>',
                                                    '<%= cliente.getIdDireccion().getCalle() %>',
                                                    '<%= cliente.getIdDireccion().getNumero() %>',
                                                    '<%= cliente.getIdDireccion().getCodigoPostal() %>',
                                                    '<%= cliente.getIdDireccion().getColonia() %>',
                                                    '<%= cliente.getIdDireccion().getCiudad() %>',
                                                    '<%= cliente.getIdDireccion().getEstado() %>',
                                                    '<%= cliente.getId()%>'
                                                )">
                                            Editar
                                        </button>
                                    </td>
                                    <td class="text-center">
                                        <form action="ClienteServlet" method="post">
                                            <input type="hidden" id="accion" name="accion" value="eliminar" />
                                            <input type="hidden" id="id" name="id" value="<%= cliente.getId()%>" />
                                            <button class="btn btn-danger" type="submit">Eliminar</button>
                                        </form>
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="modalCliente.jsp" />

        <!-- Scripts -->
        <script src="resources/js/jquery.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" 
                integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" 
        crossorigin="anonymous"></script>
        <script src="resources/js/bootstrap.js"></script>
        <script src="resources/js/cliente.js"></script>
    </body>
</html>
