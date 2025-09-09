package DAO;

import conexion.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/BorrarDatosServlet")
public class BorrarDatosServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        String idParam = request.getParameter("id");
        
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("index.jsp?error=ID+no+proporcionado");
            return;
        }

        Connection con = null;
        try {
            int idCliente = Integer.parseInt(idParam);
            con = new Conexion().conectar();
            
            String sql = "DELETE FROM tbl_libro_general WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                response.sendRedirect("index.jsp?success=Dato+eliminado+correctamente");
            } else {
                response.sendRedirect("index.jsp?error=No+se+pudo+eliminar+el+Dato");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("index.jsp?error=ID+invalido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=Error+al+eliminar+Dato:+"+e.getMessage());
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception e) { }
            }
        }
    }
}