package ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion;

import ar.edu.utn.frba.dds.Modelos.Incidente;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Persona;
import javax.persistence.Entity;

@Entity
public class NotificacionNuevoIncidente extends Notificacion {

  public NotificacionNuevoIncidente(Incidente incidente,  Persona usuarioANotificar) {
    super(incidente, usuarioANotificar);
  }

  public NotificacionNuevoIncidente() {
  }

  @Override
  public String getMensajeDeNotificacion() {
    String msg = "Hola " + usuarioANotificar.getNombre() + ", le queremos informar que hay un nuevo incidente informado por " +
        incidente.getInformante().getUsername() + " en el Servicio " + incidente.getServicio().getNombre() + " de " + incidente.getServicio().getEstablecimiento().getNombre() + " - " + incidente.getServicio().getEstablecimiento().getEntidad().getNombre() + ": " + incidente.getDescripcion();
    return msg;
  }

  @Override
  public String getEncabezado(){
    return "NUEVO INCIDENTE";
  }

}
