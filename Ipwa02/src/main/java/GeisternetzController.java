import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Date;

@Named
@SessionScoped
public class GeisternetzController implements Serializable, IGeisternetzController {

  private GeisternetzEigenschaften geisternetzEigenschaften = null;

  private Geisternetz geisternetz = null;

  private Gps gps = null;

  private final GeisternetzDAO geisternetzDAO = new GeisternetzDAO();

  @Inject
  private Verwaltung verwaltung;

  public GeisternetzController() {
  }

  public GeisternetzEigenschaften getGeisternetzEigenschaften() {
    return this.geisternetzEigenschaften;
  }

  @Override
  public Geisternetz saveGeisternetz(
      String latitude,
      String longitude,
      Date erfassungsDatum,
      String groesse,
      Boolean istMeldungPersoenlich,
      String land
  ) {
    Gps gps = this.saveGps(
        latitude,
        longitude,
        land
    );

    GeisternetzEigenschaften geisternetzEigenschaften = this.saveGeisternetzEigenschaften(
        groesse,
        erfassungsDatum,
        istMeldungPersoenlich
    );

    Geisternetz geisternetz = this.getNeuesGeisternetz();
    geisternetz.setGeisternetzEigenschaften(geisternetzEigenschaften);
    geisternetz.setGps(gps);

    return this.verwaltung.saveGeisternetz(geisternetz);
  }

  @Override
  public Gps saveGps(
      String latitude,
      String longitude,
      String land
  ) {
    Gps gps = this.getGps();
    gps.setLatitude(latitude);
    gps.setLongitude(longitude);
    gps.setLand(land);

    return geisternetzDAO.saveGps(gps);
  }

  @Override
  public GeisternetzEigenschaften saveGeisternetzEigenschaften(
      String groesse,
      Date erfassungsDatum,
      Boolean istMeldungPersoenlich
  ) {
    GeisternetzEigenschaften geisternetzEigenschaften = this.getNeueGeisternetzEigenschaften();
    geisternetzEigenschaften.setGroesse(groesse);
    geisternetzEigenschaften.setIstZugewiesen(istMeldungPersoenlich);
    geisternetzEigenschaften.setErfassungsDatum(erfassungsDatum);

    return geisternetzDAO.saveGeisternetzEigenschaften(geisternetzEigenschaften);
  }

  @Override
  public GeisternetzEigenschaften getNeueGeisternetzEigenschaften() {
    if (this.geisternetzEigenschaften == null) {
      this.geisternetzEigenschaften = new GeisternetzEigenschaften();
      return this.getGeisternetzEigenschaften();
    }
    return this.geisternetzEigenschaften;
  }

  public Gps getGps() {
    if (this.gps == null) {
      return this.getNeuesGps();
    }

    return this.gps;
  }

  @Override
  public Gps getNeuesGps() {
    this.gps = new Gps();
    return this.gps;
  }

  public Geisternetz getGeisternetz() {
    return this.geisternetz;
  }

  @Override
  public Geisternetz getNeuesGeisternetz() {
    if (this.geisternetz == null) {
      this.geisternetz = new Geisternetz();
      return this.getGeisternetz();
    }
    return this.geisternetz;
  }

  @Override
  public Geisternetz getSingleGeisternetz(Integer id) {
    return this.geisternetzDAO.getSingleGeisternetz(id);
  }
}
