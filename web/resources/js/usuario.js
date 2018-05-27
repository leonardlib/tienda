function abrirModal(accion, nombre, password, tipoUsuario, id) {
    $('#modal-agregar').modal('show');
    $('.accion-modal').val(accion);
    
    if (accion == 'agregar') {
        $('#modal-agregar-label').html('Agregar usuario');
    } else if (accion == 'editar') {
        $('#modal-agregar-label').html('Editar usuario');
        $('.id-editar-modal').val(id);
        $('#nombre').val(nombre);
        $('#password').val(password);
        $('#tipo-usuario').val(tipoUsuario);
    }
}
