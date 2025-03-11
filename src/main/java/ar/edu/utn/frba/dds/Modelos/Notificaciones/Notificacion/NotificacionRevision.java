package ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion;

import ar.edu.utn.frba.dds.Modelos.Incidente;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Persona;
import javax.persistence.Entity;

@Entity
public class NotificacionRevision extends Notificacion {

  public NotificacionRevision(Incidente incidente,  Persona usuarioANotificar) {
    super(incidente, usuarioANotificar);
  }

  public NotificacionRevision() {
  }

  @Override
  public String getMensajeDeNotificacion() {
    String msg = "Hola " + usuarioANotificar.getNombre() + ", hay un incidente activo en el Servicio " + incidente.getServicio().getNombre() + " de " + incidente.getServicio().getEstablecimiento().getNombre() + " - " + incidente.getServicio().getEstablecimiento().getEntidad().getNombre() +
        ". Por favor, si tiene tiempo de revisar y revisar su estado actual se lo agradeceriamos. Muchas gracias y disculpe las molestias.";
    return msg;
  }

  @Override
  public String getEncabezado() {
    return "INCIDENTE ACTIVO CERCA SUYO";
  }
}
