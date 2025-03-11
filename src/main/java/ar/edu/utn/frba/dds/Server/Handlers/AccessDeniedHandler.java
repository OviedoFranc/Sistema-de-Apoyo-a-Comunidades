package ar.edu.utn.frba.dds.Server.Handlers;

import ar.edu.utn.frba.dds.Server.Exceptions.AccessDeniedException;
import io.javalin.Javalin;

public class AccessDeniedHandler implements IHandler{

  @Override
  public void setHandle(Javalin app) {
    app.exception(AccessDeniedException.class, (e, context) -> {
      context.render("401.hbs");
    });
  }
}
