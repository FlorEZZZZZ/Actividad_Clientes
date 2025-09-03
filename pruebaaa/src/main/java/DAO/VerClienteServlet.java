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

@WebServlet("/VerClienteServlet")
public class VerClienteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        
        // Obtener el parámetro CORRECTO (debe coincidir con el enlace)
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
                // Pasar los datos al JSP
                request.setAttribute("cliente", rs);
                RequestDispatcher dispatcher = request.getRequestDispatcher("verCliente.jsp");
                dispatcher.forward(request, response);
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