public class GeisternetzEigenschaftenStatusHelper {
  private boolean istZugewiesen;
  private boolean istBergungBevorstehend;
  private boolean istBergungAbgeschlossen;
  private boolean istVerschollen;


  public GeisternetzEigenschaftenStatusHelper(
      boolean istBergungBevorstehend,
      boolean istBergungAbgeschlossen,
      boolean istVerschollen,
      boolean istZugewiesen
  ) {
    this.istBergungBevorstehend = istBergungBevorstehend;
    this.istBergungAbgeschlossen = istBergungAbgeschlossen;
    this.istVerschollen = istVerschollen;
    this.istZugewiesen = istZugewiesen;
  }


  public GeisternetzEigenschaftenStatusHelper(
      boolean istVerschollen,
      boolean istZugewiesen
  ) {
    this.istBergungBevorstehend = false;
    this.istBergungAbgeschlossen = false;
    this.istVerschollen = istVerschollen;
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

  public boolean isIstZugewiesen() {
    return istZugewiesen;
  }

  public void setIstZugewiesen(boolean istZugewiesen) {
    this.istZugewiesen = istZugewiesen;
  }
}
