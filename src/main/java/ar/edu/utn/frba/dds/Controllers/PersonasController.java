package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Modelos.Entidad;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.ConfiguracionTipoNotificacion.ConfiguracionNotificaciones;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.ConfiguracionTipoNotificacion.CuandoSuceden;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.MedioDeNotificacionesPreferido;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.MedioNotificacionesEmail;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.MedioNotificacionesWhatsapp;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.ConfiguracionTipoNotificacion.SinApuros;
import ar.edu.utn.frba.dds.Modelos.Servicio;
import ar.edu.utn.frba.dds.Modelos.UbicacionDTO.Localidad;
import ar.edu.utn.frba.dds.Modelos.UbicacionDTO.Provincia;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Persona;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Rol;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Usuario;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioEntidades;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioPersonas;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioServicios;
import ar.edu.utn.frba.dds.Seguridad.Filtros.ControlPasswordDebil;
import ar.edu.utn.frba.dds.Seguridad.ValidadorPassword;
import ar.edu.utn.frba.dds.Server.Utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.Servicio.GeoRefAPIService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PersonasController extends Controller implements ICrudViewsHandler {
  @Override
  public void index(Context context) {

  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {
    context.render("personas/registro.hbs");
  }

  @Override
  public void save(Context context) {

    ValidadorPassword validador = new ValidadorPassword();
    validador.addFiltro(new ControlPasswordDebil());

    Boolean contraseniaValida = validador.validarPassword(context.formParam("password"));

    Boolean usernameValido = !RepositorioPersonas.getInstance().usuarioYaExiste(context.formParam("username"));

    if (contraseniaValida && usernameValido) {
      MedioDeNotificacionesPreferido medio = null;
      if (context.formParam("selectMedio").equals("1")) {
        medio = new MedioNotificacionesWhatsapp(context.formParam("celular"));
      } else if (context.formParam("selectMedio").equals("2")){
        medio = new MedioNotificacionesEmail(context.formParam("email"));
      }

      ConfiguracionNotificaciones config;
      if (context.formParam("selectConfig").equals("2")) {
        config = new SinApuros(medio);
        for (int i = 1; i <= 4; i++) {
          if (!context.formParam("horario".concat(String.valueOf(i))).isBlank()) {
            ((SinApuros) config).agregarHorario(LocalTime.parse(context.formParam("horario".concat(String.valueOf(i)))));
          }
        }
      } else {
        config = new CuandoSuceden(medio);
      }

      Usuario usuario = new Usuario(context.formParam("username"), context.formParam("password"), Rol.NORMAL);

      Persona nuevo_user = new Persona(context.formParam("nombre"), context.formParam("apellido"), usuario, config);
      RepositorioPersonas.getInstance().add(nuevo_user);

      context.status(HttpStatus.CREATED);
      context.redirect("/login");
    } else {
      Map<String, Object> model = new HashMap<>();

      model.put("contraseniaInvalida", !contraseniaValida);
      model.put("usernameInvalido", !usernameValido);

      context.render("personas/registro.hbs", model);
    }
  }

  @Override
  public void edit(Context context) throws IOException {
    Map<String, Object> model = new HashMap<>();
    Persona persona = RepositorioPersonas.getInstance().get(context.sessionAttribute("id"));

    List<Provincia> provincias = GeoRefAPIService.getInstancia().listadoDeProvincias().provincias;
    provincias.sort(Comparator.comparing(Provincia::getNombre));

    String email = "";
    String celular = "";
    ConfiguracionNotificaciones configNotificaciones = persona.getConfiguracionNotificaciones();
    //Si es sin apuros obtenemos los horarios para actualizarlos
    if (configNotificaciones instanceof SinApuros) model.put("horarios", ((SinApuros) configNotificaciones).getHorariosDeNotificacion());

    // obtenemos los datos de los medios de notificacion dependiendo la configuracion
    if (configNotificaciones .getMedio() instanceof MedioNotificacionesEmail) email = ((MedioNotificacionesEmail) configNotificaciones.getMedio()).getEmail();
    if (configNotificaciones.getMedio() instanceof MedioNotificacionesWhatsapp) celular = ((MedioNotificacionesWhatsapp) configNotificaciones.getMedio()).getTelefono();

    model.put("user", persona);
    model.put("provincias", provincias);
    model.put("email", email);
    model.put("celular", celular);

    context.render("personas/editar_perfil.hbs", model);
  }

  @Override
  public void update(Context context){
    ValidadorPassword validador = new ValidadorPassword();
    validador.addFiltro(new ControlPasswordDebil());

    Persona persona = RepositorioPersonas.getInstance().get(context.sessionAttribute("id"));

    Boolean contraseniaValida = validador.validarPassword(context.formParam("password"));

    Boolean usernameValido = !RepositorioPersonas.getInstance().usuarioYaExiste(context.formParam("username"))
        || context.formParam("username").equals(persona.getUsername());

    Boolean ubicacionValida = true;
    Provincia prov = null;
    Localidad localidad = null;
    try {
      prov = GeoRefAPIService.getInstancia().listadoDeProvincias().provincias.stream().filter(provincia -> provincia.id == Long.parseLong(context.formParam("selectProvincia"))).findFirst().get();
      try {
        localidad = GeoRefAPIService.getInstancia().localidadPorNombreYProv(context.formParam("localidad"), prov.getId()).localidades.get(0);
      } catch (IndexOutOfBoundsException e) {
        ubicacionValida = false;
      }
    } catch (IOException e) {
      ubicacionValida = false;
    }

    if (contraseniaValida && usernameValido) {
      MedioDeNotificacionesPreferido medio = null;
      if (context.formParam("selectMedio").equals("1")) {
        medio = new MedioNotificacionesWhatsapp(context.formParam("celular"));
      } else if (context.formParam("selectMedio").equals("2")) {
        medio = new MedioNotificacionesEmail(context.formParam("email"));
      }

      ConfiguracionNotificaciones config;
      if (context.formParam("selectConfig").equals("2")) {
        config = new SinApuros(medio);
        for (int i = 1; i <= 4; i++) {
          if (!context.formParam("horario".concat(String.valueOf(i))).isBlank()) {
            ((SinApuros) config).agregarHorario(LocalTime.parse(context.formParam("horario".concat(String.valueOf(i)))));
          }
        }
      } else {
        config = new CuandoSuceden(medio);
      }



      persona.setNombre(context.formParam("nombre"));
      persona.setApellido(context.formParam("apellido"));
      persona.getUsuario().setUsername(context.formParam("username"));
      persona.getUsuario().setContrasenia(context.formParam("password"));
      persona.setConfiguracionNotificaciones(config);

      try {
        persona.setUbicacion(localidad);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

      RepositorioPersonas.getInstance().update(persona);

      context.status(HttpStatus.OK);
      context.redirect("/comunidades");
    } else {
      Map<String, Object> model = new HashMap<>();

      model.put("user", RepositorioPersonas.getInstance().get(context.sessionAttribute("id")));
      model.put("contraseniaInvalida", !contraseniaValida);
      model.put("usernameInvalido", !usernameValido);
      model.put("ubicacionInvalida", !ubicacionValida);

      context.render("personas/editar_perfil.hbs", model);
    }
  }

  @Override
  public void delete(Context context) {

  }

  public void interes(Context context) {
    Map<String, Object> model = new HashMap<>();
    RepositorioPersonas.getInstance().clean();
    Persona user = RepositorioPersonas.getInstance().get(context.sessionAttribute("id"));

    model.put("serviciosInteres", user.getServiciosDeInteres());
    model.put("entidadesInteres", user.getEntidadesDeInteres());

    context.render("personas/intereses/interes.hbs", model);
  }

  public void interesSeleccionarServicio(Context context) {
    Map<String, Object> model = new HashMap<>();

    RepositorioServicios.getInstance().clean();
    List servicios = RepositorioServicios.getInstance().all();
    Persona user = RepositorioPersonas.getInstance().get(context.sessionAttribute("id"));
    List serviciosQueLaPersonaNoTiene = servicios.stream().filter( s -> !user.getServiciosDeInteres().contains(s)).toList();

    model.put("servicios", serviciosQueLaPersonaNoTiene);

    context.render("personas/intereses/agregar_svc_interes.hbs", model);
  }

  public void interesAgregarServicio(Context context) {
    Servicio servicio = RepositorioServicios.getInstance().get(Long.parseLong(context.formParam("idServicio")));
    Persona user = RepositorioPersonas.getInstance().get(context.sessionAttribute("id"));

    user.agregarServicioDeInteres(servicio);
    RepositorioPersonas.getInstance().update(user);

    context.status(HttpStatus.OK);
    context.redirect("/interes");
  }

  public void interesSeleccionarEntidad(Context context) {
    Map<String, Object> model = new HashMap<>();

    RepositorioEntidades.getInstance().clean();
    List entidades = RepositorioEntidades.getInstance().all();
    Persona user = RepositorioPersonas.getInstance().get(context.sessionAttribute("id"));
    List entidadesQueLaPersonaNoTiene = entidades.stream().filter( e -> !user.getEntidadesDeInteres().contains(e)).toList();

    model.put("entidades", entidadesQueLaPersonaNoTiene);

    context.render("personas/intereses/agregar_entidad_interes.hbs", model);
  }

  public void interesAgregarEntidad(Context context) {
    Entidad entidad = RepositorioEntidades.getInstance().get(Long.parseLong(context.formParam("idEntidad")));
    Persona user = RepositorioPersonas.getInstance().get(context.sessionAttribute("id"));

    user.agregarEntidadDeInteres(entidad);
    RepositorioPersonas.getInstance().update(user);

    context.status(HttpStatus.OK);
    context.redirect("/interes");
  }
}
