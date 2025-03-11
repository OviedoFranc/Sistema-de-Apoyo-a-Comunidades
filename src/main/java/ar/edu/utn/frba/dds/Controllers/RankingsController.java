package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Modelos.Rankings.ItemRanking;
import ar.edu.utn.frba.dds.Modelos.Rankings.MetodosRanking;
import ar.edu.utn.frba.dds.Modelos.Rankings.RankingIncidentes;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioRankings;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositoriosItemsRankings;
import ar.edu.utn.frba.dds.Server.Utils.ICrudViewsHandler;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingsController extends Controller implements ICrudViewsHandler {

  @Override
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();

    List<MetodosRanking> rankings = RepositorioRankings.getInstance().all();

    model.put("rankings", rankings);

    context.render("rankings.hbs", model);
  }

  @Override
  public void show(Context context) {
    Map<String, Object> model = new HashMap<>();

    long idRanking = Long.parseLong(context.pathParam("id"));
    //List<ItemRanking> items = RepositoriosItemsRankings.getInstance().masRecientes(idRanking);
    List<ItemRanking> items = RepositoriosItemsRankings.getInstance().itemsEstaSemana(idRanking);
    MetodosRanking ranking = RepositorioRankings.getInstance().get(idRanking);

    model.put("items", items);
    model.put("ranking", ranking);

    context.render("ranking.hbs", model);
  }

  @Override
  public void create(Context context) {

  }

  @Override
  public void save(Context context) {

  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) {

  }

  @Override
  public void delete(Context context) {

  }
}
