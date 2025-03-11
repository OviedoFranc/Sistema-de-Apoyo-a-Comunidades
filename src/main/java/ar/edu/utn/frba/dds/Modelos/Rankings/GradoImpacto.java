package ar.edu.utn.frba.dds.Modelos.Rankings;

import ar.edu.utn.frba.dds.Modelos.Entidad;
import ar.edu.utn.frba.dds.Modelos.Incidente;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioIncidentes;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioRankings;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositoriosItemsRankings;
import ar.edu.utn.frba.dds.Servicio.gradoDeImpacto.CalculadorGradoDeImpactoService;
import ar.edu.utn.frba.dds.Servicio.gradoDeImpacto.ListadoDeResultados;
import ar.edu.utn.frba.dds.Servicio.gradoDeImpacto.ListadoDeValores;
import ar.edu.utn.frba.dds.Servicio.gradoDeImpacto.ValoresFormula;
import javax.persistence.Entity;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/* TODO: TERMINAR, grado impactos nunca fue respondido, mirar documentación */

@Entity
public class GradoImpacto extends MetodosRanking {
    /*
    @Getter
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "ranking")
    private List<ItemRanking> rankingGradoDeImpacto;

     */
    final private Integer CNF = 1;

    public GradoImpacto() {
        this.nombre = "Mayor grado de impacto de las problematicas";
    }

    private double tiempoReparacion(Incidente incidente) {
        double diff = ChronoUnit.MINUTES.between(incidente.getFechaHoraCierre(), incidente.getFechaHoraApertura()) / 60.0;
        return Math.abs(diff);
    }

    private void enviarValoresAAPI(List<Entidad> entidades){
        List<ValoresFormula> listaValores = new ArrayList<>();
        for (Entidad entidad:entidades) {
            List<Incidente> incidentesEntidad = RepositorioIncidentes.getInstance().incidentesDeEntidad(entidad);
            int cantIncidentesNoResueltos = incidentesEntidad.stream().filter(incidente -> !incidente.getEstaResuelto()).toList().size();
            double tiempoResolucionIncidente = incidentesEntidad.stream()
                .filter(Incidente::getEstaResuelto)
                .mapToDouble(this::tiempoReparacion)
                .sum();
            int totalPersonasImpactadas = incidentesEntidad.stream().mapToInt(i -> i.getComunidad().totalMiembrosAfectados()).sum();

            listaValores.add(new ValoresFormula(entidad.getId(), (int) tiempoResolucionIncidente, cantIncidentesNoResueltos, CNF, totalPersonasImpactadas));
        }
        ListadoDeValores valores = new ListadoDeValores(listaValores);
        CalculadorGradoDeImpactoService.getInstancia().calcularGradoDeImpacto(valores);
    }

    @Override
    public List<ItemRanking> generarRanking(List<Entidad> entidades) throws IOException, InterruptedException {
        List<ItemRanking> rankingGradoDeImpacto = new ArrayList<>();
        enviarValoresAAPI(entidades);
        Thread.sleep(500);
        ListadoDeResultados resultados = CalculadorGradoDeImpactoService.getInstancia().obtenerResultados();
        for (Entidad entidad : entidades) {
            ItemRanking item = new ItemRanking(entidad, resultados.valorDeEntidad(entidad.getId()).getResultadoGradoImpacto(), LocalDateTime.now(), this);
            rankingGradoDeImpacto.add(item);
            RepositoriosItemsRankings.getInstance().add(item);
        }
        return rankingGradoDeImpacto;
    }
}
