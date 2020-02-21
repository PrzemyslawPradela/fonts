package fonts.web.servlets;

import fonts.web.Font;
import fonts.web.FontBean;
import fonts.web.FontService;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.ws.WebServiceRef;
import org.apache.commons.io.IOUtils;

@WebServlet(name = "UploadServlet", urlPatterns = {"/UploadServlet"})
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/fonts-ejb-1.0-SNAPSHOT/FontService/FontBean.wsdl")
    private FontService service;

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
        try { // Call Web Service Operation
            FontBean port = service.getFontBeanPort();
            request.setCharacterEncoding("UTF-8");
            String name = request.getParameter("name");
            Part filePart = request.getPart("file");
            InputStream fileContent = filePart.getInputStream();
            byte[] file = IOUtils.toByteArray(fileContent);
            Font font = new Font();
            font.setName(name);
            font.setFile(file);
            port.addFont(font);
            response.sendRedirect("index.html");
        } catch (Exception ex) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println(ex.getMessage());
        }

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
