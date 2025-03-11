package ar.edu.utn.frba.dds.Modelos.Notificaciones;

import ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion.Notificacion;
import lombok.Getter;

public class MedioNotificacionesEmail implements MedioDeNotificacionesPreferido{

  @Getter
  private String email;

  public MedioNotificacionesEmail(String email) {
    this.email = email;
  }

  @Override
  public void notificar(Notificacion notificacion) throws Exception {
    //SenderService.getInstance().email(this.getEmail(), notificacion.getEncabezado() , notificacion.getMensajeDeNotificacion());
    //System.out.println(notificacion.getMensajeDeNotificacion());
  }
}
