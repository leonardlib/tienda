<%-- 
    Document   : modalProducto
    Created on : 26/05/2018, 04:32:08 PM
    Author     : leonardolirabecerra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                                    <form action="ProductoServlet" method="post">
                                        <input type="hidden" class="accion-modal" id="accion" name="accion" value="" />
                                        <input type="hidden" class="id-editar-modal" id="id" name="id" value="" />
                                        <div class="form-row">
                                            <div class="form-group col-sm-6">
                                                <label for="nombre">Nombre:</label>
                                                <input type="text" class="form-control" id="nombre" 
                                                       name="nombre" required />
                                            </div>
                                            <div class="form-group col-sm-6">
                                                <label for="descripcion">Descripción:</label>
                                                <input type="text" class="form-control" id="descripcion" 
                                                       name="descripcion" required />
                                            </div>
                                        </div>
                                        <div class="form-row">
                                            <div class="form-group col-sm-6">
                                                <label for="cantidad">Cantidad:</label>
                                                <input type="number" class="form-control" id="cantidad" 
                                                       name="cantidad" min="0" step="1" required />
                                            </div>
                                            <div class="form-group col-sm-6">
                                                <label for="precio">Precio:</label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text" id="basic-addon1">$</span>
                                                    </div>
                                                    <input type="number" min="1" step="0.01" 
                                                           class="form-control" aria-describedby="basic-addon1"
                                                           id="precio" name="precio" required>
                                                </div>
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
