package fonts.web.servlets;

import fonts.web.Font;
import fonts.web.FontBean;
import fonts.web.FontService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Normalizer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

@WebServlet(name = "DownloadServlet", urlPatterns = {"/DownloadServlet"})
public class DownloadServlet extends HttpServlet {

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
            byte[] file = result.getFile();
            String fileName = Normalizer.normalize(result.getName().replaceAll(" ", "_").toLowerCase().concat(".ttf"), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            OutputStream outStream;
            try (InputStream inputStream = new ByteArrayInputStream(file)) {
                int fileLength = inputStream.available();
                ServletContext context = getServletContext();
                String mimeType = context.getMimeType(fileName);
                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }
                response.setContentType(mimeType);
                response.setContentLength(fileLength);
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", fileName);
                response.setHeader(headerKey, headerValue);
                outStream = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
            }
            outStream.close();
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
