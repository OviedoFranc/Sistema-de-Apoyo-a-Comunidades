package ar.edu.utn.frba.dds.Persistencia.converters;

import javax.persistence.AttributeConverter;
import java.time.LocalTime;
import java.util.TreeSet;

public class HorariosDeNotificacionAttributeConverter implements AttributeConverter<TreeSet<LocalTime>, String> {
  @Override
  public String convertToDatabaseColumn(TreeSet<LocalTime> treeSet) {
    StringBuilder stringHorarios = new StringBuilder();
    for (LocalTime horario:treeSet) {
      stringHorarios.append(horario.toString()).append(",");
    }
    return stringHorarios.toString();
  }

  @Override
  public TreeSet<LocalTime> convertToEntityAttribute(String s) {
    TreeSet<LocalTime> treeSet = new TreeSet<LocalTime>();
    String[] stringArray = s.split(",", 2);
    boolean terminoElParseo = false;
    while (!terminoElParseo) {
      if (!stringArray[0].equals("")) treeSet.add(LocalTime.parse(stringArray[0]));
      if (stringArray.length > 1) {
        stringArray = stringArray[1].split(",", 2);
      } else {
        terminoElParseo = true;
      }
    }
    return treeSet;
  }
}
