package ma.premo.production.backend_prodctiont_managment.repositories;

import ma.premo.production.backend_prodctiont_managment.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRep  extends MongoRepository<User,String> {
}
