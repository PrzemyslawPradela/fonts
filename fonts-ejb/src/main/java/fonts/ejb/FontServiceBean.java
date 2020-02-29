package fonts.ejb;

import fonts.ejb.soap.client.Font;
import fonts.ejb.soap.client.FontService;
import fonts.ejb.soap.client.FontWebService;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;

@Stateless
@LocalBean
public class FontServiceBean {

    @WebServiceRef(wsdlLocation = "http://localhost:8080/fonts-soap/FontService?wsdl")
    private FontService service;

    public void addFont(Font font) {
        try { // Call Web Service Operation
            FontWebService port = service.getFontWebServicePort();
            port.addFont(font);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateFont(Font font) {
        try { // Call Web Service Operation
            FontWebService port = service.getFontWebServicePort();
            port.updateFont(font);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void deleteFont(int id) {
        try { // Call Web Service Operation
            FontWebService port = service.getFontWebServicePort();
            port.deleteFont(id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public Font getFont(int id) {
        Font font = null;
        try { // Call Web Service Operation
            FontWebService port = service.getFontWebServicePort();
            Font result = port.getFont(id);
            font = result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return font;
    }

    public List<Font> getFonts() {
        List<Font> fonts = null;
        try { // Call Web Service Operation
            FontWebService port = service.getFontWebServicePort();
            List<Font> result = port.getFonts();
            fonts = result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return fonts;
    }
}
