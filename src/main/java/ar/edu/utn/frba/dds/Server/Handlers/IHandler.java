package ar.edu.utn.frba.dds.Server.Handlers;

import io.javalin.Javalin;

public interface IHandler {
  void setHandle(Javalin app);
}
