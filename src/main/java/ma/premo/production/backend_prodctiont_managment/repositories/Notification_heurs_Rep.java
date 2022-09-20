package ma.premo.production.backend_prodctiont_managment.repositories;

import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;
import ma.premo.production.backend_prodctiont_managment.services.notification_heures_service;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface Notification_heurs_Rep extends MongoRepository<Notification_Heures,String> {
 List<Notification_Heures> findNotification_HeuresByIdLeader(String idLeader);
 List<Notification_Heures> findNotification_HeuresByDate(String dtae);

 List<Notification_Heures> findNotification_HeuresByIdLeaderAndStatus(String idLeader , String status);

 List<Notification_Heures> findNotification_HeuresByOF(int of);
 List<Notification_Heures> findNotification_HeuresByIdLeaderAndDate(String idLeader , String date);

 List<Notification_Heures> findNotification_HeuresByCreatedAtBetween(Date dateStart , Date dateEnd);
 @Query( value="{'createdAt' : { $gt:?0 , $lte: ?1 } , 'ligne.id': ?2}")
 List<Notification_Heures> findNotification_HeuresByCreatedAtAndLine(Date dateStart , Date dateEnd , String idLine);

 @Query( value="{'createdAt' : { $gt:?0 , $lte: ?1 } , 'produit.id': ?2}")
 List<Notification_Heures> findNotification_HeuresByCreatedAtAndProduct(Date dateStart , Date dateEnd , String idProduct);
 @Query( value="{'createdAt' : { $gt:?0 , $lte: ?1 } , 'idLeader': ?2}")
 Collection<Notification_Heures> findNotification_HeuresByCreatedAtAndIdLeader(Date from, Date to ,String id);



 @Query( value="{'createdAt' : { $gt:?0 , $lte: ?1 } , 'idLeader': ?2, 'ligne.id': ?3}")
 Collection<Notification_Heures> findNotification_HeuresByCreatedAtAndIdLeaderAndLine(Date from, Date to ,String idLeader, String idLine);

 List<Notification_Heures> findNotification_HeuresByOFAndIdLeader(int of, String idLeader);
}
