package ma.premo.production.backend_prodctiont_managment.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.Line;
import ma.premo.production.backend_prodctiont_managment.repositories.LineRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class LineServiceImpli implements LineService{
    private final LineRep lineRep;
    @Override
    public Line save(Line line) {
        log.info("saving line",line.getDesignation());
        return lineRep.save(line);
    }

    @Override
    public Collection<Line> getALL() {
        log.info("fetching all line");
        return lineRep.findAll();
    }

    @Override
    public Line get(String id) {
        log.info("fetching line of  "+id);
        return lineRep.findById(id).orElseThrow();
    }

    @Override
    public Line update(String id, Line line) {
        Line l = new Line();
        l= lineRep.findById(id).orElseThrow();
        l.setDesignation(line.getDesignation());
        l.setCentre(line.getCentre());
        return lineRep.save(l);
    }

    @Override
    public Boolean delete(String id) {
        lineRep.deleteById(id);
        return true;
    }
}
