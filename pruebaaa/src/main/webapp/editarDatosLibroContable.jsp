<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Dato de Libro Contable</title>
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
            <h3 class="panel-title">Editar Datos del Libro Contable General</h3>
        </div>
        <div class="panel-body">
            <%
                ResultSet cliente = (ResultSet) request.getAttribute("cliente");
                if (cliente != null) {
            %>
            <form action="EditarClienteServlet" method="post" onsubmit="return validarFormulario()">
                <input type="hidden" name="id" value="<%= cliente.getInt("id") %>">
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="cedula" class="required-field">Numero de Documento</label>
                            <input type="number" class="form-control" id="numero_documento" name="numero_documento" 
                                   value="<%= cliente.getString("numero_documento") %>">
                        </div>
                    </div>
                 
                    
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="rol" class="required-field">Detalles</label>
                            <select class="form-control" id="detalle" name="detalle" required>
                                <option value="">Seleccione un Detalle</option>
                                <option value="Escuela Dominical" <%= "Escuela Dominical".equals(cliente.getString("detalle")) ? "selected" : "" %>>Escuela Dominical</option>
                                <option value="Culto de Jovenes" <%= "Culto de Jovenes".equals(cliente.getString("detalle")) ? "selected" : "" %>>Culto de Jovenes</option>
                                <option value="Culto de Damas Dorcas" <%= "Culto de Damas Dorcas".equals(cliente.getString("detalle")) ? "selected" : "" %>>Culto de Damas Dorcas</option>
                            </select>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="nombres" class="required-field">Fecha</label>
                            <input type="date" class="form-control" id="fecha" name="fecha" 
                                   value="<%= cliente.getString("fecha") %>">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="apellidos" class="required-field">Debe</label>
                            <input type="number" class="form-control" id="debe" name="debe" 
                                   value="<%= cliente.getString("debe") %>">
                        </div>
                    </div>
                
                  <div class="col-md-6">
                        <div class="form-group">
                            <label for="direccion" class="required-field">Haber</label>
                            <input type="number" class="form-control" id="haber" name="haber" 
                                     value="<%= cliente.getString("haber") %>">
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

</body>
</html>