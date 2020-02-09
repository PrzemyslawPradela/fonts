package fonts.service.ejb;

import fonts.service.ejb.entities.Font;
import java.util.List;
import javax.ejb.Local;

@Local
public interface FontsSessionBeanLocal {

    void addFont(Font font);

    void editFont(Font font);

    void removeFont(int id);

    Font getFont(int id);

    List<Font> getFonts();
}
