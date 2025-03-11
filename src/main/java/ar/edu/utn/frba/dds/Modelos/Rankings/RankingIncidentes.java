package ar.edu.utn.frba.dds.Modelos.Rankings;

import ar.edu.utn.frba.dds.Modelos.Entidad;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioEntidades;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioRankings;
import lombok.Getter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class RankingIncidentes {
  private static RankingIncidentes instance = null;

  @Getter
  private List<MetodosRanking> metodosRankings = List.of(new TiempoDeCierre(), new MayorCantidadIncidentes(), new GradoImpacto()); //[new TiempoDeCierre(),MayorCantidadIncidentes,GradoImpacto]
  @Getter
  private Boolean running = false;

  public RankingIncidentes(){

  }
  public static RankingIncidentes getInstance(){
    if (instance == null){
      instance = new RankingIncidentes();
    }
    return instance;
  }

  public void generarRankings() {
    Timer tempo = new Timer();
    LocalDateTime semanaPasada = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
    long duracionSemanaEnMiliseg = semanaPasada.until(LocalDateTime.now(), ChronoUnit.MILLIS);
    running = true;
    // Para que ejecute el generador 1 vez y despues lo haga cada 7 dias
    tempo.scheduleAtFixedRate(new GeneradorRanking(), 1, 180000);
    //tempo.scheduleAtFixedRate(new GeneradorRanking(), 1, duracionSemanaEnMiliseg);
  }

  private class GeneradorRanking extends TimerTask {
    @Override
    public void run() {
      metodosRankings.forEach(metodo -> {
        RepositorioRankings.getInstance().add(metodo);
        List<Entidad> entidades = RepositorioEntidades.getInstance().all();
        //GeneradorDeInformes generadorDeInformes = new GeneradorDeInformes();
        try {
          metodo.generarRanking(entidades);
          //generadorDeInformes.generarInforme(items, metodo.nombre);
        } catch (IOException e) {
          throw new RuntimeException(e);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      });
    }
  }

}
