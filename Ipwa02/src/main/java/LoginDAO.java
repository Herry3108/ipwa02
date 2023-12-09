import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

@Stateless
public class LoginDAO {
  private final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("personBergungGeisternetzPersistence");

  public Integer validateUserLogin(String username, String password) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    Query query = entityManager.createQuery("select login from Login login " +
        "where login.username = :username and login.password = :password");
    query.setParameter("username", username);
    query.setParameter("password", password);
    List<Login> loginResult = query.getResultList();
    entityManager.close();

    if(loginResult.isEmpty()) {
      return null;
    }

    return loginResult.get(0).getPersonId();
  }
}
