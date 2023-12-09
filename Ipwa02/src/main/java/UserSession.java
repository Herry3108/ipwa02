import jakarta.ejb.Startup;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
@Startup
public class UserSession implements Serializable {

  private Boolean istNutzungAnonym = false;

  private Integer id = 0;

  private String name = "";

  private String vorname = "";

  private Integer anrede = 1;

  private String telefonnummer = "";

  private Boolean istMeldend;

  private Boolean istBergend;

  public UserSession() {}

  public String getName() {
    return name;
  }

  public String getVorname() {
    return vorname;
  }

  public Integer getAnrede() {
    return anrede;
  }

  public String getTelefonnummer() {
    return telefonnummer;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  public void setAnrede(Integer anrede) {
    this.anrede = anrede;
  }

  public void setTelefonnummer(String telefonnummer) {
    this.telefonnummer = telefonnummer;
  }

  public Boolean getIstMeldend() {
    return istMeldend;
  }

  public void setIstMeldend(Boolean istMeldend) {
    this.istMeldend = istMeldend;
  }

  public Boolean getIstBergend() {
    return istBergend;
  }

  public void setIstBergend(Boolean istBergend) {
    this.istBergend = istBergend;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Boolean getIstNutzungAnonym() {
    return istNutzungAnonym;
  }

  public void setIstNutzungAnonym(Boolean istNutzungAnonym) {
    this.istNutzungAnonym = istNutzungAnonym;
  }
}
