import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.*;
import java.net.URL;

@Named
@RequestScoped
public class PersonBergungGeisternetzController implements IPersonBergungGeisternetzController {

  @Inject
  private PersonController personController;

  @Inject
  private GeisternetzController geisternetzController;

  @Inject
  private Verwaltung verwaltung;

  @Inject
  private UserSession session;

  private PersonBergungGeisternetz personBergungGeisternetz = null;

  private final PersonBergungGeisternetzDAO personBergungGeisternetzDAO = new PersonBergungGeisternetzDAO();

  private Integer anrede;

  private String vorname;

  private String nachname;

  private String telefonnummer;

  private String latitude;

  private String longitude;

  private Date erfassungsDatum;

  private String groesse;

  private URL url = null;

  private HttpURLConnection httpConnection = null;

  private boolean istNeueMeldungPersoenlich = true;

  private final Map<Integer, GeisternetzEigenschaftenStatusHelper> eingeloggtePersonZuweisungen = new HashMap<>();

  private Map<Integer, GeisternetzEigenschaftenStatusHelper> selectedGeisternetze = new HashMap<>();

  public PersonBergungGeisternetzController() {}

  @Override
  public void saveZuweisungPersonBergungGeisternetz() {
    if(session.getId() == 0) {
      for (Map.Entry<Integer, GeisternetzEigenschaftenStatusHelper> entry : selectedGeisternetze.entrySet()) {
        Integer geisternetzId = entry.getKey();

        GeisternetzEigenschaftenStatusHelper status = entry.getValue();
        Geisternetz geisternetz = this.geisternetzController.getSingleGeisternetz(geisternetzId);

        geisternetz.getGeisternetzEigenschaften().setIstVerschollen(status.isIstVerschollen());
        geisternetz.getGeisternetzEigenschaften().setIstZugewiesen(status.isIstZugewiesen());

        verwaltung.updateGeisternetzEigenschaftenStatus(geisternetz);
      }

      Router.navigiere("verwaltung", true);
      return;
    }

    for (Map.Entry<Integer, GeisternetzEigenschaftenStatusHelper> entry : selectedGeisternetze.entrySet()) {
      Integer geisternetzId = entry.getKey();

      GeisternetzEigenschaftenStatusHelper status = entry.getValue();
      Geisternetz geisternetz = this.geisternetzController.getSingleGeisternetz(geisternetzId);

      geisternetz.getGeisternetzEigenschaften().setIstVerschollen(status.isIstVerschollen());
      geisternetz.getGeisternetzEigenschaften().setIstZugewiesen(status.isIstZugewiesen());

      verwaltung.updateGeisternetzEigenschaftenStatus(geisternetz);

      if (geisternetz.getGeisternetzEigenschaften().isIstZugewiesen()) {
        verwaltung.createRelationPersonBergungGeisternetz(geisternetzId, session.getId());
      } else {
        verwaltung.deleteRelationPersonBergungGeisternetz(geisternetzId);
      }
    }

    Router.navigiere("verwaltung", true);
  }

  @Override
  public void saveEingeloggtePersonZuweisungen() {

    for (Map.Entry<Integer, GeisternetzEigenschaftenStatusHelper> entry : eingeloggtePersonZuweisungen.entrySet()) {
      Integer geisternetzId = entry.getKey();
      GeisternetzEigenschaftenStatusHelper status = entry.getValue();
      Geisternetz geisternetz = this.geisternetzController.getSingleGeisternetz(geisternetzId);

      geisternetz.getGeisternetzEigenschaften().setIstBergungBevorstehend(status.isIstBergungBevorstehend());
      geisternetz.getGeisternetzEigenschaften().setIstBergungAbgeschlossen(status.isIstBergungAbgeschlossen());
      geisternetz.getGeisternetzEigenschaften().setIstVerschollen(status.isIstVerschollen());
      geisternetz.getGeisternetzEigenschaften().setIstZugewiesen(status.isIstZugewiesen());
      verwaltung.updateGeisternetzEigenschaftenStatus(geisternetz);

      if (status.isIstZugewiesen()) {
        verwaltung.createRelationPersonBergungGeisternetz(geisternetzId, session.getId());
      } else {
        verwaltung.deleteRelationPersonBergungGeisternetz(geisternetzId);
      }

    }

    Router.navigiere("verwaltung", true);
  }

  @Override
  public String getNamenBergendePerson(Person person) {
    return person.getPersonDaten().getVorname() + " " + person.getPersonDaten().getNachname();
  }

  @Override
  public String fetchCountryWithLatAndLong(
      String latitude,
      String longitude
  ) {
    try{

      String urlToRequest = "http://api.geonames.org/countryCode?lat="
          + latitude
          + "&lng="
          + longitude
          + "&username=TODO"; //Aus Sicherheitsgruenden kein Username oeffentlich hinterlegt

      this.setUrl(new URL(urlToRequest));
      this.setHttpConnection((HttpURLConnection) this.getUrl().openConnection());
      this.getHttpConnection().setRequestMethod("GET");

      BufferedReader reader = new BufferedReader(
          new InputStreamReader(this.getHttpConnection().getInputStream())
      );

      StringBuilder response = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        response.append(line);
      }
      reader.close();

      String countryCode = response.toString().trim();

      return countryCode;
    }
    catch (Exception exceptione) {
      return "Land nicht auffindbar";
    }
  }

  @Override
  public List<Geisternetz> getEingeloggtePersonZuweisungen() {
    List<Geisternetz> geisternetzList = this.verwaltung.getEingeloggtePersonZuweisungen();

    for (Geisternetz geisternetz: geisternetzList) {
      GeisternetzEigenschaftenStatusHelper geisternetzEigenschaftenStatusHelper = new GeisternetzEigenschaftenStatusHelper(
          geisternetz.getGeisternetzEigenschaften().isIstBergungBevorstehend(),
          geisternetz.getGeisternetzEigenschaften().isIstBergungAbgeschlossen(),
          geisternetz.getGeisternetzEigenschaften().isIstVerschollen(),
          geisternetz.getGeisternetzEigenschaften().isIstZugewiesen()
      );
      this.eingeloggtePersonZuweisungen.put(geisternetz.getId(), geisternetzEigenschaftenStatusHelper);
    }

    return geisternetzList;
  }

  @Override
  public List<Geisternetz> getAllOffeneGeisternetze() {
    List<Geisternetz> offeneGeisternetze = this.verwaltung.getAllOffeneGeisternetze();

    for (Geisternetz geisternetz : offeneGeisternetze) {
      GeisternetzEigenschaftenStatusHelper geisternetzEigenschaftenStatusHelper = new GeisternetzEigenschaftenStatusHelper(
          geisternetz.getGeisternetzEigenschaften().isIstVerschollen(),
          geisternetz.getGeisternetzEigenschaften().isIstZugewiesen()
      );
      selectedGeisternetze.put(geisternetz.getId(), geisternetzEigenschaftenStatusHelper);
    }

    return offeneGeisternetze;
  }

  @Override
  public String getStatusMappingForTable(GeisternetzEigenschaften geisternetzEigenschaften) {

    if(geisternetzEigenschaften.isIstVerschollen()) {
      return "Verschollen";
    }

    if(geisternetzEigenschaften.isIstBergungAbgeschlossen()) {
      return "Bergung abgeschlossen";
    }

    if(geisternetzEigenschaften.isIstBergungBevorstehend()) {
      return "Bergung bevorstehend";
    }

    return "Gemeldet";
  }

  @Override
  public void saveNeueMeldung() {

    if(this.isIstNeueMeldungPersoenlich()) {
      this.saveNeueMeldungMitPerson();
        return;
    }

    this.saveNeueMeldungAnonym();
  }

  private void saveNeueMeldungAnonym() {
    this.saveGeisternetz(
        this.getLatitude(),
        this.getLongitude(),
        this.getErfassungsDatum(),
        this.getGroesse(),
        false,
        this.fetchCountryWithLatAndLong(this.getLatitude(), this.getLongitude())
    );

    Router.navigiere("verwaltung", true);
  }

  private void saveNeueMeldungMitPerson() {
    Person person = this.savePerson(
        this.getAnrede(),
        this.getVorname(),
        this.getNachname(),
        this.getTelefonnummer()
    );

    Geisternetz geisternetz = this.saveGeisternetz(
        this.getLatitude(),
        this.getLongitude(),
        this.getErfassungsDatum(),
        this.getGroesse(),
        true,
        this.fetchCountryWithLatAndLong(this.getLatitude(), this.getLongitude())
    );

    PersonBergungGeisternetz personBergungGeisternetz = this.getPersonBergungGeisternetz();
    personBergungGeisternetz.setPerson(person);
    personBergungGeisternetz.setGeisternetz(geisternetz);
    this.personBergungGeisternetzDAO.savePersonBergungGeisternetz(personBergungGeisternetz);

    Router.navigiere("verwaltung", true);
  }

  private Person savePerson(
      Integer anrede,
      String vorname,
      String nachname,
      String telefonnummer
  ) {
    return personController.savePerson(
        anrede,
        vorname,
        nachname,
        telefonnummer
    );
  }

  private Geisternetz saveGeisternetz(
      String latitude,
      String longitude,
      Date erfassungsDatum,
      String groesse,
      Boolean istMeldungPersoenlich,
      String land
  ) {
    return geisternetzController.saveGeisternetz(
        latitude,
        longitude,
        erfassungsDatum,
        groesse,
        istMeldungPersoenlich,
        land
    );
  }

  private PersonBergungGeisternetz getPersonBergungGeisternetz() {
    if(this.personBergungGeisternetz == null) {
      return this.getNeuePersonBergungGeisternetz();
    }
    return this.personBergungGeisternetz;
  }

  private PersonBergungGeisternetz getNeuePersonBergungGeisternetz() {
    this.personBergungGeisternetz = new PersonBergungGeisternetz();
    return this.personBergungGeisternetz;
  }


  public Map<Integer, GeisternetzEigenschaftenStatusHelper> getMeineZuweisungenStatus() {
    return eingeloggtePersonZuweisungen;
  }

  public void updateOffeneMeldungStatus(Integer geisternetzId, String statusType, Boolean statusValue) {

    GeisternetzEigenschaftenStatusHelper status = selectedGeisternetze.get(geisternetzId);

    switch (statusType) {
      case "istZugewiesen":
        status.setIstZugewiesen(statusValue);
        break;
      case "istVerschollen":
        status.setIstVerschollen(statusValue);
        break;
    }

  }

  public void updateZuweisungStatus(Integer geisternetzId, String statusType, Boolean statusValue) {
    GeisternetzEigenschaftenStatusHelper status = eingeloggtePersonZuweisungen.get(geisternetzId);
    if (status == null) {
      status = new GeisternetzEigenschaftenStatusHelper(
          false,
          false,
          false,
          false
      );
      eingeloggtePersonZuweisungen.put(geisternetzId, status);
    }

    switch (statusType) {
      case "istBergungBevorstehend":
        status.setIstBergungBevorstehend(statusValue);
        break;
      case "istBergungAbgeschlossen":
        status.setIstBergungAbgeschlossen(statusValue);
        break;
      case "istVerschollen":
        status.setIstVerschollen(statusValue);
        break;
    }
  }

  public Integer getAnrede() {
    return anrede;
  }

  public void setAnrede(Integer anrede) {
    this.anrede = anrede;
  }

  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  public String getNachname() {
    return nachname;
  }

  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  public String getTelefonnummer() {
    return telefonnummer;
  }

  public void setTelefonnummer(String telefonnummer) {
    this.telefonnummer = telefonnummer;
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

  public Date getErfassungsDatum() {
    return erfassungsDatum;
  }

  public void setErfassungsDatum(Date erfassungsDatum) {
    this.erfassungsDatum = erfassungsDatum;
  }

  public String getGroesse() {
    return groesse;
  }

  public void setGroesse(String groesse) {
    this.groesse = groesse;
  }

  public URL getUrl() {
    return url;
  }

  public void setUrl(URL url) {
    this.url = url;
  }

  public HttpURLConnection getHttpConnection() {
    return httpConnection;
  }

  public void setHttpConnection(HttpURLConnection httpConnection) {
    this.httpConnection = httpConnection;
  }

  public Map<Integer, GeisternetzEigenschaftenStatusHelper> getSelectedGeisternetze() {
    return selectedGeisternetze;
  }

  public void setSelectedGeisternetze(Map<Integer, GeisternetzEigenschaftenStatusHelper> selectedGeisternetze) {
    this.selectedGeisternetze = selectedGeisternetze;
  }

  public boolean isIstNeueMeldungPersoenlich() {
    return istNeueMeldungPersoenlich;
  }

  public void setIstNeueMeldungPersoenlich(boolean istNeueMeldungPersoenlich) {
    this.istNeueMeldungPersoenlich = istNeueMeldungPersoenlich;
  }
}
