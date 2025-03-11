package ar.edu.utn.frba.dds.Servicio;

import ar.edu.utn.frba.dds.Modelos.Rankings.ItemRanking;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import net.bytebuddy.asm.Advice;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

public class GeneradorDeInformes {

  public void generarInforme(ArrayList<ItemRanking> items, String title) throws FileNotFoundException, DocumentException {
    Document document = new Document();

    File file = new File(title+".pdf");
    if (file.exists()){
      file.delete();
    }
    System.out.println(file.getAbsolutePath()+"HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    PdfWriter.getInstance(document, new FileOutputStream(file));
        //PdfWriter.getInstance(document, new FileOutputStream("public/files/"+ title + ".pdf-"));

    document.open();
    document.newPage();
    document.addTitle(title);
    PdfPTable table = new PdfPTable(2);
    addTableHeader(table);

    for (ItemRanking itemRanking:items) {
      addRows(table, itemRanking.getEntidad().getNombre(), itemRanking.getValorParametro().toString());
    }
    document.add(table);
    document.close();
  }

  private void addTableHeader(PdfPTable table) {
    Stream.of("ENTIDAD", "VALOR")
        .forEach(columnTitle -> {
          PdfPCell header = new PdfPCell();
          header.setBackgroundColor(BaseColor.LIGHT_GRAY);
          header.setBorderWidth(2);
          header.setPhrase(new Phrase(columnTitle));
          table.addCell(header);
        });
  }

  private void addRows(PdfPTable table, String nombreEntidad, String valor) {
    table.addCell(nombreEntidad);
    table.addCell(valor);
  }
}
