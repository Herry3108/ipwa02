import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

@Named
@ApplicationScoped
public class Startseite {

  private final PersonBergungGeisternetzDAO personBergungGeisternetzDAO = new PersonBergungGeisternetzDAO();

  private List<PersonBergungGeisternetz> letzteFuenfBergungen = null;

  private List<PersonBergungGeisternetz> letzteFuenfOffeneMeldungen = null;

  public Startseite() {}

  public List<PersonBergungGeisternetz > getLetzteFuenfErledigteBergungen() {
    this.letzteFuenfBergungen = personBergungGeisternetzDAO.getLetzteFuenfGeisternetzBergungen();
    return this.letzteFuenfBergungen;
  }

  public List<PersonBergungGeisternetz> getLetzteFuenfOffeneMeldungen() {
    this.letzteFuenfOffeneMeldungen = this.personBergungGeisternetzDAO.getLetzteFuenfOffeneMeldungen();
    return this.letzteFuenfOffeneMeldungen;
  }
}
