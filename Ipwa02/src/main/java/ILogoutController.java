import jakarta.inject.Inject;

public interface ILogoutController {

  UserSession session = null;

  void logout();
}
