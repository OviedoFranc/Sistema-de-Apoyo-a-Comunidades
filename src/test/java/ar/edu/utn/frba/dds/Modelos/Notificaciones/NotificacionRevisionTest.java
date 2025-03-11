package ar.edu.utn.frba.dds.Modelos.Notificaciones;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ar.edu.utn.frba.dds.Modelos.Entidad;
import ar.edu.utn.frba.dds.Modelos.Establecimiento;
import ar.edu.utn.frba.dds.Modelos.Incidente;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.ConfiguracionTipoNotificacion.CuandoSuceden;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion.Notificacion;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Persona;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Rol;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Usuario;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioIncidentes;
import ar.edu.utn.frba.dds.Modelos.Servicio;
import ar.edu.utn.frba.dds.Modelos.UbicacionDTO.Localidad;
import ar.edu.utn.frba.dds.Servicio.EntidadesGeoRef.ListadoDeLocalidades;
import ar.edu.utn.frba.dds.Servicio.GeoRefAPIService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class NotificacionRevisionTest implements WithSimplePersistenceUnit{
  Persona carlos;
  MedioNotificacionesEmail emailDeContacto;
  GeoRefAPIService geoRefAPIService;

  @BeforeEach
  public void init() throws Exception {
    geoRefAPIService = mock(GeoRefAPIService.class);

    Localidad liniers = new Localidad();
    liniers.id = 02063010001;
    liniers.nombre = "LINIERS";
    ListadoDeLocalidades listado = new ListadoDeLocalidades();
    listado.localidades = List.of(liniers);
    doReturn(listado).when(geoRefAPIService).listadoDeLocalidades();

    Entidad entidad = new Entidad("Banco RIO", "", null, null);
    Establecimiento sucursalBancoRIO = new Establecimiento("Banco RIO", "", geoRefAPIService.listadoDeLocalidades().localidadDeId(02063010001).get(), "Av. Rivadavia 123", entidad);
    Servicio servicioRandom = new Servicio("Ascensor de la sucursal Liniers del Banco RIO", "null", sucursalBancoRIO);
    Incidente incidenteCercano = new Incidente(null, "Ascensor fuera de servicio", null, servicioRandom, null);
    List<Incidente> activos = new ArrayList<>();
    activos.add(incidenteCercano);

    RepositorioIncidentes.getInstance().add(incidenteCercano);


    emailDeContacto = mock(String.valueOf(MedioNotificacionesEmail.class));

    Usuario usuarioCarlos = new Usuario("CarlosR", "19r10jasd", Rol.NORMAL);
    carlos = new Persona("Carlos", "Rodriguez", usuarioCarlos, new CuandoSuceden(emailDeContacto));

    doAnswer(invocationOnMock -> {
      Object[] args = invocationOnMock.getArguments();
      Notificacion notificacion = (Notificacion) args[0];
      System.out.println(notificacion.getMensajeDeNotificacion());
      return null;
    }).when(emailDeContacto).notificar(any(Notificacion.class));
  }

  @Test
  public void AlTenerLaMismaUbicacionQueUnIncidenteSeLePreguntaACarlosSiPuedeRevisarlo() throws Exception {
    carlos.setUbicacion(geoRefAPIService.listadoDeLocalidades().localidadDeId(02063010001).get());

    verify(emailDeContacto, times(1)).notificar(any(Notificacion.class));
  }
}
