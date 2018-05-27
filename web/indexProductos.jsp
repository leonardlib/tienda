<%-- 
    Document   : indexProductos
    Created on : 26/05/2018, 01:23:16 PM
    Author     : leonardolirabecerra
--%>

<%@page import="java.util.List"%>
<%@page import="modelo.Producto"%>
<%@page import="servlet.UsuarioServlet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    UsuarioServlet servletUsuario = new UsuarioServlet();
    servletUsuario.validaLogin(request, response);

    List<Producto> listaProductos = (List<Producto>) request.getSession().getAttribute("listaProductos");
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

        <title>Productos</title>
    </head>
    <body>
        <jsp:include page="navbar.jsp" />
        <div class="jumbotron text-center">
            <h1>Productos</h1>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-sm-2">
                    <button class="btn btn-info" type="button" 
                            onclick="abrirModal('agregar', '', '', '', '', '')">
                        Agregar producto
                    </button>
                </div>
                <div class="col-sm-10">
                    <form class="form-inline" action="ProductoServlet" method="post">
                        <input type="hidden" name="accion" value="buscar" />
                        <label class="sr-only" for="buscarProducto">Nombre</label>
                        <input type="text" class="form-control" id="buscarProducto" 
                               placeholder="Nombre" name="buscarProducto">
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
                                    <th scope="col">Descripción</th>
                                    <th scope="col">Cantidad</th>
                                    <th scope="col">Precio</th>
                                    <th scope="col"></th>
                                    <th scope="col"></th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Producto producto : listaProductos) {%>
                                <tr>
                                    <th scope="row"><%= producto.getId()%></th>
                                    <td><%= producto.getNombre()%></td>
                                    <td><%= producto.getDescripcion()%></td>
                                    <td><%= producto.getCantidad()%></td>
                                    <td>$<%= producto.getPrecio()%></td>
                                    <td class="text-center">
                                        <button class="btn btn-secondary" type="button" 
                                                onclick="abrirModal(
                                                                'editar',
                                                                '<%= producto.getNombre()%>',
                                                                '<%= producto.getDescripcion()%>',
                                                                '<%= producto.getCantidad()%>',
                                                                '<%= producto.getPrecio()%>',
                                                                '<%= producto.getId()%>'
                                                                )">
                                            Editar
                                        </button>
                                    </td>
                                    <td class="text-center">
                                        <form action="ProductoServlet" method="post">
                                            <input type="hidden" id="accion" name="accion" value="eliminar" />
                                            <input type="hidden" id="id" name="id" value="<%= producto.getId()%>" />
                                            <button class="btn btn-danger" type="submit">Eliminar</button>
                                        </form>
                                    </td>
                                </tr>
                                <% }%>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="modalProducto.jsp" />

        <!-- Scripts -->
        <script src="resources/js/jquery.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" 
                integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" 
        crossorigin="anonymous"></script>
        <script src="resources/js/bootstrap.js"></script>
        <script src="resources/js/producto.js"></script>
    </body>
</html>
