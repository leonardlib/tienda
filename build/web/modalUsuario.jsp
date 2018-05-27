<%-- 
    Document   : modalUsuario
    Created on : 26/05/2018, 07:45:06 PM
    Author     : leonardolirabecerra
--%>

<%@page import="modelo.TipoUsuario"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<TipoUsuario> listaTipos = (List<TipoUsuario>) request.getSession().getAttribute("listaTipos");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <div class="modal fade" id="modal-agregar" tabindex="-1" role="dialog" 
             aria-labelledby="modal-agregar-label" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modal-agregar-label"></h5>
                        <button type="button" class="close" data-dismiss="modal" 
                                aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="container">
                            <div class="row">
                                <div class="col-sm-12">
                                    <form action="UsuarioServlet" method="post">
                                        <input type="hidden" class="accion-modal" id="accion" name="accion" value="" />
                                        <input type="hidden" class="id-editar-modal" id="id" name="id" value="" />
                                        <div class="form-row">
                                            <div class="form-group col-sm-4">
                                                <label for="nombre">Nombre:</label>
                                                <input type="text" class="form-control" id="nombre" 
                                                       name="nombre" required />
                                            </div>
                                            <div class="form-group col-sm-4">
                                                <label for="password">Contraseña</label>
                                                <input type="password" id="password" 
                                                       name="password" class="form-control" 
                                                       required />
                                            </div>
                                            <div class="form-group col-sm-4">
                                                <label for="tipo-usuario">Tipo de usuario:</label>
                                                <select class="form-control" id="tipo-usuario" 
                                                        name="tipo-usuario" required>
                                                    <% for (TipoUsuario tipo : listaTipos) { %>
                                                    <option id="tipo-<%= tipo.getId() %>" value="<%= tipo.getId() %>">
                                                        <%= tipo.getTipo() %>
                                                    </option>
                                                    <% } %>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-row">
                                            <div class="form-group col-sm-12 float-right">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                                &nbsp;&nbsp;
                                                <button type="submit" class="btn btn-info">Guardar</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
