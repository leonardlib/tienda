function abrirModal(accion, nombre, descripcion, cantidad, precio, id) {
    $('#modal-agregar').modal('show');
    $('.accion-modal').val(accion);
    
    if (accion == 'agregar') {
        $('#modal-agregar-label').html('Agregar producto');
    } else if (accion == 'editar') {
        $('#modal-agregar-label').html('Editar producto');
        $('.id-editar-modal').val(id);
        $('#nombre').val(nombre);
        $('#descripcion').val(descripcion);
        $('#cantidad').val(cantidad);
        $('#precio').val(precio);
    }
}

