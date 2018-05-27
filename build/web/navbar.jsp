<%-- 
    Document   : navbar
    Created on : 26/05/2018, 01:53:23 PM
    Author     : leonardolirabecerra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int tipoUsuario = Integer.parseInt(request.getSession().getAttribute("tipoUsuario").toString());
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <header class="navbar navbar-dark bg-dark">
            <a class="navbar-brand mr-0 mr-md-2" href="indexProductos.jsp">
                Tienda
            </a>

            <ul class="navbar-nav flex-row ml-md-auto d-none d-md-flex">
                <li class="nav-item">
                    <a class="nav-link p-2" href="ProductoServlet?accion=">
                        Productos
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link p-2" href="ClienteServlet?accion=">
                        Clientes
                    </a>
                </li>
                <% if (tipoUsuario == 1) { %>
                <li class="nav-item">
                    <a class="nav-link p-2" href="UsuarioServlet?accion=">
                        Usuarios
                    </a>
                </li>
                <% } %>
                &nbsp;&nbsp;
                <li class="nav-item">
                    <form class="form-inline" action="UsuarioServlet" method="post">
                        <input type="hidden" name="accion" value="salir" /> 
                        <button class="btn btn-outline-info my-2 my-sm-0" type="submit">
                            Cerrar sesión
                        </button>
                    </form>
                </li>
            </ul>
        </header>
    </body>
</html>
