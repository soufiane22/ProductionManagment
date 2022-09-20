package ma.premo.production.backend_prodctiont_managment.repositories;

import ma.premo.production.backend_prodctiont_managment.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRep  extends MongoRepository<User,String> {
    public List<User> findByFonction(String function);
    @Query(value="{ 'line.id': ?0 }")
    public List<User> findByLine(String idLine);


    public  List<User> findAll();

    long count();
}
