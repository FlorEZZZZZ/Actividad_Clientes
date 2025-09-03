<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Cliente</title>
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
            <h3 class="panel-title">Editar Cliente</h3>
        </div>
        <div class="panel-body">
            <%
                ResultSet cliente = (ResultSet) request.getAttribute("cliente");
                if (cliente != null) {
            %>
            <form action="EditarClienteServlet" method="post" onsubmit="return validarFormulario()">
                <input type="hidden" name="idCliente" value="<%= cliente.getInt("idCliente") %>">
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="cedula" class="required-field">Cédula</label>
                            <input type="text" class="form-control" id="cedula" name="cedula" 
                                   value="<%= cliente.getString("cedula") %>" required 
                                   pattern="[0-9]{10}" title="La cédula debe tener 10 dígitos">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="rol" class="required-field">Rol</label>
                            <select class="form-control" id="rol" name="rol" required>
                                <option value="">Seleccione un rol</option>
                                <option value="Cliente" <%= "Cliente".equals(cliente.getString("rol")) ? "selected" : "" %>>Cliente</option>
                                <option value="Administrador" <%= "Administrador".equals(cliente.getString("rol")) ? "selected" : "" %>>Administrador</option>
                                <option value="Vendedor" <%= "Vendedor".equals(cliente.getString("rol")) ? "selected" : "" %>>Vendedor</option>
                            </select>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="nombres" class="required-field">Nombres</label>
                            <input type="text" class="form-control" id="nombres" name="nombres" 
                                   value="<%= cliente.getString("nombres") %>" required 
                                   pattern="[A-Za-záéíóúñüÁÉÍÓÚÑÜ\s]{2,50}" title="Solo letras y espacios (2-50 caracteres)">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="apellidos" class="required-field">Apellidos</label>
                            <input type="text" class="form-control" id="apellidos" name="apellidos" 
                                   value="<%= cliente.getString("apellidos") %>" required 
                                   pattern="[A-Za-záéíóúñüÁÉÍÓÚÑÜ\s]{2,50}" title="Solo letras y espacios (2-50 caracteres)">
                        </div>
                    </div>
                
                  <div class="col-md-6">
                        <div class="form-group">
                            <label for="direccion" class="required-field">Dirección</label>
                            <input type="text" class="form-control" id="password" name="direccion" 
                                     value="<%= cliente.getString("direccion") %>" required 
                                   pattern="[A-Za-z0-9áéíóúñüÁÉÍÓÚÑÜ!@#$%^&*()_+={}\[\]:;]{6,50}" title="Solo letras y caráctares (255 caracteres)">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="telefono" class="required-field">Teléfono</label>
                            <input type="tel" class="form-control" id="telefono" name="telefono" 
                                   value="<%= cliente.getString("telefono") != null ? cliente.getString("telefono") : "" %>"
                                   pattern="[0-9]{7,10}" title="Teléfono de 7 a 10 dígitos">
                        </div>
                    </div>
                
                  <div class="col-md-6">
                        <div class="form-group">
                            <label for="password" class="required-field">Contraseña</label>
                            <input type="text" class="form-control" id="password" name="password" 
                                     value="<%= cliente.getString("password") %>" required 
                                   pattern="[A-Za-z0-9áéíóúñüÁÉÍÓÚÑÜ!@#$%^&*()_+={}\[\]:;]{6,50}" title="Solo letras y caráctares (255 caracteres)">
                        </div>
                    </div>
                
                
                
                <div class="form-group text-center" style="margin-top: 30px;">
                    <button type="submit" class="btn btn-primary btn-lg">💾 Actualizar Cliente</button>
                    <a href="index.jsp" class="btn btn-default btn-lg">❌ Cancelar</a>
                </div>
            </form>
            <% } else { %>
                <div class="alert alert-danger">
                    <strong>Error:</strong> No se encontraron datos del cliente.
                </div>
                <a href="index.jsp" class="btn btn-default">Volver al Listado</a>
            <% } %>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
function validarFormulario() {
    var cedula = $('#cedula').val();
    var nombres = $('#nombres').val();
    var apellidos = $('#apellidos').val();
    var rol = $('#rol').val();
    
    if (cedula.trim() === '' || nombres.trim() === '' || apellidos.trim() === '' || rol.trim() === '') {
        alert('Por favor, complete todos los campos obligatorios (*)');
        return false;
    }
    
    if (!/^\d{10}$/.test(cedula)) {
        alert('La cédula debe tener exactamente 10 dígitos');
        return false;
    }
    
    return true;
}

$(document).ready(function() {
    // Validación en tiempo real
    $('#cedula').on('input', function() {
        this.value = this.value.replace(/[^0-9]/g, '');
    });
});
</script>
</body>
</html>