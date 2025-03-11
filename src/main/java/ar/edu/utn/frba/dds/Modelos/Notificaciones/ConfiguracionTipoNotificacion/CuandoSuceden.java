package ar.edu.utn.frba.dds.Modelos.Notificaciones.ConfiguracionTipoNotificacion;

import ar.edu.utn.frba.dds.Modelos.Notificaciones.MedioDeNotificacionesPreferido;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion.Notificacion;
import ar.edu.utn.frba.dds.Persistencia.converters.MedioDeContactoPreferidoAttributeConverter;
import lombok.Getter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Cuando suceden")
public class CuandoSuceden extends ConfiguracionNotificaciones{

  public CuandoSuceden(MedioDeNotificacionesPreferido medio) {
    super(medio);
  }

  public CuandoSuceden() {
    super();
  }

  @Override
  public void notificarMiembro(Notificacion notificacion) throws Exception {
    this.getMedio().notificar(notificacion);
  }
}
