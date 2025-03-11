package ar.edu.utn.frba.dds.Servicio.Comunicacion;

import java.util.Map;

public class MailService {

  static private String EMAIL_SENDER_FROM;

  //private final Gmail service;

  public MailService() throws Exception {
    Map<String, String> env = System.getenv();
    String enviromentEmailSenderFrom = "EMAIL.SENDER.FROM";
      if(!env.containsKey("EMAIL.SENDER.FROM")){
        throw new IllegalStateException("Faltan propiedades de WhatsApp. Asegúrate de configurar las siguientes variables de entorno: "+ "EMAIL_SENDER_FROM");
      }
        EMAIL_SENDER_FROM = env.get(enviromentEmailSenderFrom);
    /*
    NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    service = new Gmail.Builder(httpTransport, jsonFactory, getCredentials(httpTransport, jsonFactory))
        .setApplicationName("Sistema de Apoyo a Comunidades con Movilidad Reducida G15")
     .build();
  */
  }
  public void sendMail(String toMail, String subject, String mensaje) throws Exception {
  /*
    // Encode as MIME message
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);
    MimeMessage email = new MimeMessage(session);
    email.setFrom(new InternetAddress(EMAIL_SENDER_FROM));
    email.addRecipient(javax.mail.Message.RecipientType.TO,
        new InternetAddress(toMail));
    email.setSubject(subject);
    email.setText(mensaje);

    // Encode and wrap the MIME message into a gmail message
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    email.writeTo(buffer);
    byte[] rawMessageBytes = buffer.toByteArray();
    String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
    Message message = new Message();
    message.setRaw(encodedEmail);

    try {
      // Create send message
      message = service.users().messages().send("me", message).execute();
      System.out.println("Message id: " + message.getId());
      System.out.println(message.toPrettyString());
    } catch (GoogleJsonResponseException e) {
      GoogleJsonError error = e.getDetails();
      if (error.getCode() == 403) {
        System.err.println("Unable to send message: " + e.getDetails());
      } else {
        throw e;
      }
    }*/

  }
  /*
  private static Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory)
     throws IOException {
    // Load client secrets.

    InputStream in = MailService.class.getResourceAsStream("");
    if (in == null) {
      throw new FileNotFoundException("Resource Credentials not found");
    }
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));


    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        httpTransport, jsonFactory, clientSecrets, Set.of(GMAIL_SEND))
        .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
        .setAccessType("offline")
        .build();

    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    //returns an authorized Credential object.
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }
  */
}


