public interface IPersonController {

  Person person = null;

  PersonDaten personDaten = null;

  PersonDAO personDAO = null;

  Person savePerson(
      Integer anrede,
      String vorname,
      String nachname,
      String telefonnummer
  );

  PersonDaten savePersonDaten(
      Integer anrede,
      String vorname,
      String nachname,
      String telefonnummer
  );

  Person getNeuePerson();

  PersonDaten getNeuePersonenDaten();
}
