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
            <form action="EditarClienteServlet" method="post">
                <input type="hidden" name="id_usuario" value="<%= cliente.getInt("id_usuario") %>">

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="required-field">Nombres y Apellidos</label>
                            <input type="text" class="form-control" name="nombres_apellidos" value="<%= cliente.getString("nombres_apellidos") %>" required>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="required-field">Tipo Documento</label>
                            <input type="text" class="form-control" name="tipo_documento" value="<%= cliente.getString("tipo_documento") %>" required>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="required-field">Número Documento</label>
                            <input type="text" class="form-control" name="numero_documento" value="<%= cliente.getString("numero_documento") %>" required>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Fecha Nacimiento</label>
                            <input type="date" class="form-control" name="fecha_nacimiento" value="<%= cliente.getString("fecha_nacimiento") %>">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Lugar Nacimiento</label>
                            <input type="text" class="form-control" name="lugar_nacimiento" value="<%= cliente.getString("lugar_nacimiento") %>">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Nivel Escolaridad</label>
                            <input type="text" class="form-control" name="nivel_escolaridad" value="<%= cliente.getString("nivel_escolaridad") %>">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Ocupación Actual</label>
                            <input type="text" class="form-control" name="ocupacion_actual" value="<%= cliente.getString("ocupacion_actual") %>">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="required-field">Dirección</label>
                            <input type="text" class="form-control" name="direccion_residencia" value="<%= cliente.getString("direccion_residencia") %>" required>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Teléfono</label>
                            <input type="text" class="form-control" name="telefono" value="<%= cliente.getString("telefono") %>">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="required-field">Correo Electrónico</label>
                            <input type="email" class="form-control" name="correo_electronico" value="<%= cliente.getString("correo_electronico") %>" required>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Estado Civil</label>
                            <input type="text" class="form-control" name="estado_civil" value="<%= cliente.getString("estado_civil") %>">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Tribunal Eclesiástico</label>
                            <input type="text" class="form-control" name="tribunal_eclesiastico" value="<%= cliente.getString("tribunal_eclesiastico") %>">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Concepto Tribunal</label>
                            <input type="text" class="form-control" name="concepto_tribunal" value="<%= cliente.getString("concepto_tribunal") %>">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Archivo Concepto</label>
                            <input type="text" class="form-control" name="archivo_concepto" value="<%= cliente.getString("archivo_concepto") %>">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Nombre Cónyuge</label>
                            <input type="text" class="form-control" name="nombre_conyuge" value="<%= cliente.getString("nombre_conyuge") %>">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Número Hijos</label>
                            <input type="text" class="form-control" name="numero_hijos" value="<%= cliente.getString("numero_hijos") %>">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label>Nombres Hijos</label>
                    <input type="text" class="form-control" name="nombres_hijos" value="<%= cliente.getString("nombres_hijos") %>">
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Fecha Conversión</label>
                            <input type="date" class="form-control" name="fecha_conversion" value="<%= cliente.getString("fecha_conversion") %>">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Ha Estado Apartado</label>
                            <input type="text" class="form-control" name="ha_estado_apartado" value="<%= cliente.getString("ha_estado_apartado") %>">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Fecha Reconciliación</label>
                            <input type="date" class="form-control" name="fecha_reconciliacion" value="<%= cliente.getString("fecha_reconciliacion") %>">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Fecha Recepción Espíritu</label>
                            <input type="date" class="form-control" name="fecha_recepcion_espiritu" value="<%= cliente.getString("fecha_recepcion_espiritu") %>">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Fecha Bautismo</label>
                            <input type="date" class="form-control" name="fecha_bautismo" value="<%= cliente.getString("fecha_bautismo") %>">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Congregación Bautismo</label>
                            <input type="text" class="form-control" name="congregacion_bautismo" value="<%= cliente.getString("congregacion_bautismo") %>">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Pastor Bautismo</label>
                            <input type="text" class="form-control" name="pastor_bautismo" value="<%= cliente.getString("pastor_bautismo") %>">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Cargos Iglesia</label>
                            <input type="text" class="form-control" name="cargos_iglesia" value="<%= cliente.getString("cargos_iglesia") %>">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="required-field">Contraseña</label>
                    <input type="text" class="form-control" name="password" value="<%= cliente.getString("password") %>" required>
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
</body>
</html>
