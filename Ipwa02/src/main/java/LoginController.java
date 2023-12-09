import jakarta.ejb.Startup;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
@Startup
public class LoginController implements Serializable, ILoginController {

  private String username = "";

  private String password;

  private Boolean istMeldend;

  private Boolean istBergend;

  private final LoginDAO loginDAO = new LoginDAO();

  private final PersonDAO personDAO = new PersonDAO();

  @Inject
  private UserSession session;

  @Override
  public void loginAnonym() {
    session.setIstNutzungAnonym(true);

    Router.navigiere("index", false);
  }

  @Override
  public void login() {

    if(username.isEmpty()) {
      return;
    }

    if(password.isEmpty()) {
      return;
    }

    var userId = this.loginDAO.validateUserLogin(this.username, this.password);

    if (userId == null) {
      return;
    }

    Person eingeloggtePerson = this.personDAO.getSinglePerson(userId);
    eingeloggtePerson = this.updatePersonRechte(eingeloggtePerson);
    this.setEingeloggtePersonSessionVariables(eingeloggtePerson);

    Router.navigiere("index", true);
  }

  private Person updatePersonRechte(Person person) {
    person.setIstMeldendePerson(this.istMeldend);
    person.setIstBergendePerson(this.istBergend);

    return this.personDAO.updatePersonRechte(person);
  }

  private void setEingeloggtePersonSessionVariables(Person person) {
    session.setName(person.getPersonDaten().getNachname());
    session.setVorname(person.getPersonDaten().getVorname());
    session.setAnrede(person.getPersonDaten().getAnrede());
    session.setTelefonnummer(person.getPersonDaten().getTelefonnummer());
    session.setIstMeldend(person.isIstMeldendePerson());
    session.setIstBergend(person.isIstBergendePerson());
    session.setId(person.getId());
  }
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public void toggleIstMeldend() {
    this.istMeldend = !this.istMeldend;
  }

  public void toggleIstBergend() {
    this.istBergend = !this.istBergend;
  }

  public void setIstBergend(Boolean istBergend) {
    this.istBergend = istBergend;
  }
}