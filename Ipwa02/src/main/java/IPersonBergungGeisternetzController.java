import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPersonBergungGeisternetzController {
  PersonController personController = null;

  GeisternetzController geisternetzController = null;

  Verwaltung verwaltung  = null;

  UserSession session = null;

  PersonBergungGeisternetz personBergungGeisternetz = null;

  PersonBergungGeisternetzDAO personBergungGeisternetzDAO = null;

  Integer anrede = null;

  String vorname = null;

  String nachname = null;

  String telefonnummer = null;

  String latitude = null;

  String longitude = null;

  Date erfassungsDatum = null;

  Double groesse = null;

  URL url = null;

  HttpURLConnection httpConnection = null;

  Boolean istNeueMeldungPersoenlich = null;

  Map<Integer, GeisternetzEigenschaftenStatusHelper> eingeloggtePersonZuweisungen = null;

  Map<Integer, GeisternetzEigenschaftenStatusHelper> selectedGeisternetze = new HashMap<>();

  void saveZuweisungPersonBergungGeisternetz();

  void saveEingeloggtePersonZuweisungen();

  String getNamenBergendePerson(Person person);

  String fetchCountryWithLatAndLong(String latitude, String longitude);

  List<Geisternetz> getEingeloggtePersonZuweisungen();

  List<Geisternetz> getAllOffeneGeisternetze();

  String getStatusMappingForTable(GeisternetzEigenschaften geisternetzEigenschaften);

  void saveNeueMeldung();

  void updateZuweisungStatus(Integer geisternetzId, String statusType, Boolean statusValue);

}
