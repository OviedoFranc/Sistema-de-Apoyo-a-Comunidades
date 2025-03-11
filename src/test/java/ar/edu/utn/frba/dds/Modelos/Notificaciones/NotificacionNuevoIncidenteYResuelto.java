package ar.edu.utn.frba.dds.Modelos.Notificaciones;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import ar.edu.utn.frba.dds.Modelos.Comunidades.Comunidad;
import ar.edu.utn.frba.dds.Modelos.Entidad;
import ar.edu.utn.frba.dds.Modelos.Establecimiento;
import ar.edu.utn.frba.dds.Modelos.Incidente;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.ConfiguracionTipoNotificacion.CuandoSuceden;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.ConfiguracionTipoNotificacion.SinApuros;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion.Notificacion;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Persona;
import ar.edu.utn.frba.dds.Modelos.Comunidades.RolComunidad;
import ar.edu.utn.frba.dds.Modelos.Servicio;
import ar.edu.utn.frba.dds.Modelos.UbicacionDTO.Localidad;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Rol;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NotificacionNuevoIncidenteYResuelto {
  Persona pepe;
  Persona jose;
  Servicio unServicio;
  Comunidad comunidad;

  @BeforeEach
  public void init() throws Exception {
    MedioNotificacionesEmail medioDeContactoPepe = mock(MedioNotificacionesEmail.class); //new ContactoEmail("pepe@gmail.com");
    SinApuros configPepe = new SinApuros(medioDeContactoPepe);
    configPepe.agregarHorario(LocalTime.now().plus(10, ChronoUnit.SECONDS));
    configPepe.agregarHorario(LocalTime.of(20,30,0));
    configPepe.agregarHorario(LocalTime.of(23,0,0));
    configPepe.agregarHorario(LocalTime.of(19,0,0));

    doAnswer(invocationOnMock -> {
      Object[] args = invocationOnMock.getArguments();
      Notificacion notificacion = (Notificacion) args[0];
      System.out.println(notificacion.getMensajeDeNotificacion());
      return null;
    }).when(medioDeContactoPepe).notificar(any(Notificacion.class));


    MedioDeNotificacionesPreferido medioDeContactoJose = mock(MedioNotificacionesEmail.class); //new ContactoEmail("jose@gmail.com");
    CuandoSuceden configJose = new CuandoSuceden(medioDeContactoJose);

    doAnswer(invocationOnMock -> {
      Object[] args = invocationOnMock.getArguments();
      Notificacion notificacion = (Notificacion) args[0];
      System.out.println(notificacion.getMensajeDeNotificacion());
      return null;
    }).when(medioDeContactoJose).notificar(any(Notificacion.class));

    Usuario usuarioPepe = new Usuario("pepegonz", "askfakof", Rol.NORMAL);
    Usuario usuarioJuan = new Usuario("josegonz", "askfakof", Rol.NORMAL);
    pepe = new Persona("pepe", "gonzalez", usuarioPepe, configPepe);
    jose = new Persona("jose", "gonzalez", usuarioJuan, configJose);

    Localidad localidadRandom = new Localidad();
    localidadRandom.id = 6;
    localidadRandom.nombre = "Localidad X";
    Entidad entidad = new Entidad("Una entidad", "", null, null);
    Establecimiento establecimiento = new Establecimiento("Un establecimiento", "", localidadRandom, "", entidad);
    unServicio = new Servicio("Escalera mecanica 2do piso", "", establecimiento);
    comunidad = new Comunidad("Comunidad1");
    pepe.darseAltaComunidad(comunidad, RolComunidad.AFECTADO);
    jose.darseAltaComunidad(comunidad, RolComunidad.AFECTADO);
    comunidad.agregarServicioDeInteres(unServicio);

  }

  @Test
  public void seEnvianLasNotificacionesAlAbrirUnIncidente() throws InterruptedException {
    Incidente incidente = new Incidente(null, "La escalera mecanica del segundo piso no esta en funcionamiento", pepe, unServicio, comunidad);
    CountDownLatch lock = new CountDownLatch(1);

    comunidad.informarNuevoIncidente(incidente);

    lock.await(12, TimeUnit.SECONDS); // para que el test no finalice y le de tiempo a testear el "SinApuros"
  }

  @Test
  public void seEnvianLasNotificacionesAlCerrarUnIncidente() throws InterruptedException {
    Incidente incidente = new Incidente(null, "La escalera mecanica del segundo piso no esta en funcionamiento", pepe, unServicio, comunidad);
    CountDownLatch lock = new CountDownLatch(1);

    comunidad.informarIncidenteResuelto(incidente);

    lock.await(12, TimeUnit.SECONDS); // para que el test no finalice y le de tiempo a testear el "SinApuros"
  }
}
