import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @OneToOne
  @JoinColumn(name = "person_daten_id", referencedColumnName = "id")
  private PersonDaten personDaten;

  @Column(name = "meldende_person")
  private boolean istMeldendePerson;

  @Column(name = "bergende_person")
  private boolean istBergendePerson;

  public PersonDaten getPersonDaten() {
    return personDaten;
  }

  public void setPersonDaten(PersonDaten personDaten) {
    this.personDaten = personDaten;
  }

  public boolean isIstMeldendePerson() {
    return istMeldendePerson;
  }

  public void setIstMeldendePerson(boolean istMeldendePerson) {
    this.istMeldendePerson = istMeldendePerson;
  }

  public boolean isIstBergendePerson() {
    return istBergendePerson;
  }

  public void setIstBergendePerson(boolean istBergendePerson) {
    this.istBergendePerson = istBergendePerson;
  }

  public Integer getId() {
    return id;
  }
}
