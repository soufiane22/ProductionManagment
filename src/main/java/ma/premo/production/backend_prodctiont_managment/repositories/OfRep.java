package ma.premo.production.backend_prodctiont_managment.repositories;

import lombok.RequiredArgsConstructor;
import ma.premo.production.backend_prodctiont_managment.models.OF;
import ma.premo.production.backend_prodctiont_managment.models.Produit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Repository
public interface OfRep extends MongoRepository<OF,String> {
    List<OF> findOFByIdProduit(String id);

}
