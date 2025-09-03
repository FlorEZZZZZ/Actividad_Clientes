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
import jakarta.servlet.RequestDispatcher;

@WebServlet("/EditarClienteServlet")
public class EditarClienteServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        String idParam = request.getParameter("idCliente");
        
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("index.jsp?error=ID+no+proporcionado");
            return;
        }

        Connection con = null;
        try {
            int idCliente = Integer.parseInt(idParam);
            con = new Conexion().conectar();
            
            String sql = "SELECT * FROM tbl_cliente WHERE idCliente = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("cliente", rs);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editarCliente.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("index.jsp?error=Cliente+no+encontrado");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("index.jsp?error=ID+invalido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=Error+al+cargar+cliente");
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception e) { }
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        Connection con = null;
        try {
            // Obtener parámetros
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            String cedula = request.getParameter("cedula");
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String direccion = request.getParameter("direccion");
            String telefono = request.getParameter("telefono");
            String rol = request.getParameter("rol");
            String password = request.getParameter("password");

            con = new Conexion().conectar();
            
            String sql = "UPDATE tbl_cliente SET cedula=?, nombres=?, apellidos=?, direccion=?, telefono=?, rol=?, password=? WHERE idCliente=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            ps.setString(2, nombres);
            ps.setString(3, apellidos);
            ps.setString(4, direccion);
            ps.setString(5, telefono);
            ps.setString(6, rol);
            ps.setString(7, password);
            ps.setInt(8, idCliente);
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                response.sendRedirect("index.jsp?success=Cliente+actualizado+correctamente");
            } else {
                response.sendRedirect("index.jsp?error=No+se+pudo+actualizar+el+cliente");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=Error+al+actualizar+cliente");
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception e) { }
            }
        }
    }
}