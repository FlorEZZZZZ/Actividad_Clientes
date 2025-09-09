package DAO;

import conexion.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import conexion.EnviarCorreos;

@WebServlet("/CrearUsuarioServlet")
public class CrearUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Connection con = null;
        EnviarCorreos envc = new EnviarCorreos();

        try {
            session.removeAttribute("error");
            session.removeAttribute("success");

            // 📌 Parámetros
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String nombres_apellidos = nombres + " " + apellidos;
            String tipoDocumento = request.getParameter("tipoDocumento");
            String numeroDocumento = request.getParameter("numeroDocumento");
            String fechaNacimiento = request.getParameter("fechaNacimiento");
            String lugarNacimiento = request.getParameter("lugarNacimiento");
            String nivelEscolaridad = request.getParameter("nivelEscolaridad");
            String ocupacionActual = request.getParameter("ocupacionActual");
            String direccion = request.getParameter("direccion");
            String telefono = request.getParameter("telefono");
            String correo = request.getParameter("correo");
            String estadoCivil = request.getParameter("estadoCivil");
            String tribunalEclesiastico = request.getParameter("tribunalEclesiastico");
            String conceptoTribunal = request.getParameter("conceptoTribunal");
            String archivoConcepto = request.getParameter("archivoConcepto");
            String nombreConyuge = request.getParameter("nombreConyuge");
            String numeroHijos = request.getParameter("numeroHijos");
            String nombresHijos = request.getParameter("nombresHijos");
            String fechaConversion = request.getParameter("fechaEntrega");
            String haEstadoApartado = request.getParameter("apartado");
            String fechaReconciliacion = request.getParameter("fechaReconciliacion");
            String fechaRecepcionEspiritu = request.getParameter("fechaEspirituSanto");
            String fechaBautismo = request.getParameter("fechabautismo");
            String congregacionBautismo = request.getParameter("congregacionbautismo");
            String pastorBautismo = request.getParameter("pastorbautismo");
            String cargosIglesia = request.getParameter("cargosiglesia");
            String password = request.getParameter("password");

            con = new Conexion().conectar();

            // 📌 Verificar documento duplicado
            String sqlVerificar = "SELECT COUNT(*) FROM tbl_usuarios WHERE numero_documento = ?";
            PreparedStatement psVerificar = con.prepareStatement(sqlVerificar);
            psVerificar.setString(1, numeroDocumento);
            ResultSet rs = psVerificar.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                session.setAttribute("error", "El documento " + numeroDocumento + " ya está registrado");
                response.sendRedirect(request.getContextPath() + "/CrearUsuario.jsp");
                return;
            }

            // 📌 Insert
            String sql = "INSERT INTO tbl_usuarios " +
                    "(nombres_apellidos, tipo_documento, numero_documento, fecha_nacimiento, lugar_nacimiento, " +
                    "nivel_escolaridad, ocupacion_actual, direccion_residencia, telefono, correo_electronico, estado_civil, " +
                    "tribunal_eclesiastico, concepto_tribunal, archivo_concepto, nombre_conyuge, numero_hijos, nombres_hijos, " +
                    "fecha_conversion, ha_estado_apartado, fecha_reconciliacion, fecha_recepcion_espiritu, fecha_bautismo, " +
                    "congregacion_bautismo, pastor_bautismo, cargos_iglesia, password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombres_apellidos);
            ps.setString(2, tipoDocumento);
            ps.setString(3, numeroDocumento);
            ps.setString(4, fechaNacimiento); // ⚠️ si este también es DATE en la DB, hay que hacer lo mismo que abajo
            ps.setString(5, lugarNacimiento);
            ps.setString(6, nivelEscolaridad);
            ps.setString(7, ocupacionActual);
            ps.setString(8, direccion);
            ps.setString(9, telefono);
            ps.setString(10, correo);
            ps.setString(11, estadoCivil);
            ps.setString(12, tribunalEclesiastico);
            ps.setString(13, conceptoTribunal);
            ps.setString(14, archivoConcepto);
            ps.setString(15, nombreConyuge);
            ps.setString(16, numeroHijos);
            ps.setString(17, nombresHijos);

            // ✅ Fechas opcionales
            if (fechaConversion == null || fechaConversion.isEmpty()) {
                ps.setNull(18, java.sql.Types.DATE);
            } else {
                ps.setDate(18, java.sql.Date.valueOf(fechaConversion));
            }

            ps.setString(19, haEstadoApartado);

            if (fechaReconciliacion == null || fechaReconciliacion.isEmpty()) {
                ps.setNull(20, java.sql.Types.DATE);
            } else {
                ps.setDate(20, java.sql.Date.valueOf(fechaReconciliacion));
            }

            if (fechaRecepcionEspiritu == null || fechaRecepcionEspiritu.isEmpty()) {
                ps.setNull(21, java.sql.Types.DATE);
            } else {
                ps.setDate(21, java.sql.Date.valueOf(fechaRecepcionEspiritu));
            }

            if (fechaBautismo == null || fechaBautismo.isEmpty()) {
                ps.setNull(22, java.sql.Types.DATE);
            } else {
                ps.setDate(22, java.sql.Date.valueOf(fechaBautismo));
            }

            ps.setString(23, congregacionBautismo);
            ps.setString(24, pastorBautismo);
            ps.setString(25, cargosIglesia);
            ps.setString(26, password);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                session.setAttribute("success", "Usuario creado correctamente");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

                String resumen = String.format(
                        "📋 NUEVO USUARIO REGISTRADO%n" +
                        "Nombres y Apellidos: %s%n" +
                        "Tipo de Documento: %s%n" +
                        "Número de Documento: %s%n" +
                        "Fecha de Nacimiento: %s%n" +
                        "Lugar de Nacimiento: %s%n" +
                        "Nivel Escolaridad: %s%n" +
                        "Ocupación Actual: %s%n" +
                        "Dirección: %s%n" +
                        "Teléfono: %s%n" +
                        "Correo: %s%n" +
                        "Estado Civil: %s%n" +
                        "Tribunal Eclesiástico: %s%n" +
                        "Concepto Tribunal: %s%n" +
                        "Archivo Concepto: %s%n" +
                        "Nombre Cónyuge: %s%n" +
                        "Número Hijos: %s%n" +
                        "Nombres Hijos: %s%n" +
                        "Fecha Conversión: %s%n" +
                        "Ha estado apartado: %s%n" +
                        "Fecha Reconciliación: %s%n" +
                        "Fecha Recepción Espíritu Santo: %s%n" +
                        "Fecha Bautismo: %s%n" +
                        "Congregación Bautismo: %s%n" +
                        "Pastor Bautismo: %s%n" +
                        "Cargos en la Iglesia: %s%n" +
                        "Password: %s%n",
                        nombres_apellidos, tipoDocumento, numeroDocumento, fechaNacimiento, lugarNacimiento,
                        nivelEscolaridad, ocupacionActual, direccion, telefono, correo,
                        estadoCivil, tribunalEclesiastico, conceptoTribunal, archivoConcepto,
                        nombreConyuge, numeroHijos, nombresHijos, fechaConversion, haEstadoApartado,
                        fechaReconciliacion, fechaRecepcionEspiritu, fechaBautismo,
                        congregacionBautismo, pastorBautismo, cargosIglesia, password
                );


                envc.enviarcorreo("✅ Nuevo Usuario Creado", resumen);
            } else {
                session.setAttribute("error", "No se pudo crear el usuario");
                response.sendRedirect(request.getContextPath() + "/CrearUsuario.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error al crear usuario: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/CrearUsuario.jsp");
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception e) { }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/CrearUsuario.jsp");
    }
}
