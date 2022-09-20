package ma.premo.production.backend_prodctiont_managment.repositories;

import ma.premo.production.backend_prodctiont_managment.models.Presence;
import ma.premo.production.backend_prodctiont_managment.models.PresenceGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface PresenceGroupRep extends MongoRepository<PresenceGroup,String> {
    public List<PresenceGroup> findByDate(String date);

    public List<PresenceGroup> findByLeaderId(String date);
    public List<PresenceGroup> findByLeaderIdAndDate(String id, String date);

    List<PresenceGroup> findPresenceByCreatedAtBetween(Date dateStart , Date dateEnd);

    @Query( value="{'leaderId': ?0 , 'createdAt' : { $gt:?1 , $lte: ?2 } }")
    public List<PresenceGroup> findPresenceByLeaderAndTwoDates(String idleader ,Date dateStart ,Date dateEnd );


    @Query(value="{'createdAt' : { $gt:?0 , $lte: ?1 } , 'listOperateurs': {$elemMatch: {line.id:?2}} }")
    public List<PresenceGroup> findPresenceByLineAndTwoDates(Date dateStart ,Date dateEnd,String idLine );


}
