import jakarta.persistence.*;

@Entity
@Table(name = "person_daten")
public class PersonDaten {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "nachname")
  private String name;

  @Column(name = "vorname")
  private String vorname;

  @Column(name = "telefonnummer")
  private String telefonnummer;

  @Column(name = "anrede")
  private Integer anrede;

  public Integer getAnrede() {
    return anrede;
  }

  public void setAnrede(Integer anrede) {
    this.anrede = anrede;
  }

  public Integer getId() {
    return id;
  }

  public String getNachname() {
    return name;
  }

  public void setNachname(String name) {
    this.name = name;
  }

  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  public String getTelefonnummer() {
    return telefonnummer;
  }

  public void setTelefonnummer(String telefonnummer) {
    this.telefonnummer = telefonnummer;
  }
}
