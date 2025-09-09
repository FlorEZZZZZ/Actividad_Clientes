package DAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import conexion.EnviarCorreos;

import conexion.Conexion;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/EnviarCorreosServlet")
public class EnviarCorreosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("index.jsp?error=ID no proporcionado");
            return;
        }

        try {
            int idCliente = Integer.parseInt(idParam);
            Connection con = new Conexion().conectar();

            String sql = "SELECT * FROM tbl_usuarios WHERE id_usuario = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // 📌 Recoger los datos
                int id = rs.getInt("id_usuario");
                String nombres_apellidos = rs.getString("nombres_apellidos");
                String tipoDocumento = rs.getString("tipo_documento");
                String numeroDocumento = rs.getString("numero_documento");
                String fechaNacimiento = rs.getString("fecha_nacimiento");
                String lugarNacimiento = rs.getString("lugar_nacimiento");
                String nivelEscolaridad = rs.getString("nivel_escolaridad");
                String ocupacionActual = rs.getString("ocupacion_actual");
                String direccion = rs.getString("direccion_residencia");
                String telefono = rs.getString("telefono");
                String correo = rs.getString("correo_electronico");
                String estadoCivil = rs.getString("estado_civil");
                String tribunalEclesiastico = rs.getString("tribunal_eclesiastico");
                String conceptoTribunal = rs.getString("concepto_tribunal");
                String archivoConcepto = rs.getString("archivo_concepto");
                String nombreConyuge = rs.getString("nombre_conyuge");
                String numeroHijos = rs.getString("numero_hijos");
                String nombresHijos = rs.getString("nombres_hijos");
                String fechaConversion = rs.getString("fecha_conversion");
                String haEstadoApartado = rs.getString("ha_estado_apartado");
                String fechaReconciliacion = rs.getString("fecha_reconciliacion");
                String fechaRecepcionEspiritu = rs.getString("fecha_recepcion_espiritu");
                String fechaBautismo = rs.getString("fecha_bautismo");
                String congregacionBautismo = rs.getString("congregacion_bautismo");
                String pastorBautismo = rs.getString("pastor_bautismo");
                String cargosIglesia = rs.getString("cargos_iglesia");
                String password = rs.getString("password");

                // 📧 Enviar correo con todos los datos
                EnviarCorreos envc = new EnviarCorreos();
                envc.EnviarDocumentos(
                    id, nombres_apellidos, tipoDocumento, numeroDocumento, fechaNacimiento,
                    lugarNacimiento, nivelEscolaridad, ocupacionActual, direccion, telefono, correo,
                    estadoCivil, tribunalEclesiastico, conceptoTribunal, archivoConcepto,
                    nombreConyuge, numeroHijos, nombresHijos, fechaConversion, haEstadoApartado,
                    fechaReconciliacion, fechaRecepcionEspiritu, fechaBautismo,
                    congregacionBautismo, pastorBautismo, cargosIglesia, password);

                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                response.sendRedirect("index.jsp?error=Cliente no encontrado");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=Error en el servidor: " + e.getMessage());
        }
    }
}
