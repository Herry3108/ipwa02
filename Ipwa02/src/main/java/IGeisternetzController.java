import java.util.Date;

public interface IGeisternetzController {

  GeisternetzEigenschaften geisternetzEigenschaften = null;

  Geisternetz geisternetz = null;

  Gps gps = null;

  GeisternetzDAO geisternetzDAO = null;

  Verwaltung verwaltung = null;

  Geisternetz saveGeisternetz(
      String latitude,
      String longitude,
      Date erfassungsDatum,
      String groesse,
      Boolean istMeldungPersoenlich,
      String land
  );

  Gps saveGps(
      String latitude,
      String longitude,
      String land
  );

  GeisternetzEigenschaften saveGeisternetzEigenschaften(
      String groesse,
      Date erfassungsDatum,
      Boolean istMeldungPersoenlich
  );

  GeisternetzEigenschaften getNeueGeisternetzEigenschaften();

  Gps getNeuesGps();

  Geisternetz getNeuesGeisternetz();

  Geisternetz getSingleGeisternetz(Integer id);




}
