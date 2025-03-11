package ar.edu.utn.frba.dds.Controllers;

import static java.lang.Thread.sleep;

import ar.edu.utn.frba.dds.Modelos.Comunidades.CargoComunidad;
import ar.edu.utn.frba.dds.Modelos.Comunidades.Comunidad;
import ar.edu.utn.frba.dds.Modelos.DTOServicio1.ComunidadDTO;
import ar.edu.utn.frba.dds.Modelos.Comunidades.Membresia;
import ar.edu.utn.frba.dds.Modelos.Comunidades.RolComunidad;
import ar.edu.utn.frba.dds.Modelos.DTOServicio1.ListaPropuestas;
import ar.edu.utn.frba.dds.Modelos.DTOServicio1.PropuestaDeFusion;
import ar.edu.utn.frba.dds.Modelos.DTOServicio1.PropuestaDeFusionDTO;
import ar.edu.utn.frba.dds.Modelos.DTOServicio1.ServicioParticularObservadoDTO;
import ar.edu.utn.frba.dds.Modelos.Servicio;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Persona;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioIncidentes;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioMembresias;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioPersonas;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioServicios;
import ar.edu.utn.frba.dds.Server.Utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.Server.Utils.Servicio1;
import ar.edu.utn.frba.dds.Servicio.gradoDeImpacto.EntidadValor;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ComunidadesController extends Controller implements ICrudViewsHandler {

  @Override
  public void index(Context context) {
    long id = context.sessionAttribute("id");
    List<Membresia> membresias = RepositorioMembresias.getInstance().getComunidadesDePersona(id);
    ArrayList<Comunidad> comunidades = new ArrayList<Comunidad>();

    for (Membresia m:membresias) {
      comunidades.add(m.getComunidad());
    }

    Map<String, Object> model = new HashMap<>();
    model.put("comunidades", comunidades);

    context.render("comunidades/comunidades.hbs", model);
  }

  @Override
  public void show(Context context) {
    RepositorioComunidades.getInstance().clean();
    Comunidad comunidad = RepositorioComunidades.getInstance().get(Long.parseLong(context.pathParam("id")));
    Map<String, Object> model = new HashMap<>();
    model.put("comunidad", comunidad);

    ArrayList<Servicio> serviciosSinIncidentes = new ArrayList<>();
    ArrayList<Servicio> serviciosConIncidentes = new ArrayList<>();
    for (Servicio s:comunidad.getServiciosDeInteres()) {
      if (RepositorioIncidentes.getInstance().hayIncidentesActivosEnServicioDeComunidad(s.getId(), comunidad.getId())){
        serviciosConIncidentes.add(RepositorioServicios.getInstance().get(s.getId()));
      } else {
        serviciosSinIncidentes.add(RepositorioServicios.getInstance().get(s.getId()));
      }
    }
    model.put("serviciosSinIncidentes", serviciosSinIncidentes);
    model.put("serviciosConIncidentes", serviciosConIncidentes);

    Persona user = RepositorioPersonas.getInstance().get(context.sessionAttribute("id"));
    Boolean esAdmin = user.esAdmin(comunidad);
    model.put("admin", esAdmin);

    Membresia membresia = RepositorioMembresias.getInstance().membresiaDePersonaEnComunidad(context.sessionAttribute("id"), comunidad.getId());
    model.put("membresia", membresia);

    context.render("servicios/servicios.hbs", model);
  }

  @Override
  public void create(Context context) {
    context.render("comunidades/crear_comunidad.hbs");
  }

  @Override
  public void save(Context context) {
    Comunidad comunidadNueva = new Comunidad(context.formParam("nombreComunidad"));
    long id = context.sessionAttribute("id");
    RepositorioComunidades.getInstance().add(comunidadNueva);
    Persona user = RepositorioPersonas.getInstance().get(id);
    user.darseAltaComunidadCreada(comunidadNueva);
    RepositorioPersonas.getInstance().update(user);

    context.status(HttpStatus.CREATED);
    context.redirect("/comunidades");
  }

  @Override
  public void edit(Context context) {
    RepositorioMembresias.getInstance().clean();
    Map<String, Object> model = new HashMap<>();
    Comunidad comunidad = RepositorioComunidades.getInstance().get(Long.parseLong(context.pathParam("id")));
    List membresias = RepositorioMembresias.getInstance().membresiasDeComunidad(comunidad);


    model.put("comunidad", comunidad);
    model.put("membresias", membresias);
    model.put("cargos", CargoComunidad.values());

    context.render("comunidades/editar_comunidad.hbs", model);
  }

  public void eliminarMiembro(Context context){
    Membresia membresia = RepositorioMembresias.getInstance().membresiaDePersonaEnComunidad(Long.parseLong(context.formParam("idMiembro")), Long.parseLong(context.pathParam("id")));

    membresia.getMiembro().darseBajaComunidad(membresia);
    RepositorioPersonas.getInstance().update(membresia.getMiembro());
    RepositorioMembresias.getInstance().delete(membresia);

    context.status(HttpStatus.OK);
    context.redirect("/comunidades/".concat(context.pathParam("id")).concat("/editar"));
  }

  public void editarCargoMiembro(Context context){
    Membresia membresia = RepositorioMembresias.getInstance().membresiaDePersonaEnComunidad(Long.parseLong(context.formParam("idMiembro")), Long.parseLong(context.pathParam("id")));

    membresia.cambiarCargo(CargoComunidad.valueOf(context.formParam("cargoM")));
    RepositorioMembresias.getInstance().update(membresia);

    context.status(HttpStatus.OK);
    context.redirect("/comunidades/".concat(context.pathParam("id")).concat("/editar"));
  }

  public void editarComunidad(Context context){
    Comunidad comunidad = RepositorioComunidades.getInstance().get(Long.parseLong(context.pathParam("id")));
    comunidad.setNombreComunidad(context.formParam("nombre"));

    RepositorioComunidades.getInstance().update(comunidad);

    context.redirect("/comunidades/".concat(context.pathParam("id")).concat("/servicios"));
  }

  public void editarRolMiembro(Context context){
    RepositorioMembresias.getInstance().clean();
    Membresia membresia = RepositorioMembresias.getInstance().membresiaDePersonaEnComunidad(context.sessionAttribute("id"), Long.parseLong(context.pathParam("id")));
    membresia.cambiarRol();

    RepositorioMembresias.getInstance().update(membresia);

    context.redirect("/comunidades/".concat(context.pathParam("id")).concat("/servicios"));
  }

  public void obtenerComunidades(Context context) {
    RepositorioComunidades.getInstance().clean();
    List<Comunidad> comunidades = RepositorioComunidades.getInstance().all();

    List<ComunidadDTO> comunidadesDTO = comunidades.stream()
        .map(Comunidad::toDTO)
        .collect(Collectors.toList());

    context.status(HttpStatus.OK);
    context.json(comunidadesDTO);
  }


  public void unirse(Context context) {
    Map<String, Object> model = new HashMap<>();
    List comunidadesQueNoFormaParte = RepositorioComunidades.getInstance().all().stream().filter(comunidad -> !comunidad.personaFormaParteDeLaComunidad(RepositorioPersonas.getInstance().get(context.sessionAttribute("id")))).toList();
    model.put("comunidades", comunidadesQueNoFormaParte);
    model.put("roles", RolComunidad.values());

    context.render("comunidades/unirse_comunidad.hbs", model);
  }

  @Override
  public void update(Context context) {
    Comunidad comunidad = RepositorioComunidades.getInstance().get(Long.parseLong(context.formParam("comunidad")));
    Persona persona = RepositorioPersonas.getInstance().get(context.sessionAttribute("id"));
    persona.darseAltaComunidad(comunidad, RolComunidad.valueOf(context.formParam("rol")));

    RepositorioComunidades.getInstance().update(comunidad);
    RepositorioPersonas.getInstance().update(persona);

    context.status(HttpStatus.OK);
    context.redirect("/comunidades");
  }

  public void agregarServicio(Context context) {
    Comunidad comunidad = RepositorioComunidades.getInstance().get(Long.parseLong(context.pathParam("id")));
    Servicio servicio = RepositorioServicios.getInstance().get(Long.parseLong(context.formParam("idServicio")));
    comunidad.agregarServicioDeInteres(servicio);

    RepositorioComunidades.getInstance().update(comunidad);

    context.status(HttpStatus.OK);
    context.redirect("/comunidades/".concat(context.pathParam("id")).concat("/servicios"));
  }

  public void fusionarComunidades(Context context) {
    try {

      Comunidad una = RepositorioComunidades.getInstance().get(Long.parseLong(context.formParam("idComunidad1")));
      Comunidad otra = RepositorioComunidades.getInstance().get(Long.parseLong(context.formParam("idComunidad2")));
      PropuestaDeFusionDTO propuestaDeFusionDTO = new PropuestaDeFusionDTO(
        new ComunidadDTO((int) una.getId(), una.getNombreComunidad(), una.getServiciosDeInteres(), una.getGradoDeConfianza()),
        new ComunidadDTO((int) otra.getId(), otra.getNombreComunidad(), otra.getServiciosDeInteres(), otra.getGradoDeConfianza())
      );

      // Crear una instancia de Retrofit
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://api-fusion-de-comunidades.onrender.com")
          .addConverterFactory(GsonConverterFactory.create())
          .build();

      // Usar la instancia para hacer una llamada POST a /fusionarcomunidades
      Servicio1 service = retrofit.create(Servicio1.class);
      retrofit2.Response<ComunidadDTO> response = service.fusionarComunidades(propuestaDeFusionDTO).execute();
      sleep(300);
      if (response.code() == HttpStatus.OK.getCode()) {
        ComunidadDTO resultado = new ComunidadDTO(response.body());

        Comunidad fusionada = new Comunidad(resultado.getNombre());
        fusionada.setGradoDeConfianza((double) resultado.getGradoDeConfianza());
        for (ServicioParticularObservadoDTO s:resultado.getServiciosParticularesObservados()) {
          fusionada.agregarServicioDeInteres(RepositorioServicios.getInstance().get(s.getServicio().getId()));
        }
        Set<Persona> todos = new HashSet<>();
        List<Persona> deUnaComunidad = RepositorioComunidades.getInstance().get(Long.parseLong(context.formParam("idComunidad1"))).getMiembros().stream().map(Membresia::getMiembro).toList();
        List<Persona> deOtraComunidad = RepositorioComunidades.getInstance().get(Long.parseLong(context.formParam("idComunidad2"))).getMiembros().stream().map(Membresia::getMiembro).toList();
        todos.addAll(deUnaComunidad);
        todos.addAll(deOtraComunidad);

        for (Persona p: todos) {
          p.darseAltaComunidadFusionada(fusionada, RolComunidad.AFECTADO, CargoComunidad.ADMINISTRADOR);
        }
        List<Membresia> memDeC1 = RepositorioMembresias.getInstance().membresiasDeComunidad(una);
        for (Membresia m:memDeC1) {
          m.getMiembro().darseBajaComunidad(m);
          RepositorioMembresias.getInstance().delete(m);
        }
        List<Membresia> memDeC2 = RepositorioMembresias.getInstance().membresiasDeComunidad(otra);
        for (Membresia m:memDeC2) {
          m.getMiembro().darseBajaComunidad(m);
          RepositorioMembresias.getInstance().delete(m);
        }

        RepositorioComunidades.getInstance().add(fusionada);

        context.redirect("/obtenerPosiblesFusiones");
        RepositorioComunidades.getInstance().delete(una);
        RepositorioComunidades.getInstance().delete(otra);

      } else {
        context.status(response.code()).result("Error al fusionar comunidades");

      }
    } catch (Exception e) {
      e.printStackTrace();
      context.status(500).result("Error interno del servidor");
    }
  }

  public void obtenerPosiblesFusiones(Context context) {
    try {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://api-fusion-de-comunidades.onrender.com")
          .addConverterFactory(GsonConverterFactory.create())
          .build();

      Servicio1 service = retrofit.create(Servicio1.class);

      retrofit2.Response<List<PropuestaDeFusionDTO>> response = service.obtenerPosiblesFusiones().execute();
      sleep(300);
      if (response.code() == HttpStatus.OK.getCode()) {
        ListaPropuestas listado = new ListaPropuestas(response.body());
        Map<String, Object> model = new HashMap<>();

        ArrayList<PropuestaDeFusionDTO> listadoAMostrar = new ArrayList<>();
        for (PropuestaDeFusionDTO propuesta : listado.getPropuestas()) {
          listadoAMostrar.add(propuesta);
        }

        model.put("listado", listadoAMostrar);

        context.render("comunidades/comunidades_fusion.hbs", model);
      } else {
        context.render("comunidades/error_fusion.hbs");
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.status(500).result("Error interno del servidor");
    }
  }
  @Override
  public void delete(Context context) {

  }
}
