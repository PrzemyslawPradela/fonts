package fonts.ws;

import fonts.ws.entities.Font;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@WebService(serviceName = "FontService")
public class FontWebService {

    /**
     * Web service operation
     *
     * @param font
     */
    @WebMethod(operationName = "addFont")
    @Oneway
    public void addFont(@WebParam(name = "font") Font font) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fontsPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(font);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    /**
     * Web service operation
     *
     * @param font
     */
    @WebMethod(operationName = "updateFont")
    @Oneway
    public void updateFont(@WebParam(name = "font") Font font) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fontsPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(font);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    /**
     * Web service operation
     *
     * @param id
     */
    @WebMethod(operationName = "deleteFont")
    @Oneway
    public void deleteFont(@WebParam(name = "id") int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fontsPU");
        EntityManager em = emf.createEntityManager();
        Font font = (Font) em.createNamedQuery("Font.findById").setParameter("id", id).getSingleResult();
        em.getTransaction().begin();
        em.remove(font);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    /**
     * Web service operation
     *
     * @param id
     * @return font
     */
    @WebMethod(operationName = "getFont")
    public Font getFont(@WebParam(name = "id") int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fontsPU");
        EntityManager em = emf.createEntityManager();
        Font font = (Font) em.createNamedQuery("Font.findById").setParameter("id", id).getSingleResult();
        em.close();
        emf.close();
        byte[] bytes = font.getFile();
        String base64 = Base64.getEncoder().encodeToString(bytes);
        font.setBase64File(base64);
        return font;
    }

    /**
     * Web service operation
     *
     * @return fonts
     */
    @WebMethod(operationName = "getFonts")
    public List<Font> getFonts() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fontsPU");
        EntityManager em = emf.createEntityManager();
        List<Font> fontsTmp = em.createNamedQuery("Font.findAll").getResultList();
        em.close();
        emf.close();
        List<Font> fonts = new ArrayList<>();
        for (Font font : fontsTmp) {
            byte[] bytes = font.getFile();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            font.setBase64File(base64);
            fonts.add(font);
        }
        return fonts;
    }
}
