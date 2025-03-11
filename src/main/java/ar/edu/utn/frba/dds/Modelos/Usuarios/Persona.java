package ar.edu.utn.frba.dds.Modelos.Usuarios;

import ar.edu.utn.frba.dds.Modelos.Comunidades.CargoComunidad;
import ar.edu.utn.frba.dds.Modelos.Comunidades.Comunidad;
import ar.edu.utn.frba.dds.Modelos.Entidad;
import ar.edu.utn.frba.dds.Modelos.Incidente;
import ar.edu.utn.frba.dds.Modelos.Comunidades.Membresia;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.ConfiguracionTipoNotificacion.ConfiguracionNotificaciones;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion.Notificacion;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion.NotificacionRevision;
import ar.edu.utn.frba.dds.Modelos.Comunidades.RolComunidad;
import ar.edu.utn.frba.dds.Modelos.Servicio;
import ar.edu.utn.frba.dds.Modelos.UbicacionDTO.Localidad;
import ar.edu.utn.frba.dds.Persistencia.EntidadPersistente;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioIncidentes;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioNotificaciones;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persona")
public class Persona extends EntidadPersistente {
  @Getter
  @Setter
  @Column
  private String nombre;
  @Getter
  @Setter
  @Column
  private String apellido;
  @Getter
  @Setter
  @Embedded
  private Usuario usuario;
  @Getter
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "entidadesInteres_por_persona")
  private List<Entidad> entidadesDeInteres;
  @Getter
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "serviciosInteres_por_persona")
  private List<Servicio> serviciosDeInteres;
  @Embedded
  @AttributeOverride(name="nombre", column=@Column(name="localidad"))
  private Localidad ubicacion;
  @JsonIgnore
  @Getter
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "miembro", fetch = FetchType.EAGER)
  @Fetch(value = FetchMode.SUBSELECT)
  private List<Membresia> membresiasAComunidades;
  @Getter
  @Setter
  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
  private ConfiguracionNotificaciones configuracionNotificaciones;

  public Persona(String nombre, String apellido, Usuario usuario, ConfiguracionNotificaciones configuracionNotificaciones) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.usuario = usuario;
    this.entidadesDeInteres = new ArrayList<>();
    this.serviciosDeInteres = new ArrayList<>();
    this.membresiasAComunidades = new ArrayList<>();
    this.configuracionNotificaciones = configuracionNotificaciones;
  }

  public Persona() {

  }

  public void setUbicacion(Localidad nuevaUbicacion) throws Exception {
    this.ubicacion = nuevaUbicacion;
    List<Incidente> incidentesConMismaLocalizacion = RepositorioIncidentes.getInstance().incidentesEnUbicacion(nuevaUbicacion);
    for (Incidente incidente: incidentesConMismaLocalizacion) {
      Notificacion notificacion = new NotificacionRevision(incidente, this);
      notificar(notificacion);
    }
  }

  public String getUsername(){
    return this.usuario.getUsername();
  }

  public void darseAltaComunidadCreada(Comunidad comunidad) {
    Membresia membresia = new Membresia(comunidad, RolComunidad.AFECTADO, this, CargoComunidad.ADMINISTRADOR);
    membresiasAComunidades.add(membresia);
    comunidad.agregarMiembro(membresia);
  }

  public void darseAltaComunidad(Comunidad comunidad, RolComunidad rolComunidad) {
    Membresia membresia = new Membresia(comunidad, rolComunidad, this, CargoComunidad.MIEMBRO);

    membresiasAComunidades.add(membresia);
    comunidad.agregarMiembro(membresia);
  }

  public void darseAltaComunidadFusionada(Comunidad comunidad, RolComunidad rolComunidad, CargoComunidad cargo) {
    Membresia membresia = new Membresia(comunidad, rolComunidad, this, cargo);

    membresiasAComunidades.add(membresia);
    comunidad.agregarMiembro(membresia);
  }

  public void darseBajaComunidad(Membresia membresia) {
    this.membresiasAComunidades.remove(membresia);
    membresia.getComunidad().eliminarMiembro(membresia);
  }

  public Boolean esAdmin(Comunidad comunidad){
    Membresia mem = this.getMembresiasAComunidades().stream().filter(membresia -> membresia.getComunidad().equals(comunidad)).toList().get(0);

    return mem.getCargoDentroDeComunidad().equals(CargoComunidad.ADMINISTRADOR);
  }

  public void notificar(Notificacion notificacion) throws Exception {
    RepositorioNotificaciones.getInstance().add(notificacion);
    configuracionNotificaciones.notificarMiembro(notificacion);
  }

  public void agregarEntidadDeInteres(Entidad entidad){
    entidadesDeInteres.add(entidad);
  }

  public void borrarEntidadDeInteres(Entidad entidad){
    entidadesDeInteres.remove(entidad);
  }

  public void agregarServicioDeInteres(Servicio servicio){
    serviciosDeInteres.add(servicio);
  }

  public void borrarServicioDeInteres(Servicio servicio){
    serviciosDeInteres.remove(servicio);
  }
}
