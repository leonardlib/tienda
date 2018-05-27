function abrirModal(accion, nombre, apellidos, telefono, correo, calle, numero, codigo_postal, colonia, ciudad, estado, id) {
    $('#modal-agregar').modal('show');
    $('.accion-modal').val(accion);

    if (accion == 'agregar') {
        $('#modal-agregar-label').html('Agregar cliente');
    } else if (accion == 'editar') {
        $('#modal-agregar-label').html('Editar cliente');
        $('.id-editar-modal').val(id);
        $('#nombre').val(nombre);
        $('#apellidos').val(apellidos);
        $('#telefono').val(telefono);
        $('#correo').val(correo);
        $('#calle').val(calle);
        $('#numero').val(numero);
        $('#cp').val(codigo_postal);
        $('#colonia').val(colonia);
        $('#ciudad').val(ciudad);
        $('#estado').val(estado);
    }
}

function obtenerDir(cp) {
    $.ajax({
        url: 'https://api-codigos-postales.herokuapp.com/v2/codigo_postal/' + cp,
        method: 'GET',
        success: function (response) {
            $('#colonia').val(response.colonias[0]);
            $('#ciudad').val(response.municipio);
            $('#estado').val(response.estado);
        }
    });
}
