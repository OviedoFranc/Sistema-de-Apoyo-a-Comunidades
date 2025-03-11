package ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion;

import ar.edu.utn.frba.dds.Modelos.Incidente;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Persona;
import javax.persistence.Entity;

@Entity
public class NotificacionIncidenteResuelto extends Notificacion {
  public NotificacionIncidenteResuelto(Incidente incidente,  Persona usuarioANotificar) {
    super(incidente, usuarioANotificar);
  }

  public NotificacionIncidenteResuelto() {
  }

  @Override
  public String getMensajeDeNotificacion() {
    String msg = "Hola " + usuarioANotificar.getNombre() + ", le queremos informar que el incidente reportado anteriormente por " +
        incidente.getInformante().getUsername() + " en el Servicio " + incidente.getServicio().getNombre() + " de " + incidente.getServicio().getEstablecimiento().getNombre() + " - " + incidente.getServicio().getEstablecimiento().getEntidad().getNombre() + " fue marcado como resuelto";
    return msg;
  }

  @Override
  public String getEncabezado(){
    return "INCIDENTE RESUELTO";
  }
}
