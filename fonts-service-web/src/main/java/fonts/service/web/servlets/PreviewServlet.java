package fonts.service.web.servlets;

import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.ws.WebServiceRef;
import org.apache.commons.io.IOUtils;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "PreviewServlet", urlPatterns = {"/PreviewServlet"})
public class PreviewServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/FontsWebService/FontsWebService.wsdl")
    private fonts.service.web.service.FontsWebService_Service service;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sid = request.getParameter("id");
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try { // Call Web Service Operation
                fonts.service.web.service.FontsWebService port = service.getFontsWebServicePort();
                int id = Integer.parseInt(sid);
                fonts.service.web.service.Font result = port.getFont(id);
                String jsonString = new GsonBuilder().create().toJson(result);
                out.println(jsonString);
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);
        String name = request.getParameter("name");
        Part filePart = request.getPart("file");

        try { // Call Web Service Operation
            fonts.service.web.service.FontsWebService port = service.getFontsWebServicePort();
            fonts.service.web.service.Font font = port.getFont(id);
            if (!name.isEmpty()) {
                font.setName(name);
            }
            if (filePart != null) {
                InputStream fileContent = filePart.getInputStream();
                byte[] file = IOUtils.toByteArray(fileContent);
                font.setFile(file);
            }
            port.editFont(font);
        } catch (Exception ex) {
        }
        response.sendRedirect("preview.html?id=" + sid);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
