<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Detalles del Cliente</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2 class="text-primary">Detalles del Cliente</h2>
    
    <%
        ResultSet cliente = (ResultSet) request.getAttribute("cliente");
        if (cliente != null) {
    %>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Información del Cliente</h3>
            </div>
            <div class="panel-body">
                <table class="table table-bordered">
                    <tr><th>ID:</th><td><%= cliente.getInt("idCliente") %></td></tr>
                    <tr><th>Cédula:</th><td><%= cliente.getString("cedula") %></td></tr>
                    <tr><th>Nombres:</th><td><%= cliente.getString("nombres") %></td></tr>
                    <tr><th>Apellidos:</th><td><%= cliente.getString("apellidos") %></td></tr>
                    <tr><th>Dirección:</th><td><%= cliente.getString("direccion") %></td></tr>
                    <tr><th>Teléfono:</th><td><%= cliente.getString("telefono") %></td></tr>
                    <tr><th>Rol:</th><td><span class="badge"><%= cliente.getString("rol") %></span></td></tr>
                </table>
            </div>
        </div>
    <% } else { %>
        <div class="alert alert-danger">Cliente no encontrado</div>
    <% } %>
    
    <a href="index.jsp" class="btn btn-primary">Volver al Listado</a>
</div>
</body>
</html>