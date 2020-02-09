package fonts.service.ejb.web.service;

import fonts.service.ejb.FontsSessionBeanLocal;
import fonts.service.ejb.entities.Font;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "FontsWebService")
@Stateless()
public class FontsWebService {

    @EJB
    private FontsSessionBeanLocal ejbRef;

    @WebMethod(operationName = "addFont")
    @Oneway
    public void addFont(@WebParam(name = "font") Font font) {
        ejbRef.addFont(font);
    }

    @WebMethod(operationName = "editFont")
    @Oneway
    public void editFont(@WebParam(name = "font") Font font) {
        ejbRef.editFont(font);
    }

    @WebMethod(operationName = "removeFont")
    @Oneway
    public void removeFont(@WebParam(name = "id") int id) {
        ejbRef.removeFont(id);
    }

    @WebMethod(operationName = "getFont")
    public Font getFont(@WebParam(name = "id") int id) {
        return ejbRef.getFont(id);
    }

    @WebMethod(operationName = "getFonts")
    public List<Font> getFonts() {
        return ejbRef.getFonts();
    }

}
