package ar.edu.utn.frba.dds.Modelos.Notificaciones;

import ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion.Notificacion;

public interface MedioDeNotificacionesPreferido {
  void notificar(Notificacion notificacion) throws Exception;
}
