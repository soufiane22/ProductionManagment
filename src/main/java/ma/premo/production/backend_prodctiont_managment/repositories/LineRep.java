package ma.premo.production.backend_prodctiont_managment.repositories;

import ma.premo.production.backend_prodctiont_managment.models.Line;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRep extends MongoRepository<Line,String> {
}
