package ar.edu.utn.frba.dds.Modelos.Notificaciones.ConfiguracionTipoNotificacion;

import ar.edu.utn.frba.dds.Modelos.Notificaciones.MedioDeNotificacionesPreferido;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion.Notificacion;

import ar.edu.utn.frba.dds.Persistencia.EntidadPersistente;
import ar.edu.utn.frba.dds.Persistencia.converters.MedioDeContactoPreferidoAttributeConverter;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "configuracion_notificaciones")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoNotificaciones", discriminatorType = DiscriminatorType.STRING)
public abstract class ConfiguracionNotificaciones extends EntidadPersistente {

  @Getter
  @Convert(converter = MedioDeContactoPreferidoAttributeConverter.class)
  @Column(name = "contacto")
  private MedioDeNotificacionesPreferido medio;

  protected ConfiguracionNotificaciones() {
    this.medio = null; // Para el inicializador del JPA
  }

  public void notificarMiembro(Notificacion notificacion) throws Exception {
  }

  protected ConfiguracionNotificaciones(MedioDeNotificacionesPreferido medio) {
    if (medio == null) {
      throw new IllegalArgumentException("El medio de notificación no puede ser nulo.");
    }
    this.medio = medio;
  }
}
