<%@ page import="java.sql.*"%>
<%@ page import="conexion.Conexion"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Clientes y Productos</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap.min.css">
<style>
body {
	background: #f7f9fb;
}

.page-header {
	margin-top: 30px;
}

.panel {
	border-radius: 10px;
	margin-top: 20px;
}

.table>thead>tr>th {
	background: #0d6efd;
	color: #fff;
}

.badge-rol {
	background: #6c757d;
	padding: 4px 8px;
	border-radius: 4px;
	color: white;
}

.top-actions {
	margin-bottom: 10px;
}

.pull-right .btn {
	margin-left: 6px;
}

.alert {
	margin-top: 15px;
}
</style>
</head>
<body class="container">

	<%
    // 📩 Captura de mensajes de sesión
    HttpSession sessionObj = request.getSession(false);
    String success = null;
    String error = null;

    if (sessionObj != null) {
        success = (String) sessionObj.getAttribute("success");
        error = (String) sessionObj.getAttribute("error");
        // Limpiar después de usarlos
        if (success != null) sessionObj.removeAttribute("success");
        if (error != null) sessionObj.removeAttribute("error");
    }
%>

	<%
    Connection conn = null;
    try {
        Conexion cx = new Conexion();
        conn = cx.conectar();
    } catch (Exception e) {
        out.println("<div class='alert alert-danger'>No se pudo conectar a la base de datos: " + e.getMessage() + "</div>");
    }
%>

	<div class="page-header">
		<h1 class="text-primary">Panel — Usuarios</h1>
	</div>

	<%
    if (conn != null) {
        try {
            if (!conn.isClosed()) {
%>
	<div class="alert alert-info">Conectado correctamente a la base
		de datos.</div>
	<%
            } else {
%>
	<div class="alert alert-warning">La conexión está cerrada.</div>
	<%
            }
        } catch (SQLException ignore) {}
    } else {
%>
	<div class="alert alert-danger">
		Sin conexión activa. Verifica la clase
		<code>conexion.Conexion</code>
		.
	</div>
	<%
    }
%>

	<!-- ===================== CLIENTES ===================== -->
	<div class="panel panel-default">
		<div class="panel-heading clearfix">
			<h3 class="panel-title pull-left">Clientes</h3>
			<div class="pull-right top-actions">
				<a href="CrearCliente.jsp" class="btn btn-success btn-sm">Agregar
					nuevo cliente</a>
			</div>
		</div>
		<div class="panel-body">
			<%
    if (conn != null) {
        String sqlClientes = "SELECT * FROM tbl_cliente";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlClientes)) {

            if (!rs.isBeforeFirst()) {
%>
			<p class="text-muted">
				<em>No se encontraron clientes.</em>
			</p>
			<%
            } else {
%>
			<div class="table-responsive">
				<table class="table table-bordered table-striped table-hover">
					<thead>
						<tr>
							<th>#</th>
							<th>Cédula</th>
							<th>Nombres</th>
							<th>Apellidos</th>
							<th>Dirección</th>
							<th>Teléfono</th>
							<th>Rol</th>
							<th>Contraseña</th>
							<th style="width: 220px;">Acciones</th>
						</tr>
					</thead>
					<tbody>
						<%
                            while (rs.next()) {
                        %>
						<tr>
							<td><%= rs.getInt("idCliente") %></td>
							<td><%= rs.getString("cedula") %></td>
							<td><%= rs.getString("nombres") %></td>
							<td><%= rs.getString("apellidos") %></td>
							<td><%= rs.getString("direccion") %></td>
							<td><%= rs.getString("telefono") %></td>
							<td><span class="badge badge-rol"><%= rs.getString("rol") %></span></td>
							<td><%= rs.getString("password") %></td>
							<td><a
								href="VerClienteServlet?idCliente=<%= rs.getInt("idCliente") %>"
								class="btn btn-info btn-xs">👁 Ver</a> <a
								href="EditarClienteServlet?idCliente=<%= rs.getInt("idCliente") %>"
								class="btn btn-warning btn-xs">✏ Editar</a> <a
								href="${pageContext.request.contextPath}/GenerarPDFServlet?idCliente=<%= rs.getInt("idCliente") %>"
								class="btn btn-success btn-xs">⬇ PDF</a> <a
								href="EnviarCorreosServlet?idCliente=<%= rs.getInt("idCliente") %>"
								class="btn btn-primary btn-xs">📧 Correo</a> <a
								href="${pageContext.request.contextPath}/borrar?id=<%= rs.getInt("idCliente") %>"
								class="btn btn-danger btn-xs"
								onclick="return confirm('¿Seguro que quieres borrar este cliente?');">🗑️
									Borrar</a></td>
						</tr>
						<%
                            }
                        %>
					</tbody>
				</table>
			</div>
			<%
            }
        } catch (SQLException e) {
            out.println("<div class='alert alert-danger'>Error al cargar clientes: " + e.getMessage() + "</div>");
        }
    }
%>
		</div>
	</div>

	<%
    if (conn != null) {
        try { conn.close(); } catch (SQLException ignore) { }
    }
%>

	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/js/bootstrap.min.js"></script>
	<script>
// Mostrar mensajes de éxito/error del servlet
$(document).ready(function(){
    <% if (success != null) { %>
        mostrarAlerta('<%= success.replace("'", "\\'") %>', 'success');
    <% } %>
    <% if (error != null) { %>
        mostrarAlerta('<%= error.replace("'", "\\'") %>', 'danger');
    <% } %>
});

function mostrarAlerta(mensaje, tipo) {
    const alerta = document.createElement('div');
    alerta.className = 'alert alert-' + tipo + ' alert-dismissible fade in';
    alerta.style.cssText = 'position: fixed; top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    alerta.innerHTML =
        '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
        '<strong>' + (tipo === 'danger' ? 'Error: ' : 'Éxito: ') + '</strong>' + mensaje;
    document.body.appendChild(alerta);
    setTimeout(() => $(alerta).alert('close'), 5000);
}
</script>
</body>
</html>
