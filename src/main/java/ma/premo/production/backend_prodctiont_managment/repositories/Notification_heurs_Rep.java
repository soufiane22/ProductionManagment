package ma.premo.production.backend_prodctiont_managment.repositories;

import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Notification_heurs_Rep extends MongoRepository<Notification_Heures,String> {
 List<Notification_Heures> findNotification_HeuresByIdLeader(String idLeader);

}
