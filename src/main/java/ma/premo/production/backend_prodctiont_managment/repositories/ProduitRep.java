package ma.premo.production.backend_prodctiont_managment.repositories;

import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;
import ma.premo.production.backend_prodctiont_managment.models.Produit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ProduitRep extends MongoRepository<Produit,String> {
   // @Query( value="{'createdAt' : { $gt:?0 , $lte: ?1 } , 'produit.id': ?2}")
   //@Query( value="{ 'line.id': ?0}")

   @Query( value="{listLines: {$elemMatch: {id:?0}}}")
    List<Produit> findProduitByLineById(String idLigne);


    long count();
}
