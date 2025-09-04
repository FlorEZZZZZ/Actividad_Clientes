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

String idParam = request.getParameter("idCliente");
        
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("index.jsp?error=ID no proporcionado");
            return;
        }

        try {
            int idCliente = Integer.parseInt(idParam);
            Connection con = new Conexion().conectar();
            
            // Consulta CORRECTA según tu estructura de tabla
            String sql = "SELECT * FROM tbl_cliente WHERE idCliente = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // recoger los datos
                request.setAttribute("cliente", rs);
                int id = rs.getInt("idCliente"); 
        		String cedula = rs.getString("cedula");
        		String nombres= rs.getString("nombres");
        		String apellidos = rs.getString("apellidos");
        		String direccion = rs.getString("direccion");
        		String telefono = rs.getString("telefono"); 
        		String rol = rs.getString("rol");
        		String password = rs.getString("password"); 
        		String correo = rs.getString("correo");
        		EnviarCorreos envc = new EnviarCorreos();
        		envc.EnviarDocumentos(id, cedula, nombres, apellidos, direccion, telefono, rol, password, correo);
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                response.sendRedirect("index.jsp?error=Cliente no encontrado");
            }
            
            con.close();
            
        } catch (NumberFormatException e) {
            response.sendRedirect("index.jsp?error=ID inválido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=Error al obtener cliente");
        }
        
    }
}


