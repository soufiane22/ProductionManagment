package ma.premo.production.backend_prodctiont_managment.services;

import ma.premo.production.backend_prodctiont_managment.models.Presence;
import ma.premo.production.backend_prodctiont_managment.models.Produit;

import java.util.Collection;
import java.util.Date;

public interface PresenceService {
    Presence save(Presence presence );

    Collection<Presence> list(int limit);

    Collection<Presence> getALL();

    Collection<Presence> saveAll(Collection<Presence> listPresence);

    Collection<Presence> getPresencetByShift(String shift);

    Collection<Presence> getPresenceByDate(String date);
    Collection<Presence> getBetweenTwoDates(Date startDate , Date endDate );
    Presence get(String id);



    Presence update(String id, Presence presence );

    Boolean delete(String id);
}
