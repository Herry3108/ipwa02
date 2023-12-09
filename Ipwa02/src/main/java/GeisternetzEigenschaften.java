import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "geisternetz_eigenschaften")
public class GeisternetzEigenschaften {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "groesse")
  private String groesse;

  @Column(name = "erfassungs_datum")
  @Temporal(TemporalType.DATE)
  private Date erfassungsDatum;

  @Column(name = "ist_zugewiesen")
  private boolean istZugewiesen;

  @Column(name = "ist_bergung_bevorstehend")
  private boolean istBergungBevorstehend;

  @Column(name = "ist_bergung_abgeschlossen")
  private boolean istBergungAbgeschlossen;

  @Column(name = "ist_verschollen")
  private boolean istVerschollen;

  @Column(name = "ist_gemeldet")
  private boolean istGemeldet;

  public Integer getId() {
    return id;
  }

  public String getGroesse() {
    return groesse;
  }

  public void setGroesse(String groesse) {
    this.groesse = groesse;
  }

  public Date getErfassungsDatum() {
    return erfassungsDatum;
  }

  public void setErfassungsDatum(Date erfassungsDatum) {
    this.erfassungsDatum = erfassungsDatum;
  }

  public boolean isIstZugewiesen() {
    return istZugewiesen;
  }

  public void setIstZugewiesen(boolean istZugewiesen) {
    this.istZugewiesen = istZugewiesen;
  }

  public boolean isIstBergungBevorstehend() {
    return istBergungBevorstehend;
  }

  public void setIstBergungBevorstehend(boolean istBergungBevorstehend) {
    this.istBergungBevorstehend = istBergungBevorstehend;
  }

  public boolean isIstBergungAbgeschlossen() {
    return istBergungAbgeschlossen;
  }

  public void setIstBergungAbgeschlossen(boolean istBergungAbgeschlossen) {
    this.istBergungAbgeschlossen = istBergungAbgeschlossen;
  }

  public boolean isIstVerschollen() {
    return istVerschollen;
  }

  public void setIstVerschollen(boolean istVerschollen) {
    this.istVerschollen = istVerschollen;
  }

  public boolean isIstGemeldet() {
    return istGemeldet;
  }

  public void setIstGemeldet(boolean istGemeldet) {
    this.istGemeldet = istGemeldet;
  }
}
