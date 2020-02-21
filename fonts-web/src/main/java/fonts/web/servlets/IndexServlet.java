package fonts.web.servlets;

import com.google.gson.Gson;
import fonts.web.Font;
import fonts.web.FontBean;
import fonts.web.FontService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

@WebServlet(name = "IndexServlet", urlPatterns = {"/IndexServlet"})
public class IndexServlet extends HttpServlet {

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
            List<Font> result = port.getFonts();
            String jsonString = new Gson().toJson(result);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(jsonString);
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
