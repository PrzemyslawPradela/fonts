package fonts.web.servlets;

import com.google.gson.Gson;
import fonts.ejb.FontServiceBean;
import fonts.ws.client.Font;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

@WebServlet(name = "PreviewServlet", urlPatterns = {"/PreviewServlet"})
@MultipartConfig
public class PreviewServlet extends HttpServlet {

    @EJB
    private FontServiceBean fontServiceBean;

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
        String idString = request.getParameter("id");
        int id = Integer.parseInt(idString);
        Font font = fontServiceBean.getFont(id);
        String jsonString = new Gson().toJson(font);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonString);
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
        request.setCharacterEncoding("utf-8");
        String idString = request.getParameter("id");
        String name = request.getParameter("name");
        Part filePart = request.getPart("file");
        int id = Integer.parseInt(idString);
        Font font = fontServiceBean.getFont(id);
        if (!name.isEmpty() && !name.equals(font.getName())) {
            font.setName(name);
            fontServiceBean.updateFont(font);
        }
        if (filePart != null) {
            InputStream fileContent = filePart.getInputStream();
            byte[] file = IOUtils.toByteArray(fileContent);
            if (!Arrays.equals(file, font.getFile())) {
                font.setFile(file);
                fontServiceBean.updateFont(font);
            }
        }
        response.sendRedirect("preview.html?id=" + id);
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
