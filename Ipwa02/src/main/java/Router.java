import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Map;

@Named
@SessionScoped
public class Router implements Serializable {

  private final String verwaltungSeite = "Verwaltung";

  private final String startSeiteSeite = "Startseite";

  private Map<String, String> routes = Map.of(
      verwaltungSeite, "verwaltung?faces-redirect=true",
      startSeiteSeite, "index?faces-redirect=true"
  );

  public String navigiereZuSeite(String page) {
    if (page.equals(this.verwaltungSeite)) {
      return routes.get(this.verwaltungSeite);
    }
    return routes.get(this.startSeiteSeite);
  }

  public static void navigiere(String newPage, boolean redirect) {
    if (redirect) {
      newPage = newPage + "?faces-redirect=true";
    }
    FacesContext context = FacesContext.getCurrentInstance();
    context.getApplication().getNavigationHandler().handleNavigation(context, null, newPage);
  }
}
