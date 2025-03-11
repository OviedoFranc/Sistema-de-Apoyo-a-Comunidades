package ar.edu.utn.frba.dds.Seguridad;

import ar.edu.utn.frba.dds.Seguridad.Filtros.FiltroInterface;
import java.util.ArrayList;
import java.util.List;

public class ValidadorPassword {

  public ValidadorPassword() {
    this.filtros = filtros;
  }

  private List<FiltroInterface> filtros = new ArrayList<>();

  public Boolean validarPassword(String password) {
    return this.filtros.stream().allMatch(filtro -> filtro.validar(password));
  }

  public void addFiltro(FiltroInterface filtro) {
    filtros.add(filtro);
  }
}
