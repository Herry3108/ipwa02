import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import java.util.List;

@Stateless
public class GeisternetzDAO {
  private final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("personBergungGeisternetzPersistence");

  public List<Geisternetz> getAllOffeneGeisternetze() {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    Query query = entityManager.createQuery("select geisternetz from Geisternetz geisternetz "
      + "where geisternetz.geisternetzEigenschaften.istZugewiesen = false"
    );
    List<Geisternetz> alleGeisternetze = query.getResultList();
    entityManager.close();
    return alleGeisternetze;
  }

  public Geisternetz saveGeisternetz(Geisternetz geisternetz) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    entityTransaction.begin();
    entityManager.persist(geisternetz);
    entityTransaction.commit();
    entityManager.close();

    return geisternetz;
  }

  public GeisternetzEigenschaften saveGeisternetzEigenschaften(GeisternetzEigenschaften geisternetzEigenschaften) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    entityTransaction.begin();
    entityManager.persist(geisternetzEigenschaften);
    entityTransaction.commit();
    entityManager.close();

    return geisternetzEigenschaften;
  }

  public Gps saveGps(Gps gps) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    entityTransaction.begin();
    entityManager.persist(gps);
    entityTransaction.commit();
    entityManager.close();

    return gps;
  }

  public Geisternetz getSingleGeisternetz(Integer geisternetzId) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    Query query = entityManager.createQuery("select geisternetz " +
        "from Geisternetz geisternetz where " +
        "geisternetz.id = :geisternetzId");
    query.setParameter("geisternetzId", geisternetzId);
    List<Geisternetz> geisternetz = query.getResultList();
    entityManager.close();
    if(geisternetz.isEmpty()) {
      return null;
    }

    return geisternetz.get(0);
  }

  public Geisternetz updateGeisternetzEigenschaftenStatus(Geisternetz geisternetz) {

      GeisternetzEigenschaften eigenschaften = geisternetz.getGeisternetzEigenschaften();

      EntityManager entityManager = entityManagerFactory.createEntityManager();
      GeisternetzEigenschaften existingEigenschaften = entityManager.find(GeisternetzEigenschaften.class, eigenschaften.getId());

      existingEigenschaften.setIstBergungBevorstehend(eigenschaften.isIstBergungBevorstehend());
      existingEigenschaften.setIstBergungAbgeschlossen(eigenschaften.isIstBergungAbgeschlossen());
      existingEigenschaften.setIstVerschollen(eigenschaften.isIstVerschollen());

      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();
      entityManager.merge(existingEigenschaften);
      entityTransaction.commit();
      entityManager.close();

      return this.getSingleGeisternetz(geisternetz.getId());
  }
}
