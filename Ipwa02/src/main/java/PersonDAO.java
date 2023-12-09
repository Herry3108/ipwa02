import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;

@Stateless
public class PersonDAO {

  private final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("personBergungGeisternetzPersistence");

  public Person savePerson(Person person) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    entityTransaction.begin();
    entityManager.persist(person);
    entityTransaction.commit();
    entityManager.close();

    return person;
  }

  public PersonDaten savePersonDaten(PersonDaten personDaten) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    entityTransaction.begin();
    entityManager.persist(personDaten);
    entityTransaction.commit();
    entityManager.close();

    return personDaten;
  }

  public Person getSinglePerson(Integer personId) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    Query query = entityManager.createQuery("select person " +
        "from Person person where " +
        "person.id = :personId");
    query.setParameter("personId", personId);
    List<Person> person = query.getResultList();
    entityManager.close();
    if(person.isEmpty()) {
      return null;
    }

    return person.get(0);
  }

  public Person updatePersonRechte(Person person) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    EntityTransaction entityTransaction = entityManager.getTransaction();
    entityTransaction.begin();
    entityManager.merge(person);
    entityTransaction.commit();
    entityManager.close();

    return person;
  }
}
