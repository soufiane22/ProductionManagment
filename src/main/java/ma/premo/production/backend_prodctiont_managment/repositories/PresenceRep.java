package ma.premo.production.backend_prodctiont_managment.repositories;

import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;
import ma.premo.production.backend_prodctiont_managment.models.Presence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PresenceRep extends MongoRepository<Presence,String> {
    List<Presence> findPresenceByCreatedAtBetween(Date dateStart , Date dateEnd);

    List<Presence> findPresenceByDate(String date);
}
