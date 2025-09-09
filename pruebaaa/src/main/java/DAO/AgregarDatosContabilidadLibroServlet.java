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
import jakarta.servlet.http.HttpSession;
import java.sql.ResultSet;
import conexion.EnviarCorreos;
@WebServlet("/AgregarDatosContabilidadLibroServlet")
public class AgregarDatosContabilidadLibroServlet extends HttpServlet {
    
    // Eliminamos la función de encriptarPassword ya que no se requiere hashing.
    // private String encriptarPassword(String password) {
    //     try {
    //         MessageDigest digest = MessageDigest.getInstance("SHA-256");
    //         byte[] hash = digest.digest(password.getBytes("UTF-8"));
    //         StringBuilder hexString = new StringBuilder();
            
    //         for (byte b : hash) {
    //             String hex = Integer.toHexString(0xff & b);
    //             if (hex.length() == 1) hexString.append('0');
    //             hexString.append(hex);
    //         }
    //         
    //         return hexString.toString();
    //     } catch (Exception e) {
    //         throw new RuntimeException(e);
    //     }
    // }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Connection con = null;
        EnviarCorreos envc = new EnviarCorreos();
        
        try {
            // Limpiar mensajes anteriores
            session.removeAttribute("error");
            session.removeAttribute("success");
            
            // Obtener parámetros
            String fecha = request.getParameter("fecha");
            String numDoc = request.getParameter("numDoc");
            String detalle = request.getParameter("detalle");
            String debe = request.getParameter("debe");
            String haber = request.getParameter("haber");
            String saldo = request.getParameter("saldo");
            
            con = new Conexion().conectar();
            System.out.println("DEBUG: Conexión establecida");
            
            // Verificar si la cédula ya existe
            String sqlVerificar = "SELECT COUNT(*) FROM tbl_libro_general WHERE numero_documento = ?";
            PreparedStatement psVerificar = con.prepareStatement(sqlVerificar);
            psVerificar.setString(1, numDoc);
            ResultSet rs = psVerificar.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                session.setAttribute("error", "El numero de documento " + numDoc + " ya está registrada");
                response.sendRedirect(request.getContextPath() + "/CrearDatoContabilidadLibro.jsp");
                return;
            }
            
            // Insertar nuevo usuario
            String sql = "INSERT INTO tbl_libro_general (fecha, numero_documento, debe, haber, saldo, detalle) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setString(2, numDoc);
            ps.setString(3, debe);
            ps.setString(4, haber);
            ps.setString(5, debe);
            ps.setString(6, detalle);

            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                String resumen = String.format(
                	    "Fecha: %s%n" +
                	    "Numero de Documento: %s%n" +
                	    "detalle: %s%n" +
                	    "Debe: %s%n" +
                	    "Haber: %s%n" +
                	    "Saldo: %s%n",
                	    fecha, numDoc, detalle, debe, haber, saldo);

                
                envc.enviarcorreo("Exito al Crear datos en el libro de contabilidad:", resumen);
                
                session.setAttribute("success", "Dato creado correctamente");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                session.setAttribute("error", "No se pudo crear el dato");
                response.sendRedirect(request.getContextPath() + "/CrearDatoContabilidadLibro.jsp");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error al crear usuario: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/CrearDatoContabilidadLibro.jsp");
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception e) { }
            }
        }
    }
    
    // Método doGet opcional
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/CrearDatoContabilidadLibro.jsp");
    }
}
