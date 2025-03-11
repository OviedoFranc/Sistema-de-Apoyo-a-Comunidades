package ar.edu.utn.frba.dds.Servicio.gradoDeImpacto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import java.util.List;

public interface CalculadorGradoDeImpactoAPIService {
  @GET("/api/calcularImpacto")
  Call<List<EntidadValor>> obtenerResultados();

  @POST("/api/calcularImpacto")
  Call<ListadoDeValores> calcularGradoDeImpacto(@Body ListadoDeValores listadoDeValores);

}
