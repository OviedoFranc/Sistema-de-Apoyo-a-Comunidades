package ar.edu.utn.frba.dds.Servicio.SendersTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ar.edu.utn.frba.dds.Servicio.Comunicacion.SenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SenderServiceTest {
  SenderService senderService = null;

  @BeforeEach
  public void init() throws Exception {
    senderService = mock(SenderService.class);

    doAnswer(invocationOnMock -> null).when(senderService).email(anyString(), anyString(), anyString());
    doAnswer(invocationOnMock -> null).when(senderService).whatsApp(anyString(), anyString());
  }

  @Test
  public void testWpMail() throws Exception{
    senderService.email("oviedofranc@gmail.com", "Test encabezado", "Test Mensaje");
    senderService.whatsApp("Test Mensaje", "1126068543");

    verify(senderService, times(1)).email(anyString(), anyString(), anyString());
    verify(senderService, times(1)).whatsApp(anyString(), anyString());
    }
}
