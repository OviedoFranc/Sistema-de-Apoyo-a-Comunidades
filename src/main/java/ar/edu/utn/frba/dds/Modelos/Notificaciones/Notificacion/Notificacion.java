package ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion;

import ar.edu.utn.frba.dds.Modelos.Incidente;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Persona;
import ar.edu.utn.frba.dds.Persistencia.EntidadPersistente;
import lombok.Getter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "notificaciones")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Notificacion extends EntidadPersistente {
  @Getter
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  protected Incidente incidente;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  protected Persona usuarioANotificar;
  @Getter
  @Column
  protected LocalDateTime fecha;

  public Notificacion(Incidente incidente, Persona usuarioANotificar) {
    this.incidente = incidente;
    this.usuarioANotificar = usuarioANotificar;
    this.fecha = LocalDateTime.now();
  }

  public Notificacion() {

  }

  public abstract String getMensajeDeNotificacion();

  public abstract String getEncabezado();

  public String getFechaString() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm, dd-MM-yyy");

    return fecha.format(dateTimeFormatter);
  }
}
