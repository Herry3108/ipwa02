import jakarta.ejb.Startup;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
@Startup
public class LogoutController implements Serializable, ILogoutController {

  @Inject
  private UserSession session;

  public LogoutController() {}

  @Override
  public void logout() {
    this.clearEingeloggterUserVariables();

    Router.navigiere("login", true);
  }

  private void clearEingeloggterUserVariables() {
    session.setAnrede(1);
    session.setId(0);
    session.setVorname("");
    session.setName("");
    session.setTelefonnummer("");
    session.setIstNutzungAnonym(false);
    session.setIstBergend(false);
    session.setIstMeldend(false);
  }
}
