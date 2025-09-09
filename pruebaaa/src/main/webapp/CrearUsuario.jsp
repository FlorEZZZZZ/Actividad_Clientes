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
                
                <!-- Datos Personales -->
                <h4><strong>Datos Personales</strong></h4><hr>
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
                            <label for="tipoDocumento" class="required-field">Tipo de Documento</label>
                            <select class="form-control" id="tipoDocumento" name="tipoDocumento" required>
                                <option value="">Seleccione...</option>
                                <option value="1">Cédula de Ciudadanía</option>
                                <option value="2">Tarjeta de Identidad</option>
                                <option value="3">Registro Civil</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="numeroDocumento" class="required-field">Número de Documento</label>
                            <input type="number" class="form-control" id="numeroDocumento" name="numeroDocumento" required>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="fechaNacimiento" class="required-field">Fecha de Nacimiento</label>
                            <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" required>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="lugarNacimiento" class="required-field">Lugar de Nacimiento</label>
                            <input type="text" class="form-control" id="lugarNacimiento" name="lugarNacimiento" required>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="nivelEscolaridad" class="required-field">Nivel de Escolaridad</label>
                            <select class="form-control" id="nivelEscolaridad" name="nivelEscolaridad" required>
                                <option value="">Seleccione...</option>
                                <option value="1">Primaria</option>
                                <option value="2">Bachiller</option>
                                <option value="3">Técnico</option>
                                <option value="4">Tecnólogo</option>
                                <option value="5">Superior</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="ocupacionActual" class="required-field">Ocupación Actual</label>
                            <input type="text" class="form-control" id="ocupacionActual" name="ocupacionActual" required>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="direccion" class="required-field">Dirección</label>
                            <input type="text" class="form-control" id="direccion" name="direccion" required>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="telefono" class="required-field">Teléfono</label>
                            <input type="tel" class="form-control" id="telefono" name="telefono" required>
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="correo" class="required-field">Correo Electrónico</label>
                    <input type="email" class="form-control" id="correo" name="correo" required>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="estadoCivil" class="required-field">Estado Civil</label>
                            <select class="form-control" id="estadoCivil" name="estadoCivil" required>
                                <option value="">Seleccione...</option>
                                <option value="1">Soltero</option>
                                <option value="2">Casado</option>
                                <option value="3">Viudo</option>
                                <option value="4">Divorciado</option>
                            </select>
                        </div>
                    </div>
                </div>
                
                <!-- Datos Iglesia -->
                <h4><strong>Datos Iglesia</strong></h4><hr>
                
                <div class="form-group">
                    <label for="fechaEntrega" class="required-field">Fecha en que se entregó al Señor Jesucristo</label>
                    <input type="date" class="form-control" id="fechaEntrega" name="fechaEntrega" required>
                </div>
                
                <div class="form-group">
                    <label for="apartado" class="required-field">¿Ha estado apartado?</label>
                    <select class="form-control" id="apartado" name="apartado" required>
                        <option value="2">No</option>
                        <option value="1">Sí</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="fechaReconciliacion">Fecha de Reconciliación</label>
                    <input type="date" class="form-control" id="fechaReconciliacion" name="fechaReconciliacion">
                </div>
                
                <div class="form-group">
                    <label for="fechaEspirituSanto">Fecha de Recepción del Espíritu Santo</label>
                    <input type="date" class="form-control" id="fechaEspirituSanto" name="fechaEspirituSanto">
                </div>
                
                <div class="form-group">
                    <label for="fechaBautismo">Fecha de Bautismo en el nombre de Jesús</label>
                    <input type="date" class="form-control" id="fechaBautismo" name="fechaBautismo">
                </div>
                
                <div class="form-group">
                    <label for="congregacionBautismo">Congregación en que se Bautizó</label>
                    <input type="text" class="form-control" id="congregacionBautismo" name="congregacionBautismo">
                </div>
                
                <div class="form-group">
                    <label for="pastorBautizo">Nombre del Pastor que lo Bautizó</label>
                    <input type="text" class="form-control" id="pastorBautizo" name="pastorBautizo">
                </div>
                
                <div class="form-group">
                    <label for="cargosIglesia">Cargos en la Iglesia</label>
                    <input type="text" class="form-control" id="cargosIglesia" name="cargosIglesia">
                </div>
                
                <!-- Seguridad -->
                <h4><strong>Seguridad</strong></h4><hr>
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
function mostrarAlerta(mensaje, tipo) {
    const alerta = document.createElement('div');
    alerta.className = 'alert alert-' + tipo + ' alert-dismissible';
    alerta.style.cssText = 'position: fixed; top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    var titulo = tipo === 'danger' ? 'Error:' : 'Éxito:';
    alerta.innerHTML = '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                       '<strong>' + titulo + '</strong> ' + mensaje;
    document.body.appendChild(alerta);
    setTimeout(function() { $(alerta).alert('close'); }, 5000);
}

function validarFormulario() {
    const password = $('#password').val();
    const confirmPassword = $('#confirmPassword').val();
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
});
</script>
</body>
</html>
