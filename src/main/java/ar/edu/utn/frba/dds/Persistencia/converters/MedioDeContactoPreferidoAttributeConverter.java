package ar.edu.utn.frba.dds.Persistencia.converters;

import ar.edu.utn.frba.dds.Modelos.Notificaciones.MedioDeNotificacionesPreferido;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.MedioNotificacionesEmail;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.MedioNotificacionesWhatsapp;
import javax.persistence.AttributeConverter;
import java.util.regex.Pattern;

public class MedioDeContactoPreferidoAttributeConverter implements AttributeConverter<MedioDeNotificacionesPreferido, String> {
  @Override
  public String convertToDatabaseColumn(MedioDeNotificacionesPreferido medioDeNotificacionesPreferido) {
    String medioPreferido = medioDeNotificacionesPreferido.getClass().getName();
    String stringMedio = "";
    if (medioPreferido.equals("ar.edu.utn.frba.dds.Modelos.Notificaciones.MedioNotificacionesEmail")){
      stringMedio = "Email";
    } else if (medioPreferido.equals("ar.edu.utn.frba.dds.Modelos.Notificaciones.MedioNotificacionesWhatsapp")) {
      stringMedio = "Whatshapp";
    }

    return stringMedio;
  }

  @Override
  public MedioDeNotificacionesPreferido convertToEntityAttribute(String s) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
        "[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
        "A-Z]{2,7}$";
    Pattern p = Pattern.compile(emailRegex);
    MedioDeNotificacionesPreferido medio = null;
    if (p.matcher(s).matches()) {
      medio = new MedioNotificacionesEmail(s);
    } else {
      medio = new MedioNotificacionesWhatsapp(s);
    }

    return medio;
  }
}
