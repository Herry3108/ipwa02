import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import java.util.List;

@Stateless
public class PersonBergungGeisternetzDAO {

  private final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("personBergungGeisternetzPersistence");

  public List<Geisternetz> getAllGeisternetzeEinzelPerson(Integer id) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    Query query = entityManager.createQuery("select personBergungGeisternetz.geisternetz from PersonBergungGeisternetz personBergungGeisternetz " +
        "where personBergungGeisternetz.person.id = :person_id and personBergungGeisternetz.geisternetz.geisternetzEigenschaften.istVerschollen = false");
    query.setParameter("person_id", id);
    List<Geisternetz> allePersonBergungGeisternetz = query.getResultList();
    entityManager.close();
    return allePersonBergungGeisternetz;
  }

  public List<PersonBergungGeisternetz> getLetzteFuenfGeisternetzBergungen() {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    Query query = entityManager.createQuery("select personBergungGeisternetz from PersonBergungGeisternetz personBergungGeisternetz " +
        "where personBergungGeisternetz.geisternetz.geisternetzEigenschaften.istBergungAbgeschlossen = true ");
    query.setMaxResults(5);
    List<PersonBergungGeisternetz> alleGeisternetze = query.getResultList();
    entityManager.close();
    return alleGeisternetze;
  }

  public PersonBergungGeisternetz getSinglePersonBergungGeisternetzByGeisternetzId(Integer geisternetzId) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    Query query = entityManager.createQuery("select personBergungGeisternetz " +
        "from PersonBergungGeisternetz personBergungGeisternetz where " +
        "personBergungGeisternetz.geisternetz.id = :geisternetzId");
    query.setParameter("geisternetzId", geisternetzId);
    List<PersonBergungGeisternetz> personBergungGeisternetzVerknuepfung = query.getResultList();
    entityManager.close();

    if(personBergungGeisternetzVerknuepfung.isEmpty()) {
      return null;
    }

    return personBergungGeisternetzVerknuepfung.get(0);
  }

  public Geisternetz deleteRelationPersonBergungGeisternetz(PersonBergungGeisternetz personBergungGeisternetz) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    PersonBergungGeisternetz personBergungGeisternetzFinding = entityManager.find(PersonBergungGeisternetz.class, personBergungGeisternetz.getId());

    EntityTransaction entityTransaction = entityManager.getTransaction();
    entityTransaction.begin();
    Geisternetz geisternetz = personBergungGeisternetzFinding.getGeisternetz();
    geisternetz.getGeisternetzEigenschaften().setIstZugewiesen(false);
    geisternetz.getGeisternetzEigenschaften().setIstBergungAbgeschlossen(false);
    geisternetz.getGeisternetzEigenschaften().setIstVerschollen(false);
    geisternetz.getGeisternetzEigenschaften().setIstBergungBevorstehend(false);
    entityManager.merge(geisternetz.getGeisternetzEigenschaften());
    entityTransaction.commit();

    entityTransaction.begin();
    entityManager.remove(personBergungGeisternetzFinding);
    entityTransaction.commit();
    entityManager.close();

    return geisternetz;
  }

  public PersonBergungGeisternetz savePersonBergungGeisternetz(PersonBergungGeisternetz personBergungGeisternetz) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();
    Query query = entityManager.createQuery("select personBergungGeisternetz " +
        "from PersonBergungGeisternetz personBergungGeisternetz where " +
        "personBergungGeisternetz.geisternetz.id = :geisternetzId");
    query.setParameter("geisternetzId", personBergungGeisternetz.getGeisternetz().getId());
    List<PersonBergungGeisternetz> personBergungGeisternetzResult = query.getResultList();

    if(!personBergungGeisternetzResult.isEmpty()) {
      return personBergungGeisternetzResult.get(0);
    }

    entityTransaction.begin();
    Geisternetz geisternetz = personBergungGeisternetz.getGeisternetz();
    geisternetz.getGeisternetzEigenschaften().setIstZugewiesen(true);
    entityManager.merge(geisternetz.getGeisternetzEigenschaften());
    entityTransaction.commit();

    entityTransaction.begin();
    entityManager.persist(personBergungGeisternetz);
    entityTransaction.commit();
    entityManager.close();

    return personBergungGeisternetz;
  }

  public List<PersonBergungGeisternetz> getLetzteFuenfOffeneMeldungen() {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    Query query = entityManager.createQuery("select personBergungGeisternetz from PersonBergungGeisternetz personBergungGeisternetz "
        + "where personBergungGeisternetz.geisternetz.geisternetzEigenschaften.istVerschollen = false " +
        "or personBergungGeisternetz.geisternetz.geisternetzEigenschaften.istBergungAbgeschlossen = false"
    );
    query.setMaxResults(5);
    List<PersonBergungGeisternetz> alleGeisternetze = query.getResultList();
    entityManager.close();
    return alleGeisternetze;
  }
}

