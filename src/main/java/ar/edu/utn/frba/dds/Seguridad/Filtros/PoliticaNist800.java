package ar.edu.utn.frba.dds.Seguridad.Filtros;

import java.util.stream.Stream;

public class PoliticaNist800 implements FiltroInterface {

  private static final int MIN_LENGTH = 8;
  private static final int MAX_LENGTH = 64;

  private Boolean passwordLongitud(String passsword) {
    return (passsword.length() <= MIN_LENGTH || passsword.length() >= MAX_LENGTH) ? false : true;
  }

  private Boolean passwordCompleja(String passsword) {
    return true;
  }

  private Boolean passwordRotacion(String passsword) {
    return true;
  }

  @Override
  public Boolean validar(String password) {
    return Stream.of(
        passwordLongitud(password),
        passwordCompleja(password),
        passwordRotacion(password)
    ).allMatch(Boolean::booleanValue);
  }
}
