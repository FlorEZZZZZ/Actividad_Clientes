package DAO;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfWriter;

@WebServlet("/GenerarPDFServlet")
public class GenerarPDFServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("index.jsp?error=ID+no+proporcionado");
            return;
        }

        int idCliente = Integer.parseInt(idParam);

        // Configurar la respuesta HTTP para PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=cliente_" + idCliente + ".pdf");

        Document documento = new Document();

        try (OutputStream out = response.getOutputStream()) {
            PdfWriter.getInstance(documento, out);
            documento.open();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/ipuc_vista_hermosa", "root", "2995196");

                PreparedStatement ps = con.prepareStatement(
                    "SELECT id, cedula, nombres, apellidos, direccion, telefono, rol, password " +
                    "FROM tbl_usuarios WHERE id=?"
                );
                ps.setInt(1, idCliente);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    documento.add(new Paragraph("Datos del Usuario"));
                    documento.add(new Paragraph("-------------------"));
                    documento.add(new Paragraph("ID Cliente: " + rs.getInt("id")));
                    documento.add(new Paragraph("Cédula: " + rs.getInt("cedula")));
                    documento.add(new Paragraph("Nombres: " + rs.getString("nombres")));
                    documento.add(new Paragraph("Apellidos: " + rs.getString("apellidos")));
                    documento.add(new Paragraph("Dirección: " + rs.getString("direccion")));
                    documento.add(new Paragraph("Teléfono: " + rs.getString("telefono")));
                    documento.add(new Paragraph("Rol: " + rs.getString("rol")));
                    documento.add(new Paragraph("Contraseña: " + rs.getString("password")));
                } else {
                    documento.add(new Paragraph("Cliente no encontrado"));
                }

                con.close();
            } catch (Exception e) {
                documento.add(new Paragraph("Error: " + e.getMessage()));
            }

            documento.close();

        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }
}
