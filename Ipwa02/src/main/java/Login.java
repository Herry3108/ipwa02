import jakarta.persistence.*;

@Entity
@Table(name = "login")
public class Login {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "person_id")
  private Integer personId;

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Integer getId() {
    return id;
  }

  public Integer getPersonId() {
    return this.personId;
  }
}
