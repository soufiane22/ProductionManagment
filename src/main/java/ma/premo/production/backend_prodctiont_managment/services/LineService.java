package ma.premo.production.backend_prodctiont_managment.services;


import ma.premo.production.backend_prodctiont_managment.models.Line;
import ma.premo.production.backend_prodctiont_managment.models.OF;

import java.util.Collection;

public interface LineService {
    Line save(Line line);
    Collection<Line> getALL();
    Line get(String id);
    Line update(String id, Line line );
    Boolean delete(String id);
}
