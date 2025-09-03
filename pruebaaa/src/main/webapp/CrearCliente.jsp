<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <title>Crear Nuevo Usuario</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap.min.css">
    <style>
        body { background: #f7f9fb; padding: 20px; }
        .panel { margin-top: 20px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .btn-back { margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        .required-field::after { content: " *"; color: red; }
    </style>
</head>
<body>
<div class="container">
    <a href="index.jsp" class="btn btn-default btn-back">← Volver al Listado</a>
    
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">Crear Nuevo Usuario</h3>
        </div>
        <div class="panel-body">
            
            <form action="CrearUsuarioServlet" method="post" onsubmit="return validarFormulario()" id="userForm">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="cedula" class="required-field">Cédula</label>
                            <input type="number" class="form-control" id="cedula" name="cedula" 
                                   required min="1000000000" max="9999999999">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="rol" class="required-field">Rol</label>
                            <select class="form-control" id="rol" name="rol" required>
                                <option value="">Seleccione un rol</option>
                                <option value="Cliente">Cliente</option>
                                <option value="Administrador">Administrador</option>
                                <option value="Vendedor">Vendedor</option>
                            </select>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="nombres" class="required-field">Nombres</label>
                            <input type="text" class="form-control" id="nombres" name="nombres" required>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="apellidos" class="required-field">Apellidos</label>
                            <input type="text" class="form-control" id="apellidos" name="apellidos" required>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="direccion">Dirección</label>
                            <textarea class="form-control" id="direccion" name="direccion" rows="3"></textarea>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="telefono">Teléfono</label>
                            <input type="tel" class="form-control" id="telefono" name="telefono">
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="password" class="required-field">Contraseña</label>
                            <input type="password" class="form-control" id="password" name="password" required minlength="6">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="confirmPassword" class="required-field">Confirmar Contraseña</label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required minlength="6">
                        </div>
                    </div>
                </div>
                
                <div class="form-group text-center" style="margin-top: 30px;">
                    <button type="submit" class="btn btn-success btn-lg">➕ Crear Usuario</button>
                    
                    <a href="index.jsp" class="btn btn-default btn-lg">❌ Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
// Función para mostrar alertas
function mostrarAlerta(mensaje, tipo) {
    // Crear elemento de alerta
    const alerta = document.createElement('div');
    alerta.className = 'alert alert-' + tipo + ' alert-dismissible';
    alerta.style.cssText = 'position: fixed; top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    
    var titulo = tipo === 'danger' ? 'Error:' : 'Éxito:';
    alerta.innerHTML = 
        '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
        '<strong>' + titulo + '</strong> ' + mensaje;
    
    // Agregar al cuerpo
    document.body.appendChild(alerta);
    
    // Auto-eliminar después de 5 segundos
    setTimeout(function() {
        $(alerta).alert('close');
    }, 5000);
}

// Validación del formulario
function validarFormulario() {
    const cedula = $('#cedula').val();
    const password = $('#password').val();
    const confirmPassword = $('#confirmPassword').val();
    
    if (cedula.length !== 10) {
        mostrarAlerta('La cédula debe tener exactamente 10 dígitos', 'danger');
        return false;
    }
    
    if (password !== confirmPassword) {
        mostrarAlerta('Las contraseñas no coinciden', 'danger');
        return false;
    }
    
    if (password.length < 6) {
        mostrarAlerta('La contraseña debe tener al menos 6 caracteres', 'danger');
        return false;
    }
    
    return true;
}

// Mostrar mensajes del servidor al cargar la página
$(document).ready(function() {
    <%
        session = request.getSession(false);
        if (session != null) {
            String error = (String) session.getAttribute("error");
            String success = (String) session.getAttribute("success");
            
            if (error != null) {
    %>
                mostrarAlerta('<%= error %>', 'danger');
    <%
                session.removeAttribute("error");
            }
            
            if (success != null) {
    %>
                mostrarAlerta('<%= success %>', 'success');
    <%
                session.removeAttribute("success");
            }
        }
    %>
    
    // Validación de cédula
    $('#cedula').on('input', function() {
        this.value = this.value.replace(/[^0-9]/g, '');
        if (this.value.length > 10) {
            this.value = this.value.slice(0, 10);
        }
    });
    
    // Cerrar alertas al hacer click en la X
    $(document).on('click', '.alert .close', function() {
        $(this).parent().fadeOut('slow', function() {
            $(this).remove();
        });
    });
});
</script>
</body>
</html>