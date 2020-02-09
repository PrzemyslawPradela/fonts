package fonts.service.ejb;

import fonts.service.ejb.entities.Font;
import java.util.ArrayList;
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

        byte[] preview = font.getPreview();
        String base64Preview = Base64.getEncoder().encodeToString(preview);
        font.setBase64Preview(base64Preview);

        byte[] characterMap = font.getCharacterMap();
        String base64CharacterMap = Base64.getEncoder().encodeToString(characterMap);
        font.setBase64CharacterMap(base64CharacterMap);

        return font;
    }

    @Override
    public List<Font> getFonts() {
        List<Font> fonts = entityManager.createNamedQuery("Font.findAll").getResultList();
        List<Font> fontsWithBase64Preview = new ArrayList<>();
        for (Font font
                : fonts) {
            byte[] bytes = font.getPreview();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            font.setBase64Preview(base64);
            fontsWithBase64Preview.add(font);
        }
        return fontsWithBase64Preview;
    }
}
