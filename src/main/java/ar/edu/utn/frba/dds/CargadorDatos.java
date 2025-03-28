package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Modelos.Comunidades.Comunidad;
import ar.edu.utn.frba.dds.Modelos.Entidad;
import ar.edu.utn.frba.dds.Modelos.EntidadPropietaria;
import ar.edu.utn.frba.dds.Modelos.Establecimiento;
import ar.edu.utn.frba.dds.Modelos.Incidente;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.ConfiguracionTipoNotificacion.CuandoSuceden;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.MedioNotificacionesEmail;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.MedioNotificacionesWhatsapp;
import ar.edu.utn.frba.dds.Modelos.Notificaciones.ConfiguracionTipoNotificacion.SinApuros;
import ar.edu.utn.frba.dds.Modelos.OrganismoDeControl;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Persona;
import ar.edu.utn.frba.dds.Modelos.Usuarios.PersonaDesignada;
import ar.edu.utn.frba.dds.Modelos.Servicio;
import ar.edu.utn.frba.dds.Modelos.UbicacionDTO.Localidad;
import ar.edu.utn.frba.dds.Modelos.UbicacionDTO.Provincia;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Rol;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Usuario;
import ar.edu.utn.frba.dds.Persistencia.EntityManagerHelper;
import ar.edu.utn.frba.dds.Servicio.GeoRefAPIService;
import java.time.LocalTime;

public class CargadorDatos {
  public CargadorDatos() {
  }

  public void cargarDatos() throws Exception {
    // USUARIOS Y PERSONSAS DESIGNADAS
    Usuario usuarioLarreta = new Usuario("hr.larreta","1234",Rol.PERSONA_DESIGNADA);
    PersonaDesignada larretaSubte = new PersonaDesignada("Horacio","Rodriguez Larreta",usuarioLarreta);


    // ORGANISMOS DE CONTROL
    OrganismoDeControl gobCiudad = new OrganismoDeControl("Gobierno de la ciudad autonoma de Buenos Aires", larretaSubte);

    //ENTIDAD PROPIETARIAS
    EntidadPropietaria SBASE = new EntidadPropietaria("Subterráneos de Buenos Aires Sociedad del Estado","Subterráneos de Buenos Aires Sociedad del Estado (SBASE) es la empresa de la Ciudad Autónoma de Buenos Aires que tiene a su cargo la administración de la red de subtes, su desarrollo, expansión y el control de la operación del servicio. Además, es la autoridad de aplicación de la Ley 4472.",larretaSubte,gobCiudad);

    //PROVINCIAS Y LOCALIDADES
    Provincia bsas = GeoRefAPIService.getInstancia().listadoDeProvincias().provinciaDeId(6).get();
    Provincia caba = GeoRefAPIService.getInstancia().listadoDeProvincias().provinciaDeId(2).get();

    Localidad flores = GeoRefAPIService.getInstancia().localidadPorNombreYProv("Flores",caba.id).localidades.get(0);
    Localidad caballito = GeoRefAPIService.getInstancia().localidadPorNombreYProv("Caballito",caba.id).localidades.get(0);
    Localidad monserrat = GeoRefAPIService.getInstancia().localidadPorNombreYProv("Monserrat",caba.id).localidades.get(0);
    Localidad sanTelmo = GeoRefAPIService.getInstancia().localidadPorNombreYProv("San Telmo",caba.id).localidades.get(0);
    Localidad sanNicolas = GeoRefAPIService.getInstancia().localidadPorNombreYProv("San Nicolas",caba.id).localidades.get(0);
    Localidad almagro = GeoRefAPIService.getInstancia().localidadPorNombreYProv("Almagro",caba.id).localidades.get(0);


    //ENTIDADES
    Entidad lineaA = new Entidad("Linea A de subte","La Línea A es una de las seis líneas del Subte de Buenos Aires, abierta al público el 1° de diciembre de 1913, convirtiéndose así en la primera de toda América Latina, el hemisferio sur y todos los países de habla hispana. Se extiende a lo largo de 9,7 kilómetros entre Plaza de Mayo en el barrio porteño de Monserrat y San Pedrito en el barrio porteño de Flores. Circula por debajo de la totalidad de la Avenida de Mayo y parte de la Avenida Rivadavia, siendo utilizada por alrededor de 250.000 personas al día.",SBASE,bsas);
    Entidad lineaB = new Entidad("Linea B de subte", "La línea B del subte de Buenos Aires, abierta al público el 17 de octubre de 1930, se extiende a lo largo de 11,8 km entre Leandro N. Alem y Juan Manuel de Rosas. Esta fue la última estación inaugurada de la línea, en julio de 2013, junto a la Echeverría.", SBASE,bsas);
    //ESTABLECIMIENTOS
    //linea A
    Establecimiento estacionSanPedrito = new Establecimiento("San Pedrito","San Pedrito es la estación terminal oeste de la línea A del Subte de Buenos Aires.",flores,null,lineaA);
    Establecimiento estacionSanJoseDeFlores = new Establecimiento("San jose de flores","San José de Flores es una estación de la línea A del Subte de Buenos Aires. Debido a su cercanía con la estación Flores de la línea Sarmiento, es un importante nodo de combinación.",flores,null,lineaA);
    Establecimiento estacionCarabobo = new Establecimiento("Carabobo","Carabobo es una estación de la línea A del Subte de Buenos Aires.",flores,null,lineaA);
    Establecimiento estacionPuan = new Establecimiento("Puán", "Puán es la estación terminal oeste de la línea A del Subte de Buenos Aires.", flores, null, lineaA);
    Establecimiento estacionPrimeraJunta = new Establecimiento("Primera Junta", "Primera Junta es la estación terminal oeste de la línea A del Subte de Buenos Aires.", caballito, null, lineaA);
    Establecimiento estacionAcoyte = new Establecimiento("Acoyte", "Acoyte es la estación terminal oeste de la línea A del Subte de Buenos Aires.", caballito, null, lineaA);
    Establecimiento estacionRioDeJaneiro = new Establecimiento("Rio de Janeiro", "Rio de Janeiro es la estación terminal oeste de la línea A del Subte de Buenos Aires.", flores, null, lineaA);
    Establecimiento estacionCastroBarros = new Establecimiento("Castro Barros", "Castro Barros es la estación terminal oeste de la línea A del Subte de Buenos Aires.", flores, null, lineaA);
    Establecimiento estacionLoria = new Establecimiento("Loria", "Loria es la estación terminal oeste de la línea A del Subte de Buenos Aires.", flores, null, lineaA);
    Establecimiento estacionPlazaMiserere = new Establecimiento("Plaza Miserere", "Plaza Miserere es la estación terminal oeste de la línea A del Subte de Buenos Aires.", flores, null, lineaA);
    Establecimiento estacionAlberti = new Establecimiento("Alberti", "Alberti es la estación terminal oeste de la línea A del Subte de Buenos Aires.", flores, null, lineaA);
    Establecimiento estacionPasco = new Establecimiento("Pasco", "Pasco es la estación terminal oeste de la línea A del Subte de Buenos Aires.", flores, null, lineaA);
    Establecimiento estacionCongreso = new Establecimiento("Congreso", "Congreso es la estación terminal oeste de la línea A del Subte de Buenos Aires.", flores, null, lineaA);
    Establecimiento estacionSaenzPena = new Establecimiento("Sáenz Peña", "Sáenz Peña es la estación terminal oeste de la línea A del Subte de Buenos Aires.", flores, null, lineaA);
    Establecimiento estacionLima = new Establecimiento("Lima", "Lima es la estación terminal oeste de la línea A del Subte de Buenos Aires.", monserrat, null, lineaA);
    Establecimiento estacionPiedras = new Establecimiento("Piedras", "Piedras es la estación terminal oeste de la línea A del Subte de Buenos Aires.", sanTelmo, null, lineaA);
    Establecimiento estacionPeru = new Establecimiento("Perú", "Perú es la estación terminal oeste de la línea A del Subte de Buenos Aires.", sanTelmo, null, lineaA);
    Establecimiento estacionPlazaDeMayo = new Establecimiento("Plaza de Mayo", "Plaza de Mayo es la estación terminal oeste de la línea A del Subte de Buenos Aires.", monserrat, null, lineaA);

    //linea B
    // Crear establecimientos para la línea B
    Establecimiento estacionLeandroNAlem = new Establecimiento("Leandro N. Alem", "Leandro N. Alem es una estación de la línea B del Subte de Buenos Aires.", sanNicolas, null, lineaB);
    Establecimiento estacionFlorida = new Establecimiento("Florida", "Florida es una estación de la línea B del Subte de Buenos Aires.", sanNicolas, null, lineaB);
    Establecimiento estacionCarlosPellegrini = new Establecimiento("Carlos Pellegrini", "Carlos Pellegrini es una estación de la línea B del Subte de Buenos Aires.", sanNicolas, null, lineaB);
    Establecimiento estacionUruguay = new Establecimiento("Uruguay", "Uruguay es una estación de la línea B del Subte de Buenos Aires.", almagro, null, lineaB);
    Establecimiento estacionCallao = new Establecimiento("Callao", "Callao es una estación de la línea B del Subte de Buenos Aires.", almagro, null, lineaB);
    Establecimiento estacionPasteur = new Establecimiento("Pasteur", "Pasteur es una estación de la línea B del Subte de Buenos Aires.", almagro, null, lineaB);
    Establecimiento estacionPueyrredon = new Establecimiento("Pueyrredón", "Pueyrredón es una estación de la línea B del Subte de Buenos Aires.", almagro, null, lineaB);
    Establecimiento estacionCarlosGardel = new Establecimiento("Carlos Gardel", "Carlos Gardel es una estación de la línea B del Subte de Buenos Aires.", almagro, null, lineaB);
    Establecimiento estacionMedrano = new Establecimiento("Medrano", "Medrano es una estación de la línea B del Subte de Buenos Aires.", flores, null, lineaB);
    Establecimiento estacionAngelGallardo = new Establecimiento("Ángel Gallardo", "Ángel Gallardo es una estación de la línea B del Subte de Buenos Aires.", flores, null, lineaB);
    Establecimiento estacionMalabia = new Establecimiento("Malabia", "Malabia es una estación de la línea B del Subte de Buenos Aires.", almagro, null, lineaB);
    Establecimiento estacionDorregoFCGSM = new Establecimiento("DorregoFCGSM", "DorregoFCGSM es una estación de la línea B del Subte de Buenos Aires.", flores, null, lineaB);
    Establecimiento estacionFedericoLacroze = new Establecimiento("Federico Lacroze", "Federico Lacroze es una estación de la línea B del Subte de Buenos Aires.", almagro, null, lineaB);
    Establecimiento estacionTronadorVillaOrtuzar = new Establecimiento("Tronador - Villa Ortúzar", "Tronador - Villa Ortúzar es una estación de la línea B del Subte de Buenos Aires.", flores, null, lineaB);
    Establecimiento estacionDeLosIncasParqueChas = new Establecimiento("De los Incas - Parque Chas", "De los Incas - Parque Chas es una estación de la línea B del Subte de Buenos Aires.", flores, null, lineaB);
    Establecimiento estacionEcheverria = new Establecimiento("Echeverría", "Echeverría es una estación de la línea B del Subte de Buenos Aires.", almagro, null, lineaB);
    Establecimiento estacionJuanManuelDeRosasFCGBM = new Establecimiento("Juan Manuel de RosasFCGBM", "Juan Manuel de RosasFCGBM es una estación de la línea B del Subte de Buenos Aires.", almagro, null, lineaB);

    //Servicios
    // Crear servicios de baños para cada estación de la línea A
    Servicio servicioBaniosCarabobo = new Servicio("Baños de la Estacion", "Baños disponibles en la estacion Carabobo",estacionCarabobo);
    Servicio servicioBaniosSanPedrito = new Servicio("Baños de la Estacion", "Baños disponibles en la estacion San Pedrito",estacionSanPedrito);
    Servicio servicioBaniosSanJoseDeFlores = new Servicio("Baños de la Estacion","Baños disponibles en la estacion San Jose de Flores",estacionSanJoseDeFlores);
    Servicio servicioBaniosPuán = new Servicio("Baños de la Estación", "Baños disponibles en la estación Puán.", estacionPuan);
    Servicio servicioBaniosPrimeraJunta = new Servicio("Baños de la Estación", "Baños disponibles en la estación Primera Junta.", estacionPrimeraJunta);
    Servicio servicioBaniosAcoyte = new Servicio("Baños de la Estación", "Baños disponibles en la estación Acoyte.", estacionAcoyte);
    Servicio servicioBaniosRioDeJaneiro = new Servicio("Baños de la Estación", "Baños disponibles en la estación Rio de Janeiro.", estacionRioDeJaneiro);
    Servicio servicioBaniosCastroBarros = new Servicio("Baños de la Estación", "Baños disponibles en la estación Castro Barros.", estacionCastroBarros);
    Servicio servicioBaniosLoria = new Servicio("Baños de la Estación", "Baños disponibles en la estación Loria.", estacionLoria);
    Servicio servicioBaniosPlazaMiserere = new Servicio("Baños de la Estación", "Baños disponibles en la estación Plaza Miserere.", estacionPlazaMiserere);
    Servicio servicioBaniosAlberti = new Servicio("Baños de la Estación", "Baños disponibles en la estación Alberti.", estacionAlberti);
    Servicio servicioBaniosPasco = new Servicio("Baños de la Estación", "Baños disponibles en la estación Pasco.", estacionPasco);
    Servicio servicioBaniosCongreso = new Servicio("Baños de la Estación", "Baños disponibles en la estación Congreso.", estacionCongreso);
    Servicio servicioBaniosSaenzPena = new Servicio("Baños de la Estación", "Baños disponibles en la estación Sáenz Peña.", estacionSaenzPena);
    Servicio servicioBaniosLima = new Servicio("Baños de la Estación", "Baños disponibles en la estación Lima.", estacionLima);
    Servicio servicioBaniosPiedras = new Servicio("Baños de la Estación", "Baños disponibles en la estación Piedras.", estacionPiedras);
    Servicio servicioBaniosPeru = new Servicio("Baños de la Estación", "Baños disponibles en la estación Perú.", estacionPeru);
    Servicio servicioBaniosPlazaDeMayo = new Servicio("Baños de la Estación", "Baños disponibles en la estación Plaza de Mayo.", estacionPlazaDeMayo);

    Servicio servicioEscaleraPuan = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Puán.", estacionPuan);
    Servicio servicioEscaleraPrimeraJunta = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Primera Junta.", estacionPrimeraJunta);
    Servicio servicioEscaleraAcoyte = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Acoyte.", estacionAcoyte);
    Servicio servicioEscaleraCarabobo = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Carabobo.", estacionCarabobo);
    Servicio servicioEscaleraRioDeJaneiro = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Río de Janeiro.", estacionRioDeJaneiro);
    Servicio servicioEscaleraCastroBarros = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Castro Barros.", estacionCastroBarros);
    Servicio servicioEscaleraLoria = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Loria.", estacionLoria);
    Servicio servicioEscaleraPlazaMiserere = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Plaza Miserere.", estacionPlazaMiserere);
    Servicio servicioEscaleraAlberti = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Alberti.", estacionAlberti);
    Servicio servicioEscaleraPasco = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Pasco.", estacionPasco);
    Servicio servicioEscaleraCongreso = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Congreso.", estacionCongreso);
    Servicio servicioEscaleraSaenzPena = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Sáenz Peña.", estacionSaenzPena);
    Servicio servicioEscaleraLima = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Lima.", estacionLima);
    Servicio servicioEscaleraPiedras = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Piedras.", estacionPiedras);
    Servicio servicioEscaleraPeru = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Perú.", estacionPeru);
    Servicio servicioEscaleraPlazaDeMayo = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Plaza de Mayo.", estacionPlazaDeMayo);


    // Crear servicios de baños para cada estación de la línea B
    Servicio servicioBaniosLeandroNAlem = new Servicio("Baños de la Estación", "Baños disponibles en la estación Leandro N. Alem.", estacionLeandroNAlem);
    Servicio servicioBaniosFlorida = new Servicio("Baños de la Estación", "Baños disponibles en la estación Florida.", estacionFlorida);
    Servicio servicioBaniosCarlosPellegrini = new Servicio("Baños de la Estación", "Baños disponibles en la estación Carlos Pellegrini.", estacionCarlosPellegrini);
    Servicio servicioBaniosUruguay = new Servicio("Baños de la Estación", "Baños disponibles en la estación Uruguay.", estacionUruguay);
    Servicio servicioBaniosCallao = new Servicio("Baños de la Estación", "Baños disponibles en la estación Callao.", estacionCallao);
    Servicio servicioBaniosPasteur = new Servicio("Baños de la Estación", "Baños disponibles en la estación Pasteur.", estacionPasteur);
    Servicio servicioBaniosPueyrredon = new Servicio("Baños de la Estación", "Baños disponibles en la estación Pueyrredón.", estacionPueyrredon);
    Servicio servicioBaniosCarlosGardel = new Servicio("Baños de la Estación", "Baños disponibles en la estación Carlos Gardel.", estacionCarlosGardel);
    Servicio servicioBaniosMedrano = new Servicio("Baños de la Estación", "Baños disponibles en la estación Medrano.", estacionMedrano);
    Servicio servicioBaniosAngelGallardo = new Servicio("Baños de la Estación", "Baños disponibles en la estación Ángel Gallardo.", estacionAngelGallardo);
    Servicio servicioBaniosMalabia = new Servicio("Baños de la Estación", "Baños disponibles en la estación Malabia.", estacionMalabia);
    Servicio servicioBaniosDorregoFCGSM = new Servicio("Baños de la Estación", "Baños disponibles en la estación DorregoFCGSM.", estacionDorregoFCGSM);
    Servicio servicioBaniosFedericoLacroze = new Servicio("Baños de la Estación", "Baños disponibles en la estación Federico Lacroze.", estacionFedericoLacroze);
    Servicio servicioBaniosTronadorVillaOrtuzar = new Servicio("Baños de la Estación", "Baños disponibles en la estación Tronador - Villa Ortúzar.", estacionTronadorVillaOrtuzar);
    Servicio servicioBaniosDeLosIncasParqueChas = new Servicio("Baños de la Estación", "Baños disponibles en la estación De los Incas - Parque Chas.", estacionDeLosIncasParqueChas);
    Servicio servicioBaniosEcheverria = new Servicio("Baños de la Estación", "Baños disponibles en la estación Echeverría.", estacionEcheverria);
    Servicio servicioBaniosJuanManuelDeRosasFCGBM = new Servicio("Baños de la Estación", "Baños disponibles en la estación Juan Manuel de RosasFCGBM.", estacionJuanManuelDeRosasFCGBM);

    Servicio servicioEscaleraLeandroNAlem = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Leandro N. Alem.", estacionLeandroNAlem);
    Servicio servicioEscaleraFlorida = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Florida.", estacionFlorida);
    Servicio servicioEscaleraCarlosPellegrini = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Carlos Pellegrini.", estacionCarlosPellegrini);
    Servicio servicioEscaleraUruguay = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Uruguay.", estacionUruguay);
    Servicio servicioEscaleraCallao = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Callao.", estacionCallao);
    Servicio servicioEscaleraPueyrredon = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Pueyrredón.", estacionPueyrredon);
    Servicio servicioEscaleraCarlosGardel = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Carlos Gardel.", estacionCarlosGardel);
    Servicio servicioEscaleraMedrano = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Medrano.", estacionMedrano);
    Servicio servicioEscaleraAngelGallardo = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Ángel Gallardo.", estacionAngelGallardo);
    Servicio servicioEscaleraFedericoLacroze = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Federico Lacroze.", estacionFedericoLacroze);
    Servicio servicioEscaleraTronadorVillaOrtuzar = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Tronador - Villa Ortúzar.", estacionTronadorVillaOrtuzar);
    Servicio servicioEscaleraDeLosIncasParqueChas = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en De los Incas - Parque Chas.", estacionDeLosIncasParqueChas);
    Servicio servicioEscaleraEcheverria = new Servicio("Escalera Mecánica", "Escalera mecánica disponible en Echeverría.", estacionEcheverria);




    //Comunidades
    Comunidad comunidadCaballito = new Comunidad("Comunidad de Caballito");
    comunidadCaballito.agregarServicioDeInteres(servicioBaniosAcoyte);
    comunidadCaballito.agregarServicioDeInteres(servicioBaniosPrimeraJunta);
    Comunidad comunidadAlmagro = new Comunidad("Comunidad de Almagro");
    comunidadAlmagro.agregarServicioDeInteres(servicioEscaleraUruguay);
    comunidadAlmagro.agregarServicioDeInteres(servicioEscaleraCallao);

    MedioNotificacionesEmail medioPreferido = new MedioNotificacionesEmail("juanro@gmail.com");
    SinApuros config = new SinApuros(medioPreferido);
    config.agregarHorario(LocalTime.of(20, 30, 0));
    config.agregarHorario(LocalTime.of(7, 45, 0));
    config.agregarHorario(LocalTime.of(14, 0, 0));

    // === Personas ===
    Usuario usuarioJuan = new Usuario("juanro1259", "123", Rol.NORMAL);
    Persona juan = new Persona("Juan", "Rodriguez", usuarioJuan, config);
    juan.setUbicacion(flores);
    juan.agregarEntidadDeInteres(lineaA);
    juan.agregarServicioDeInteres(servicioBaniosCarabobo);
    juan.darseAltaComunidadCreada(comunidadCaballito);

    MedioNotificacionesWhatsapp medioPreferidoJose = new MedioNotificacionesWhatsapp("123123123");
    CuandoSuceden configJose = new CuandoSuceden(medioPreferidoJose);
    Usuario usuarioJose = new Usuario("josero1259", "123", Rol.NORMAL);
    Persona jose = new Persona("Jose", "Rodriguez", usuarioJose, configJose);
    jose.setUbicacion(almagro);
    jose.agregarEntidadDeInteres(lineaB);
    jose.agregarServicioDeInteres(servicioEscaleraLeandroNAlem);
    jose.darseAltaComunidadCreada(comunidadAlmagro);

    Incidente incidente1 = new Incidente("Incidente 1", "Los baños están fuera de servicio", juan, servicioBaniosPrimeraJunta, comunidadCaballito);
    Incidente incidente2 = new Incidente("Incidente 2", "Los baños no están disponibles por mantenimiento", juan, servicioBaniosPrimeraJunta, comunidadCaballito);
    Incidente incidente3 = new Incidente("Incidente 1", "Las escaleras mecanicas no están funcionando", jose, servicioEscaleraCallao, comunidadAlmagro);

    comunidadCaballito.informarIncidenteResuelto(incidente1);
    comunidadAlmagro.informarIncidenteResuelto(incidente3);


    // ------- PERSISTENCIA -------

    EntityManagerHelper.beginTransaction();

    EntityManagerHelper.persist(larretaSubte);
    EntityManagerHelper.persist(gobCiudad);


    EntityManagerHelper.persist(lineaA);
    EntityManagerHelper.persist(SBASE);

    //Linea A
    EntityManagerHelper.persist(estacionCarabobo);
    EntityManagerHelper.persist(estacionSanPedrito);
    EntityManagerHelper.persist(estacionSanJoseDeFlores);
    EntityManagerHelper.persist(estacionPuan);
    EntityManagerHelper.persist(estacionPrimeraJunta);
    EntityManagerHelper.persist(estacionAcoyte);
    EntityManagerHelper.persist(estacionRioDeJaneiro);
    EntityManagerHelper.persist(estacionCastroBarros);
    EntityManagerHelper.persist(estacionLoria);
    EntityManagerHelper.persist(estacionPlazaMiserere);
    EntityManagerHelper.persist(estacionAlberti);
    EntityManagerHelper.persist(estacionPasco);
    EntityManagerHelper.persist(estacionCongreso);
    EntityManagerHelper.persist(estacionSaenzPena);
    EntityManagerHelper.persist(estacionLima);
    EntityManagerHelper.persist(estacionPiedras);
    EntityManagerHelper.persist(estacionPeru);
    EntityManagerHelper.persist(estacionPlazaDeMayo);

    //linea b
    // Persistir cada estación de la línea B individualmente
    EntityManagerHelper.persist(estacionLeandroNAlem);
    EntityManagerHelper.persist(estacionFlorida);
    EntityManagerHelper.persist(estacionCarlosPellegrini);
    EntityManagerHelper.persist(estacionUruguay);
    EntityManagerHelper.persist(estacionCallao);
    EntityManagerHelper.persist(estacionPasteur);
    EntityManagerHelper.persist(estacionPueyrredon);
    EntityManagerHelper.persist(estacionCarlosGardel);
    EntityManagerHelper.persist(estacionMedrano);
    EntityManagerHelper.persist(estacionAngelGallardo);
    EntityManagerHelper.persist(estacionMalabia);
    EntityManagerHelper.persist(estacionDorregoFCGSM);
    EntityManagerHelper.persist(estacionFedericoLacroze);
    EntityManagerHelper.persist(estacionTronadorVillaOrtuzar);
    EntityManagerHelper.persist(estacionDeLosIncasParqueChas);
    EntityManagerHelper.persist(estacionEcheverria);
    EntityManagerHelper.persist(estacionJuanManuelDeRosasFCGBM);

    // Persistir servicios de baños de la línea A
    EntityManagerHelper.persist(servicioBaniosCarabobo);
    EntityManagerHelper.persist(servicioBaniosSanPedrito);
    EntityManagerHelper.persist(servicioBaniosSanJoseDeFlores);
    EntityManagerHelper.persist(servicioBaniosPuán);
    EntityManagerHelper.persist(servicioBaniosPrimeraJunta);
    EntityManagerHelper.persist(servicioBaniosAcoyte);
    EntityManagerHelper.persist(servicioBaniosRioDeJaneiro);
    EntityManagerHelper.persist(servicioBaniosCastroBarros);
    EntityManagerHelper.persist(servicioBaniosLoria);
    EntityManagerHelper.persist(servicioBaniosPlazaMiserere);
    EntityManagerHelper.persist(servicioBaniosAlberti);
    EntityManagerHelper.persist(servicioBaniosPasco);
    EntityManagerHelper.persist(servicioBaniosCongreso);
    EntityManagerHelper.persist(servicioBaniosSaenzPena);
    EntityManagerHelper.persist(servicioBaniosLima);
    EntityManagerHelper.persist(servicioBaniosPiedras);
    EntityManagerHelper.persist(servicioBaniosPeru);
    EntityManagerHelper.persist(servicioBaniosPlazaDeMayo);

    // Persistir servicios de escalera mecánica para la Línea A
    EntityManagerHelper.persist(servicioEscaleraPuan);
    EntityManagerHelper.persist(servicioEscaleraPrimeraJunta);
    EntityManagerHelper.persist(servicioEscaleraAcoyte);
    EntityManagerHelper.persist(servicioEscaleraCarabobo);
    EntityManagerHelper.persist(servicioEscaleraRioDeJaneiro);
    EntityManagerHelper.persist(servicioEscaleraCastroBarros);
    EntityManagerHelper.persist(servicioEscaleraLoria);
    EntityManagerHelper.persist(servicioEscaleraPlazaMiserere);
    EntityManagerHelper.persist(servicioEscaleraAlberti);
    EntityManagerHelper.persist(servicioEscaleraPasco);
    EntityManagerHelper.persist(servicioEscaleraCongreso);
    EntityManagerHelper.persist(servicioEscaleraSaenzPena);
    EntityManagerHelper.persist(servicioEscaleraLima);
    EntityManagerHelper.persist(servicioEscaleraPiedras);
    EntityManagerHelper.persist(servicioEscaleraPeru);
    EntityManagerHelper.persist(servicioEscaleraPlazaDeMayo);

    // Persistir servicios de baños de la línea B
    EntityManagerHelper.persist(servicioBaniosLeandroNAlem);
    EntityManagerHelper.persist(servicioBaniosFlorida);
    EntityManagerHelper.persist(servicioBaniosCarlosPellegrini);
    EntityManagerHelper.persist(servicioBaniosUruguay);
    EntityManagerHelper.persist(servicioBaniosCallao);
    EntityManagerHelper.persist(servicioBaniosPasteur);
    EntityManagerHelper.persist(servicioBaniosPueyrredon);
    EntityManagerHelper.persist(servicioBaniosCarlosGardel);
    EntityManagerHelper.persist(servicioBaniosMedrano);
    EntityManagerHelper.persist(servicioBaniosAngelGallardo);
    EntityManagerHelper.persist(servicioBaniosMalabia);
    EntityManagerHelper.persist(servicioBaniosDorregoFCGSM);
    EntityManagerHelper.persist(servicioBaniosFedericoLacroze);
    EntityManagerHelper.persist(servicioBaniosTronadorVillaOrtuzar);
    EntityManagerHelper.persist(servicioBaniosDeLosIncasParqueChas);
    EntityManagerHelper.persist(servicioBaniosEcheverria);
    EntityManagerHelper.persist(servicioBaniosJuanManuelDeRosasFCGBM);

    // Persistir servicios de escalera mecánica para la Línea B
    EntityManagerHelper.persist(servicioEscaleraLeandroNAlem);
    EntityManagerHelper.persist(servicioEscaleraFlorida);
    EntityManagerHelper.persist(servicioEscaleraCarlosPellegrini);
    EntityManagerHelper.persist(servicioEscaleraUruguay);
    EntityManagerHelper.persist(servicioEscaleraCallao);
    EntityManagerHelper.persist(servicioEscaleraPueyrredon);
    EntityManagerHelper.persist(servicioEscaleraCarlosGardel);
    EntityManagerHelper.persist(servicioEscaleraMedrano);
    EntityManagerHelper.persist(servicioEscaleraAngelGallardo);
    EntityManagerHelper.persist(servicioEscaleraFedericoLacroze);
    EntityManagerHelper.persist(servicioEscaleraTronadorVillaOrtuzar);
    EntityManagerHelper.persist(servicioEscaleraDeLosIncasParqueChas);
    EntityManagerHelper.persist(servicioEscaleraEcheverria);

    // Comunidad
    EntityManagerHelper.persist(comunidadCaballito);

    // Persona
    EntityManagerHelper.persist(juan);
    EntityManagerHelper.persist(jose);

    EntityManagerHelper.persist(incidente1);
    EntityManagerHelper.persist(incidente2);
    EntityManagerHelper.persist(incidente3);

    EntityManagerHelper.commit();
    EntityManagerHelper.closeEntityManager();
  }
}
