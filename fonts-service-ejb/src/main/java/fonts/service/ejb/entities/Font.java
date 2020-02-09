package fonts.service.ejb.entities;

import java.io.Serializable;
import java.util.Base64;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "FONT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Font.findAll", query = "SELECT f FROM Font f")
    , @NamedQuery(name = "Font.findById", query = "SELECT f FROM Font f WHERE f.id = :id")
    , @NamedQuery(name = "Font.findByName", query = "SELECT f FROM Font f WHERE f.name = :name")})
public class Font implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @Lob
    @Column(name = "PREVIEW")
    private byte[] preview;
    @Basic(optional = false)
    @Lob
    @Column(name = "CHARACTER_MAP")
    private byte[] characterMap;
    @Basic(optional = false)
    @Lob
    @Column(name = "FILE")
    private byte[] file;
    @Transient
    private String base64Preview;
    @Transient
    private String base64CharacterMap;

    public Font() {
    }

    public Font(Integer id) {
        this.id = id;
    }

    public Font(Integer id, String name, byte[] preview, byte[] characterMap, byte[] file) {
        this.id = id;
        this.name = name;
        this.preview = preview;
        this.characterMap = characterMap;
        this.file = file;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPreview() {
        return preview;
    }

    public void setPreview(byte[] preview) {
        this.preview = preview;
    }

    public String getBase64Preview() {
        base64Preview = Base64.getEncoder().encodeToString(this.preview);
        return base64Preview;
    }

    public void setBase64Preview(String base64Preview) {
        this.base64Preview = base64Preview;
    }

    public byte[] getCharacterMap() {
        return characterMap;
    }

    public void setCharacterMap(byte[] characterMap) {
        this.characterMap = characterMap;
    }

    public String getBase64CharacterMap() {
        base64CharacterMap = Base64.getEncoder().encodeToString(this.characterMap);
        return base64CharacterMap;
    }

    public void setBase64CharacterMap(String base64CharacterMap) {
        this.base64CharacterMap = base64CharacterMap;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Font)) {
            return false;
        }
        Font other = (Font) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fonts.service.ejb.entities.Font[ id=" + id + " ]";
    }

}
