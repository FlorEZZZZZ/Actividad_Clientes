package DAO;

import conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditarClienteServlet")
public class EditarClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id_usuario");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("index.jsp?error=ID+no+proporcionado");
            return;
        }

        Connection con = null;
        try {
            int idUsuario = Integer.parseInt(idParam);
            con = new Conexion().conectar();

            String sql = "SELECT * FROM tbl_usuarios WHERE id_usuario = ?";
            PreparedStatement ps = con.prepareStatement(
                sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            // Verifica si hay filas sin consumir el cursor
            if (!rs.isBeforeFirst()) {
                // no hay datos
                try { rs.close(); } catch (Exception ignore) {}
                try { ps.close(); } catch (Exception ignore) {}
                try { con.close(); } catch (Exception ignore) {}
                response.sendRedirect("index.jsp?error=Cliente+no+encontrado");
                return;
            }

            // Deja el cursor en el inicio para que JSP haga rs.next()
            request.setAttribute("cliente", rs);
            request.setAttribute("conexion", con); // la cerramos en el JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("editarCliente.jsp");
            dispatcher.forward(request, response);

            // OJO: no cierres aquí. Se cierran en el JSP.

        } catch (NumberFormatException e) {
            if (con != null) try { con.close(); } catch (Exception ignore) {}
            response.sendRedirect("index.jsp?error=ID+invalido");
        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) try { con.close(); } catch (Exception ignore) {}
            response.sendRedirect("index.jsp?error=Error+al+cargar+cliente");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        try {
            con = new Conexion().conectar();

            int id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
            String nombres_apellidos     = request.getParameter("nombres_apellidos");
            String tipo_documento        = request.getParameter("tipo_documento");
            String numero_documento      = request.getParameter("numero_documento");
            String fecha_nacimiento      = request.getParameter("fecha_nacimiento");
            String lugar_nacimiento      = request.getParameter("lugar_nacimiento");
            String nivel_escolaridad     = request.getParameter("nivel_escolaridad");
            String ocupacion_actual      = request.getParameter("ocupacion_actual");
            String direccion_residencia  = request.getParameter("direccion_residencia");
            String telefono              = request.getParameter("telefono");
            String correo_electronico    = request.getParameter("correo_electronico");
            String estado_civil          = request.getParameter("estado_civil");
            String tribunal_eclesiastico = request.getParameter("tribunal_eclesiastico");
            String concepto_tribunal     = request.getParameter("concepto_tribunal");
            String archivo_concepto      = request.getParameter("archivo_concepto");
            String nombre_conyuge        = request.getParameter("nombre_conyuge");
            String numero_hijos          = request.getParameter("numero_hijos");
            String nombres_hijos         = request.getParameter("nombres_hijos");
            String fecha_conversion      = request.getParameter("fecha_conversion");
            String ha_estado_apartado    = request.getParameter("ha_estado_apartado");
            String fecha_reconciliacion  = request.getParameter("fecha_reconciliacion");
            String fecha_recepcion_espiritu = request.getParameter("fecha_recepcion_espiritu");
            String fecha_bautismo        = request.getParameter("fecha_bautismo");
            String congregacion_bautismo = request.getParameter("congregacion_bautismo");
            String pastor_bautismo       = request.getParameter("pastor_bautismo");
            String cargos_iglesia        = request.getParameter("cargos_iglesia");
            String password              = request.getParameter("password");

            String sql = "UPDATE tbl_usuarios SET "
                + "nombres_apellidos=?, tipo_documento=?, numero_documento=?, fecha_nacimiento=?, "
                + "lugar_nacimiento=?, nivel_escolaridad=?, ocupacion_actual=?, direccion_residencia=?, "
                + "telefono=?, correo_electronico=?, estado_civil=?, tribunal_eclesiastico=?, "
                + "concepto_tribunal=?, archivo_concepto=?, nombre_conyuge=?, numero_hijos=?, "
                + "nombres_hijos=?, fecha_conversion=?, ha_estado_apartado=?, fecha_reconciliacion=?, "
                + "fecha_recepcion_espiritu=?, fecha_bautismo=?, congregacion_bautismo=?, "
                + "pastor_bautismo=?, cargos_iglesia=?, password=? "
                + "WHERE id_usuario=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,  nombres_apellidos);
            ps.setString(2,  tipo_documento);
            ps.setString(3,  numero_documento);
            setNullableDate(ps, 4,  fecha_nacimiento);       // <- fechas opcionales seguras
            ps.setString(5,  lugar_nacimiento);
            ps.setString(6,  nivel_escolaridad);
            ps.setString(7,  ocupacion_actual);
            ps.setString(8,  direccion_residencia);
            ps.setString(9,  telefono);
            ps.setString(10, correo_electronico);
            ps.setString(11, estado_civil);
            ps.setString(12, tribunal_eclesiastico);
            ps.setString(13, concepto_tribunal);
            ps.setString(14, archivo_concepto);
            ps.setString(15, nombre_conyuge);
            ps.setString(16, numero_hijos);
            ps.setString(17, nombres_hijos);
            setNullableDate(ps, 18, fecha_conversion);
            ps.setString(19, ha_estado_apartado);
            setNullableDate(ps, 20, fecha_reconciliacion);
            setNullableDate(ps, 21, fecha_recepcion_espiritu);
            setNullableDate(ps, 22, fecha_bautismo);
            ps.setString(23, congregacion_bautismo);
            ps.setString(24, pastor_bautismo);
            ps.setString(25, cargos_iglesia);
            ps.setString(26, password);
            ps.setInt(27, id_usuario);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("index.jsp?success=Cliente+actualizado+correctamente");
            } else {
                response.sendRedirect("index.jsp?error=No+se+pudo+actualizar+el+cliente");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=Error+al+actualizar+cliente");
        } finally {
            if (con != null) try { con.close(); } catch (Exception ignore) {}
        }
    }

    // Si tu columna es DATE en MySQL, esto evita el error "Incorrect date value: ''"
    private static void setNullableDate(PreparedStatement ps, int index, String val) throws Exception {
        if (val == null || val.trim().isEmpty()) {
            ps.setNull(index, java.sql.Types.DATE);
        } else {
            ps.setDate(index, java.sql.Date.valueOf(val)); // val: "YYYY-MM-DD"
        }
    }
}
