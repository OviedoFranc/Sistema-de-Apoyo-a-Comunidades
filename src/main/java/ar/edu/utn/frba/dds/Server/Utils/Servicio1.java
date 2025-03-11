package ar.edu.utn.frba.dds.Server.Utils;

import ar.edu.utn.frba.dds.Modelos.DTOServicio1.ComunidadDTO;
import ar.edu.utn.frba.dds.Modelos.DTOServicio1.PropuestaDeFusionDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import java.util.List;

public interface Servicio1 {
  @GET("/posiblesfusiones")
  Call<List<PropuestaDeFusionDTO>> obtenerPosiblesFusiones();

  @POST("/fusionarcomunidades")
  Call<ComunidadDTO> fusionarComunidades(@Body PropuestaDeFusionDTO comunidad);
}
