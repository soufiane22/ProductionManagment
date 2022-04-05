package ma.premo.production.backend_prodctiont_managment.repositories;

import ma.premo.production.backend_prodctiont_managment.models.Groupe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupeRep extends MongoRepository<Groupe,String> {
}
