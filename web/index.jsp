<%-- 
    Document   : index
    Created on : 26/05/2018, 12:45:56 PM
    Author     : leonardolirabecerra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="resources/css/bootstrap.css">
        <link rel="stylesheet" href="resources/css/login.css">

        <title>Inicio de sesión</title>
    </head>
    <body class="text-center">
        <form class="form-signin" action="UsuarioServlet" method="post">
            <input type="hidden" name="accion" value="ingresar" /> 
            <h1 class="h3 mb-3 font-weight-normal">Inicio de sesión</h1>
            
            <label for="nombre" class="sr-only">Usuario</label>
            <input type="text" id="nombre" name="nombre" class="form-control" 
                   placeholder="Usuario" required autofocus>
            
            <label for="password" class="sr-only">Contraseña</label>
            <input type="password" id="password" name="password" class="form-control" 
                   placeholder="Contraseña" required>
            
            <button class="btn btn-lg btn-primary btn-block" type="submit">Entrar</button>
            <p class="mt-5 mb-3 text-muted">Leonardo Lira Becerra</p>
        </form>

        <!-- Scripts -->
        <script src="resources/js/jquery.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" 
                integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" 
        crossorigin="anonymous"></script>
        <script src="resources/js/bootstrap.js"></script>       
    </body>
</html>