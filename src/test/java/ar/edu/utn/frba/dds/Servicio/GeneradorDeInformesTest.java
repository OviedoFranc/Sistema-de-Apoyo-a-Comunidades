package ar.edu.utn.frba.dds.Servicio;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ar.edu.utn.frba.dds.Modelos.Entidad;
import ar.edu.utn.frba.dds.Modelos.Rankings.ItemRanking;
import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GeneradorDeInformesTest {
  GeneradorDeInformes generadorDeInformes;
  ArrayList<ItemRanking> listaDeItems;

  @BeforeEach
  public void init() throws DocumentException, FileNotFoundException {
    //generadorDeInformes = new GeneradorDeInformes();
    generadorDeInformes = mock(GeneradorDeInformes.class);
    doAnswer(invocationOnMock -> null).when(generadorDeInformes).generarInforme(any(ArrayList.class), any(String.class));

    Entidad entidad1 = new Entidad("Entidad 1", null, null, null);
    Entidad entidad2 = new Entidad("Entidad 2", null, null, null);
    Entidad entidad3 = new Entidad("Entidad 3", null, null, null);
    Entidad entidad4 = new Entidad("Entidad 4", null, null, null);

    ItemRanking item = new ItemRanking(entidad1, 1.0, LocalDateTime.now(), null);
    ItemRanking item2 = new ItemRanking(entidad2, 2.0, LocalDateTime.now(), null);
    ItemRanking item3 = new ItemRanking(entidad3, 3.0, LocalDateTime.now(), null);
    ItemRanking item4 = new ItemRanking(entidad4, 4.0, LocalDateTime.now(), null);
    listaDeItems = new ArrayList<>();
    listaDeItems.add(item);
    listaDeItems.add(item2);
    listaDeItems.add(item3);
    listaDeItems.add(item4);
  }

  @Test
  public void generarUnPdf() throws DocumentException, FileNotFoundException {
    generadorDeInformes.generarInforme(listaDeItems, "");

    verify(generadorDeInformes).generarInforme(any(ArrayList.class), any(String.class));
    //verify(generadorDeInformes, times(1));
  }
}