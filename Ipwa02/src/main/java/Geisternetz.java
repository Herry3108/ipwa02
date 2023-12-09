import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "geisternetz")
public class Geisternetz {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @OneToOne
  @JoinColumn(name = "geisternetz_eigenschaften_id", referencedColumnName = "id")
  private GeisternetzEigenschaften geisternetzEigenschaften;

  @OneToOne
  private Gps gps;

  public Geisternetz() {}

  public Geisternetz(GeisternetzEigenschaften geisternetzEigenschaften, Gps gps) {
    this.geisternetzEigenschaften = geisternetzEigenschaften;
    this.gps = gps;
  }

  public Integer getId() {
    return this.id;
  }

  @Override
  public boolean equals(Object zuPruefendesObjekt) {

    if (this == zuPruefendesObjekt){
      return true;
    }

    if (zuPruefendesObjekt == null || getClass() != zuPruefendesObjekt.getClass()) {
      return false;
    }

    Geisternetz zuPruefendesObjektCast = (Geisternetz) zuPruefendesObjekt;
    return Objects.equals(id, zuPruefendesObjektCast.id);
  }

  public GeisternetzEigenschaften getGeisternetzEigenschaften() {
    return geisternetzEigenschaften;
  }

  public void setGeisternetzEigenschaften(GeisternetzEigenschaften geisternetzEigenschaften) {
    this.geisternetzEigenschaften = geisternetzEigenschaften;
  }

  public Gps getGps() {
    return this.gps;
  }

  public void setGps(Gps gps) {
    this.gps = gps;
  }
}
