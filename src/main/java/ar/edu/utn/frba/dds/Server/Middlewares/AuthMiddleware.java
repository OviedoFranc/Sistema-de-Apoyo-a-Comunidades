package ar.edu.utn.frba.dds.Server.Middlewares;

import ar.edu.utn.frba.dds.Modelos.Usuarios.Rol;
import ar.edu.utn.frba.dds.Server.Exceptions.AccessDeniedException;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class AuthMiddleware {
  public static void apply(JavalinConfig config) {
    config.accessManager(((handler, context, routeRoles) -> {
      Rol userRole = getUserRoleType(context);

      if(routeRoles.size() == 0 || routeRoles.contains(userRole)) {
        handler.handle(context);
      }
      else {
        throw new AccessDeniedException();
      }
    }));
  }

  private static Rol getUserRoleType(Context context) {
    return context.sessionAttribute("tipo_rol") != null?
        Rol.valueOf(context.sessionAttribute("tipo_rol")) : null;
  }
}
