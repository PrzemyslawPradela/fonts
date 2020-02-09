package fonts.service.ejb;

import fonts.service.ejb.entities.Font;
import java.util.Base64;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Stateless
public class FontsSessionBean implements FontsSessionBeanLocal {

    @PersistenceContext(unitName = "fonts.service.ejb_fonts-service-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager entityManager;

    @Override
    public void addFont(Font font) {
        entityManager.persist(font);
    }

    @Override
    public void editFont(Font font) {
        entityManager.merge(font);
    }

    @Override
    public void removeFont(int id) {
        Font font = (Font) entityManager.createNamedQuery("Font.findById").setParameter("id", id).getSingleResult();
        if (font != null) {
            entityManager.remove(font);
        }
    }

    @Override
    public Font getFont(int id) {
        Font font = (Font) entityManager.createNamedQuery("Font.findById").setParameter("id", id).getSingleResult();
        if (font == null) {
            throw new EntityNotFoundException("Nie można znaleźć czcionki z ID " + id);
        }

        byte[] file = font.getFile();
        String base64File = Base64.getEncoder().encodeToString(file);
        font.setBase64File(base64File);

        return font;
    }

    @Override
    public List<Font> getFonts() {
        return entityManager.createNamedQuery("Font.findAll").getResultList();
    }
}
