<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <title>Crear Dato en Libro de Contabilidad</title>
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
            <h3 class="panel-title">Crear Nuevo Dato</h3>
        </div>
        <div class="panel-body">
            
            <form action="AgregarDatosContabilidadLibroServlet" method="post" onsubmit="return validarFormulario()" id="userForm">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="cedula" class="required-field">Numero de Documento</label>
                            <input type="number" class="form-control" id="numDoc" name="numDoc">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="rol" class="required-field">Detalle</label>
                            <select class="form-control" id="detalle" name="detalle" required>
                                <option value="">Seleccione un Detalle</option>
                                <option value="Escuela Dominical">Escuela Dominical</option>
                                <option value="Culto de Jovenes">Culto de Jovenes</option>
                                <option value="Culto de Damas Dorcas">Culto de Damas Dorcas</option>
                            </select>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="nombres" class="required-field">Fecha</label>
                            <input type="date" class="form-control" id="fecha" name="fecha" required>
                        </div>
                    </div>
                 
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="direccion">Debe</label>
                            <input type="number" class="form-control" id="debe" name="debe"></input>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="telefono">Haber</label>
                            <input type="number" class="form-control" id="haber" name="haber">
                        </div>
                    </div>
                        
                
                <div class="form-group text-center" style="margin-top: 30px;">
                    <button type="submit" class="btn btn-success btn-lg">➕ Crear Dato</button>
                    
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
</script>
</body>
</html>