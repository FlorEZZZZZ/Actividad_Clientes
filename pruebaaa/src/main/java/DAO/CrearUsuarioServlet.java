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

@WebServlet("/CrearUsuarioServlet")
public class CrearUsuarioServlet extends HttpServlet {
    
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
        
        try {
            // Limpiar mensajes anteriores
            session.removeAttribute("error");
            session.removeAttribute("success");
            
            // Obtener parámetros
            String cedula = request.getParameter("cedula");
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String direccion = request.getParameter("direccion");
            String telefono = request.getParameter("telefono");
            String rol = request.getParameter("rol");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            
            System.out.println("DEBUG: Parámetros recibidos - Cédula: " + cedula);
            
            // Validaciones
            if (password == null || !password.equals(confirmPassword)) {
                session.setAttribute("error", "Las contraseñas no coinciden");
                response.sendRedirect(request.getContextPath() + "/crearCliente.jsp");
                return;
            }
            
            if (password.length() < 6) {
                session.setAttribute("error", "La contraseña debe tener al menos 6 caracteres");
                response.sendRedirect(request.getContextPath() + "/crearCliente.jsp");
                return;
            }
            
            // Aquí ya no se encripta la contraseña, se usa tal cual se recibe del formulario
            String passwordOriginal = password; 
            System.out.println("DEBUG: Contraseña recibida: " + passwordOriginal);
            
            con = new Conexion().conectar();
            System.out.println("DEBUG: Conexión establecida");
            
            // Verificar si la cédula ya existe
            String sqlVerificar = "SELECT COUNT(*) FROM tbl_cliente WHERE cedula = ?";
            PreparedStatement psVerificar = con.prepareStatement(sqlVerificar);
            psVerificar.setString(1, cedula);
            ResultSet rs = psVerificar.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                session.setAttribute("error", "La cédula " + cedula + " ya está registrada");
                response.sendRedirect(request.getContextPath() + "/CrearCliente.jsp");
                return;
            }
            
            // Insertar nuevo usuario
            String sql = "INSERT INTO tbl_cliente (cedula, nombres, apellidos, direccion, telefono, rol, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            ps.setString(2, nombres);
            ps.setString(3, apellidos);
            ps.setString(4, direccion);
            ps.setString(5, telefono);
            ps.setString(6, rol);
            ps.setString(7, passwordOriginal); // Guardamos la contraseña directamente
            
            int rowsAffected = ps.executeUpdate();
            System.out.println("DEBUG: Filas afectadas: " + rowsAffected);
            
            if (rowsAffected > 0) {
                session.setAttribute("success", "Usuario creado correctamente");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                session.setAttribute("error", "No se pudo crear el usuario");
                response.sendRedirect(request.getContextPath() + "/crearCliente.jsp");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error al crear usuario: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/crearCliente.jsp");
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
        response.sendRedirect(request.getContextPath() + "/crearCliente.jsp");
    }
}
