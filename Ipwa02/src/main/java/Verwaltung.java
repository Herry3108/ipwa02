import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class Verwaltung {

  @Inject
  private UserSession session;

  private final GeisternetzDAO geisternetzDAO = new GeisternetzDAO();

  private final PersonBergungGeisternetzDAO personBergungGeisternetzDAO = new PersonBergungGeisternetzDAO();

  private List<Geisternetz> allOffeneGeisternetze = new ArrayList<Geisternetz>();

  private List<Geisternetz> eingeloggtePersonZuweisungen = new ArrayList<Geisternetz>();

  private final PersonDAO personDAO = new PersonDAO();

  public Verwaltung() {}

  public List<Geisternetz> getAllOffeneGeisternetze() {
    if(this.allOffeneGeisternetze.isEmpty()) {
      this.allOffeneGeisternetze = this.geisternetzDAO.getAllOffeneGeisternetze();
    }
    return this.allOffeneGeisternetze;
  }


  public List<Geisternetz> getEingeloggtePersonZuweisungen() {
    if(this.eingeloggtePersonZuweisungen.isEmpty()) {
      this.eingeloggtePersonZuweisungen = this.personBergungGeisternetzDAO.getAllGeisternetzeEinzelPerson(session.getId());
    }
    return this.eingeloggtePersonZuweisungen;
  }


  public void savePerson(Person person) {
    this.personDAO.savePerson(person);
  }

  public Geisternetz saveGeisternetz(Geisternetz geisternetz) {
    return this.geisternetzDAO.saveGeisternetz(geisternetz);
  }

  public void deleteRelationPersonBergungGeisternetz(Integer geisternetzId) {
    PersonBergungGeisternetz relation = this.personBergungGeisternetzDAO.getSinglePersonBergungGeisternetzByGeisternetzId(geisternetzId);
    if(relation == null) {
      return;
    }
    Geisternetz geisternetz = this.personBergungGeisternetzDAO.deleteRelationPersonBergungGeisternetz(relation);

    this.allOffeneGeisternetze.add(geisternetz);
    this.eingeloggtePersonZuweisungen.remove(geisternetz);
  }

  public void createRelationPersonBergungGeisternetz(Integer geisternetzId, Integer personId) {

    Person person = this.personDAO.getSinglePerson(personId);
    Geisternetz geisternetz = this.geisternetzDAO.getSingleGeisternetz(geisternetzId);

    if(this.eingeloggtePersonZuweisungen.contains(geisternetz)) {
      return;
    }

    PersonBergungGeisternetz personBergungGeisternetz = new PersonBergungGeisternetz();
    personBergungGeisternetz.setGeisternetz(geisternetz);
    personBergungGeisternetz.setPerson(person);

    this.personBergungGeisternetzDAO.savePersonBergungGeisternetz((personBergungGeisternetz));
    this.allOffeneGeisternetze.remove(geisternetz);
    this.eingeloggtePersonZuweisungen.add(geisternetz);
  }


  public void updateGeisternetzEigenschaftenStatus(Geisternetz geisternetz) {
    Geisternetz aktualisiertesGeisternetz = this.geisternetzDAO.updateGeisternetzEigenschaftenStatus(geisternetz);

    if(this.eingeloggtePersonZuweisungen.contains(geisternetz)) {
      this.eingeloggtePersonZuweisungen.remove(geisternetz);
      this.eingeloggtePersonZuweisungen.add(aktualisiertesGeisternetz);
    }

    if(this.allOffeneGeisternetze.contains(geisternetz)) {
      this.allOffeneGeisternetze.remove(geisternetz);
      this.allOffeneGeisternetze.add(aktualisiertesGeisternetz);
    }
  }
}
