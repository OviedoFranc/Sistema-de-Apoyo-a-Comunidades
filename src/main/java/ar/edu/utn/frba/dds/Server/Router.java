package ar.edu.utn.frba.dds.Server;

import static io.javalin.apibuilder.ApiBuilder.*;

import ar.edu.utn.frba.dds.CargadorDatos;
import ar.edu.utn.frba.dds.Controllers.ComunidadesController;
import ar.edu.utn.frba.dds.Controllers.FactoryController;
import ar.edu.utn.frba.dds.Controllers.IncidentesController;
import ar.edu.utn.frba.dds.Controllers.LoginController;
import ar.edu.utn.frba.dds.Controllers.PersonasController;
import ar.edu.utn.frba.dds.Controllers.ServiciosController;
import ar.edu.utn.frba.dds.Controllers.*;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Rol;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

public class Router {

    public static void init() {

        Server.app().get("/", context -> {
            context.redirect("/login");
        });


        Server.app().routes(() -> {
            get("cargarDatos", ctx -> {CargadorDatos cd = new CargadorDatos();cd.cargarDatos();});
            get("registro", ((PersonasController) FactoryController.controller("Personas"))::create);
            post("registro", ((PersonasController) FactoryController.controller("Personas"))::save);
            get("login", ((LoginController) FactoryController.controller("Login"))::get);
            post("login", ((LoginController) FactoryController.controller("Login"))::post);
            get("comunidades/obtenerTodas", ((ComunidadesController) FactoryController.controller("Comunidades"))::obtenerComunidades);
            get("comunidades", ((ComunidadesController) FactoryController.controller("Comunidades"))::index, Rol.NORMAL);
            get("comunidades/crear", ((ComunidadesController) FactoryController.controller("Comunidades"))::create, Rol.NORMAL);
            post("comunidades/crear", ((ComunidadesController) FactoryController.controller("Comunidades"))::save, Rol.NORMAL);
            get("comunidades/unirse", ((ComunidadesController) FactoryController.controller("Comunidades"))::unirse, Rol.NORMAL);
            post("comunidades/unirse", ((ComunidadesController) FactoryController.controller("Comunidades"))::update, Rol.NORMAL);
            get("comunidades/{id}/editar", ((ComunidadesController) FactoryController.controller("Comunidades"))::edit, Rol.NORMAL); // asjkdalsdjaslkdjl
            post("comunidades/{id}/editar/eliminar_miembro", ((ComunidadesController) FactoryController.controller("Comunidades"))::eliminarMiembro, Rol.NORMAL);
            post("comunidades/{id}/editar/editar_cargo_miembro", ((ComunidadesController) FactoryController.controller("Comunidades"))::editarCargoMiembro, Rol.NORMAL);
            post("comunidades/{id}/editar", ((ComunidadesController) FactoryController.controller("Comunidades"))::editarComunidad, Rol.NORMAL);
            get("comunidades/{id}/cambiar_rol", ((ComunidadesController) FactoryController.controller("Comunidades"))::editarRolMiembro, Rol.NORMAL);
            get("comunidades/{id}/servicios", ((ComunidadesController) FactoryController.controller("Comunidades"))::show, Rol.NORMAL);
            get("comunidades/{id}/servicios/agregar", ((ServiciosController) FactoryController.controller("Servicios"))::index, Rol.NORMAL);
            post("comunidades/{id}/servicios/agregar", ((ComunidadesController) FactoryController.controller("Comunidades"))::agregarServicio, Rol.NORMAL);
            post("comunidades/{id}/servicios/crear", ((ServiciosController) FactoryController.controller("Servicios"))::save, Rol.NORMAL);
            get("comunidades/{id}/servicios/{idServicio}", ((ServiciosController) FactoryController.controller("Servicios"))::show, Rol.NORMAL);
            post("comunidades/{id}/servicios/{idServicio}/crear_incidente", ((IncidentesController) FactoryController.controller("Incidentes"))::save, Rol.NORMAL);

            post("comunidades/{id}/servicios/{idServicio}/resolver/", ((IncidentesController) FactoryController.controller("Incidentes"))::edit, Rol.NORMAL);

            get("interes", ((PersonasController) FactoryController.controller("Personas"))::interes, Rol.NORMAL);
            get("interes/agregar_servicio", ((PersonasController) FactoryController.controller("Personas"))::interesSeleccionarServicio, Rol.NORMAL);
            post("interes/agregar_servicio", ((PersonasController) FactoryController.controller("Personas"))::interesAgregarServicio, Rol.NORMAL);
            get("interes/agregar_entidad", ((PersonasController) FactoryController.controller("Personas"))::interesSeleccionarEntidad, Rol.NORMAL);
            post("interes/agregar_entidad", ((PersonasController) FactoryController.controller("Personas"))::interesAgregarEntidad, Rol.NORMAL);

            get("/csv",((CSVController) FactoryController.controller("Csv"))::index, Rol.ADMINISTRADOR);
            post("/csv",((CSVController) FactoryController.controller("Csv"))::save, Rol.ADMINISTRADOR);

            /*Integracion del servicio 1 */
            get("/obtenerPosiblesFusiones", ((ComunidadesController) FactoryController.controller("Comunidades"))::obtenerPosiblesFusiones);
            post("comunidades/fusionar", ((ComunidadesController) FactoryController.controller("Comunidades"))::fusionarComunidades);

            get("rankings", ((RankingsController) FactoryController.controller("Rankings"))::index, Rol.PERSONA_DESIGNADA);
            get("rankings/{id}", ((RankingsController) FactoryController.controller("Rankings"))::show, Rol.PERSONA_DESIGNADA);

            get("notificaciones",((NotificacionesController) FactoryController.controller("Notificaciones"))::index, Rol.NORMAL);
            post("notificaciones",((NotificacionesController) FactoryController.controller("Notificaciones"))::delete, Rol.NORMAL);

            get("editar_perfil",((PersonasController) FactoryController.controller("Personas"))::edit, Rol.NORMAL);
            post("editar_perfil",((PersonasController) FactoryController.controller("Personas"))::update, Rol.NORMAL);

            get("editar_perfil_designada",((PersonasDesignadasController) FactoryController.controller("PersonasDesignadas"))::edit, Rol.PERSONA_DESIGNADA);
            post("editar_perfil_designada",((PersonasDesignadasController) FactoryController.controller("PersonasDesignadas"))::update, Rol.PERSONA_DESIGNADA);
        });
        Server.app().error(HttpStatus.NOT_FOUND, ctx -> {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.render("401.hbs");
        });
    }
}
