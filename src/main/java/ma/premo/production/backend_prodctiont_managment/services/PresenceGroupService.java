package ma.premo.production.backend_prodctiont_managment.services;

import ma.premo.production.backend_prodctiont_managment.models.Presence;
import ma.premo.production.backend_prodctiont_managment.models.PresenceGroup;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface PresenceGroupService {
    PresenceGroup save(PresenceGroup presence ) throws ParseException;

    List<PresenceGroup> getList(int limit , String lesderId);

    Collection<PresenceGroup> getALL();

    Collection<PresenceGroup> getPresenceGrouptByDate(String date);

    Collection<PresenceGroup> getByLeaderAndDate(String id , String date);

    Collection<PresenceGroup> getByLeaderAndBetweenTwoDates(String id , Date startDate, Date endDate);

    Collection<PresenceGroup> getByLineAndBetweenTwoDates(Date startDate, Date endDate, String idLine);
    Collection<PresenceGroup> getByDate(String date);

    PresenceGroup get(String id);

    PresenceGroup update(String id, PresenceGroup presence ) throws ParseException;

    PresenceGroup updatestatus(String id, PresenceGroup presence ) throws ParseException;

    Boolean delete(String id);

    Collection<PresenceGroup> getBetweenTwoDates(Date startDate , Date endDate );
}
