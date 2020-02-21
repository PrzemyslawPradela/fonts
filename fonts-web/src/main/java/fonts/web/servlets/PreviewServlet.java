package fonts.web.servlets;

import com.google.gson.Gson;
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

@WebServlet(name = "PreviewServlet", urlPatterns = {"/PreviewServlet"})
@MultipartConfig
public class PreviewServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/fonts-ejb-1.0-SNAPSHOT/FontService/FontBean.wsdl")
    private FontService service;

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
        try { // Call Web Service Operation
            FontBean port = service.getFontBeanPort();
            String idString = request.getParameter("id");
            int id = Integer.parseInt(idString);
            Font result = port.getFont(id);
            String jsonString = new Gson().toJson(result);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(jsonString);
        } catch (Exception ex) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println(ex.getMessage());
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
        try { // Call Web Service Operation
            FontBean port = service.getFontBeanPort();
            request.setCharacterEncoding("utf-8");
            String idString = request.getParameter("id");
            String name = request.getParameter("name");
            Part filePart = request.getPart("file");
            int id = Integer.parseInt(idString);
            Font font = port.getFont(id);
            if (!name.isEmpty() || !name.equals(font.getName())) {
                font.setName(name);
                port.updateFont(font);
            } else if (filePart != null) {
                InputStream fileContent = filePart.getInputStream();
                byte[] file = IOUtils.toByteArray(fileContent);
                font.setFile(file);
                port.updateFont(font);
            }
            response.sendRedirect("preview.html?id=" + id);
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
