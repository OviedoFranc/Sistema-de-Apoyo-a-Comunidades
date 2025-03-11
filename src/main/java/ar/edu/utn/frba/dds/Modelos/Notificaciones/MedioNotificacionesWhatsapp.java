package ar.edu.utn.frba.dds.Modelos.Notificaciones;

import ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion.Notificacion;
import lombok.Getter;

public class MedioNotificacionesWhatsapp implements MedioDeNotificacionesPreferido {

  @Getter
  private String telefono;

  public MedioNotificacionesWhatsapp(String telefono) {
    this.telefono = telefono;
  }

  @Override
  public void notificar(Notificacion notificacion) throws Exception {
    //todo: ACA PODRIA HACER UN POP UP!
    //SenderService.getInstance().whatsApp(notificacion.getMensajeDeNotificacion(), this.getTelefono());
    //System.out.println(notificacion.getMensajeDeNotificacion());
  }
}
