package ar.edu.utn.frba.dds.Servicio;

import ar.edu.utn.frba.dds.Servicio.EntidadesGeoRef.ListadoDeLocalidades;
import ar.edu.utn.frba.dds.Servicio.EntidadesGeoRef.ListadoDeProvincias;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;


public class GeoRefAPIService {
    private static GeoRefAPIService instancia = null;
    private  static  final String URLAPI = "https://apis.datos.gob.ar/georef/api/";
    private Retrofit retrofit;

    private GeoRefAPIService(){
      this.retrofit = new Retrofit.Builder()
          .baseUrl(URLAPI)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
    }

    public static GeoRefAPIService getInstancia(){
      if (instancia == null){
        instancia = new GeoRefAPIService();
      }
      return instancia;
    }

    public ListadoDeProvincias listadoDeProvincias() throws IOException {
      GeoRefService geoRefService = this.retrofit.create(GeoRefService.class);
      Call<ListadoDeProvincias> requestProvinciasArgentinas =  geoRefService.provincias();
      Response<ListadoDeProvincias> responseProvinciasArgentinas = requestProvinciasArgentinas.execute();
      return responseProvinciasArgentinas.body();
    }

  public ListadoDeLocalidades listadoDeLocalidades() throws IOException {
    GeoRefService geoRefService = this.retrofit.create(GeoRefService.class);
    Call<ListadoDeLocalidades> requestLocalidadesArgentinas =  geoRefService.localidades();
    Response<ListadoDeLocalidades> responseLocalidadesArgentinas = requestLocalidadesArgentinas.execute();
    return responseLocalidadesArgentinas.body();
  }

  public ListadoDeLocalidades localidadPorNombreYProv(String nombre, long idProvincia) throws IOException {
    GeoRefService geoRefService = this.retrofit.create(GeoRefService.class);
    Call<ListadoDeLocalidades> requestLocalidadesArgentinas =  geoRefService.localidades(nombre, idProvincia);
    Response<ListadoDeLocalidades> responseLocalidadesArgentinas = requestLocalidadesArgentinas.execute();
    return responseLocalidadesArgentinas.body();
  }
  }