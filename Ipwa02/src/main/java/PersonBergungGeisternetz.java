import jakarta.persistence.*;

@Entity
@Table(name = "person_bergung_geisternetz")
public class PersonBergungGeisternetz {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @OneToOne
  private Person person;

  @OneToOne
  private Geisternetz geisternetz;

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public Geisternetz getGeisternetz() {
    return geisternetz;
  }

  public void setGeisternetz(Geisternetz geisternetz) {
    this.geisternetz = geisternetz;
  }

  public Integer getId() {
    return id;
  }
}
