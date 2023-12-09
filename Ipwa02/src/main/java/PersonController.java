import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class PersonController implements Serializable, IPersonController {

  private Person person = null;

  private PersonDaten personDaten = null;

  private PersonDAO personDAO = new PersonDAO();

  public PersonController() {}

  @Override
  public Person savePerson(
        Integer anrede,
        String vorname,
        String nachname,
        String telefonnummer
  ) {
    PersonDaten personDaten = this.savePersonDaten(
        anrede,
        vorname,
        nachname,
        telefonnummer
    );

    Person person = this.getNeuePerson();
    person.setPersonDaten(personDaten);
    person.setIstMeldendePerson(true);
    person.setIstBergendePerson(false);
    personDAO.savePerson(person);

    return person;
  }

  @Override
  public PersonDaten savePersonDaten(
    Integer anrede,
    String vorname,
    String nachname,
    String telefonnummer
  ) {
    PersonDaten personDaten = this.getNeuePersonenDaten();
    personDaten.setAnrede(anrede);
    personDaten.setNachname(nachname);
    personDaten.setVorname(vorname);
    personDaten.setTelefonnummer(telefonnummer);

    personDAO.savePersonDaten(personDaten);

    return personDaten;
  }

  public Person getPerson() {
    if(this.person == null) {
      return this.getNeuePerson();
    }
    return this.person;
  }


  public Person getSinglePersonById(Integer personId) {
    return this.personDAO.getSinglePerson(personId);
  }

  @Override
  public Person getNeuePerson() {
    this.person = new Person();
    return this.person;
  }

  public PersonDaten getPersonDaten() {
    if(this.personDaten == null) {
      return this.getNeuePersonenDaten();
    }
    return this.personDaten;
  }

  @Override
  public PersonDaten getNeuePersonenDaten() {
    this.personDaten = new PersonDaten();
    return this.personDaten;
  }
}
