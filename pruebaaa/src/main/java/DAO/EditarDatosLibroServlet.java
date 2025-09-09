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

@WebServlet("/EditarDatosLibroServlet")
public class EditarDatosLibroServlet extends HttpServlet {
    
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
            
            String sql = "SELECT * FROM tbl_libro_general WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("cliente", rs);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editarDatosLibroContable.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("index.jsp?error=datos+no+encontrado");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("index.jsp?error=ID+invalido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=Error+al+cargar+datos");
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
            int id = Integer.parseInt(request.getParameter("id"));
            String fecha = request.getParameter("fecha");
            String numDoc = request.getParameter("numero_documento");
            String detalle = request.getParameter("detalle");
            String debe = request.getParameter("debe");
            String haber = request.getParameter("haber");
            String saldo = request.getParameter("debe");

            con = new Conexion().conectar();
            
            String sql = "UPDATE tbl_libro_general SET fecha=?, numero_documento=?, debe=?, haber=?, saldo=?, detalle=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setString(2, numDoc);
            ps.setString(3, debe);
            ps.setString(4, haber);
            ps.setString(5, saldo);
            ps.setString(6, detalle);
            ps.setInt(7, id);

            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                response.sendRedirect("index.jsp?success=Datos+actualizado+correctamente");
            } else {
                response.sendRedirect("index.jsp?error=No+se+pudo+actualizar+los+datos");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=Error+al+actualizar+datos");
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception e) { }
            }
        }
    }
}