package ar.edu.utn.frba.dds.Seguridad.Filtros;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ControlPasswordDebil implements FiltroInterface {
  private static String commonPasswordsPath;

  public ControlPasswordDebil() {
    commonPasswordsPath = "src/main/java/ar/edu/utn/frba/dds/Seguridad/Filtros/common-passwords.txt";
  }

  @Override
  public Boolean validar(String password) {
    return !EsComun(password);
  }

  public boolean EsComun(String password){
    try{
      InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("public/files/common-passwords.txt");
      BufferedReader buff = new BufferedReader(new InputStreamReader(inputStream));
      String s;
      while((s=buff.readLine())!=null){
        if(s.trim().contains(password)){
          return true;
        }
      }
      buff.close();
    }catch(Exception e){e.printStackTrace();}
    return false;
  }
}
