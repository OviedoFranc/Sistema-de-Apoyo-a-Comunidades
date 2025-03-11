package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Modelos.Usuarios.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

public abstract class Controller implements WithSimplePersistenceUnit {
  protected Usuario usuarioLogueado(Context ctx) {
    if(ctx.sessionAttribute("usuario_id") == null)
      return null;
    return entityManager()
        .find(Usuario.class, Long.parseLong(ctx.sessionAttribute("usuario_id")));
  }
}
