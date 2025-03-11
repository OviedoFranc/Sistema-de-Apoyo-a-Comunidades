package ar.edu.utn.frba.dds.Server.Utils;

import com.opencsv.exceptions.CsvValidationException;
import io.javalin.http.Context;
import java.io.IOException;

public interface ICrudViewsHandler {
    void index(Context context);
    void show(Context context);
    void create(Context context);
    void save(Context context) throws IOException, CsvValidationException;
    void edit(Context context) throws IOException;
    void update(Context context) throws IOException;
    void delete(Context context);
}
