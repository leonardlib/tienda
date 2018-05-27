<%-- 
    Document   : modalCliente
    Created on : 26/05/2018, 06:21:24 PM
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
                                    <form action="ClienteServlet" method="post">
                                        <input type="hidden" class="accion-modal" id="accion" name="accion" value="" />
                                        <input type="hidden" class="id-editar-modal" id="id" name="id" value="" />
                                        <div class="form-row">
                                            <div class="form-group col-sm-6">
                                                <label for="nombre">Nombre:</label>
                                                <input type="text" class="form-control" id="nombre" 
                                                       name="nombre" required />
                                            </div>
                                            <div class="form-group col-sm-6">
                                                <label for="apellidos">Apellidos:</label>
                                                <input type="text" class="form-control" id="apellidos" 
                                                       name="apellidos" required />
                                            </div>
                                        </div>
                                        <div class="form-row">
                                            <div class="form-group col-sm-6">
                                                <label for="telefono">Teléfono:</label>
                                                <input type="tel" class="form-control" id="telefono"
                                                       pattern="[0-9]{10}"
                                                       name="telefono" required />
                                            </div>
                                            <div class="form-group col-sm-6">
                                                <label for="correo">Correo:</label>
                                                <input type="email" class="form-control" id="correo" 
                                                       name="correo" required />
                                            </div>
                                        </div>
                                        <div class="form-row">
                                            <div class="form-group col-sm-4">
                                                <label for="calle">Calle:</label>
                                                <input type="text" class="form-control" id="calle" 
                                                       name="calle" required />
                                            </div>
                                            <div class="form-group col-sm-4">
                                                <label for="numero">Número:</label>
                                                <input type="text" class="form-control" id="numero" 
                                                       name="numero" required />
                                            </div>
                                            <div class="form-group col-sm-4">
                                                <label for="cp">Código postal:</label>
                                                <input type="number" class="form-control" id="cp" onchange="obtenerDir(this.value)"
                                                       name="cp" min="00001" max="99999" required />
                                            </div>
                                        </div>
                                        <div class="form-row">
                                            <div class="form-group col-sm-4">
                                                <label for="colonia">Colonia:</label>
                                                <input type="text" class="form-control" id="colonia" 
                                                       name="colonia" readonly required />
                                            </div>
                                            <div class="form-group col-sm-4">
                                                <label for="ciudad">Ciudad:</label>
                                                <input type="text" class="form-control" id="ciudad" 
                                                       name="ciudad" readonly required />
                                            </div>
                                            <div class="form-group col-sm-4">
                                                <label for="estado">Estado:</label>
                                                <input type="text" class="form-control" id="estado" 
                                                       name="estado" readonly required />
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
