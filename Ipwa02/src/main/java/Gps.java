import jakarta.persistence.*;

@Entity
@Table(name = "gps")
public class Gps {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "latitude", columnDefinition = "real")
  private String latitude;

  @Column(name = "longitude", columnDefinition = "real")
  private String longitude;

  @Column(name = "land")
  private String land;

  public Integer getId() {
    return id;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLand() {
    return land;
  }

  public void setLand(String land) {
    this.land = land;
  }
}
