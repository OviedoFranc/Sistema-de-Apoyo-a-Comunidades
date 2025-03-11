package ar.edu.utn.frba.dds.Servicio.Comunicacion;
import com.twilio.Twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.Map;


public class WhatsAppService {

  private String ACCOUNT_SID_WHATSAPP;
  private String AUTH_TOKEN_WHATSAPP;
  private String PHONE_NUMBER_FROM_WHATSAPP;

  public WhatsAppService() throws Exception{
    Map<String, String> env = System.getenv();
      if (!env.containsKey("ACCOUNT_SID_WHATSAPP") ||
          !env.containsKey("AUTH_TOKEN_WHATSAPP") ||
          !env.containsKey("PHONE_NUMBER_FROM_WHATSAPP")) {
        throw new IllegalStateException(
            "Faltan propiedades de WhatsApp. Asegúrate de configurar las siguientes variables de entorno: " +
                "ACCOUNT_SID_WHATSAPP, AUTH_TOKEN_WHATSAPP, PHONE_NUMBER_FROM_WHATSAPP"
        );
      }
      ACCOUNT_SID_WHATSAPP = env.get("ACCOUNT_SID_WHATSAPP");
      AUTH_TOKEN_WHATSAPP = env.get("AUTH_TOKEN_WHATSAPP");
      PHONE_NUMBER_FROM_WHATSAPP = env.get("PHONE_NUMBER_FROM_WHATSAPP");
  }


  public void SendWhatsapp(String mensaje, String numero){
      Twilio.init(ACCOUNT_SID_WHATSAPP, AUTH_TOKEN_WHATSAPP);
      Message message = Message
          .creator(
              new PhoneNumber("whatsapp:+549" + numero),
              new PhoneNumber("whatsapp:+" + PHONE_NUMBER_FROM_WHATSAPP),
              mensaje
          )
          .create();

      System.out.println(message.getSid());
  }
}
