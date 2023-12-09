public interface ILoginController {

  String username = null;

  String password = null;

  Boolean istMeldend = null;

  Boolean istBergend = null;

  LoginDAO loginDAO = null;

  PersonDAO personDAO = null;

  UserSession session = null;

  void loginAnonym();

  void login();

}
