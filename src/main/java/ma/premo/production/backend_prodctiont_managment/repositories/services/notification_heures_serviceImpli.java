package ma.premo.production.backend_prodctiont_managment.repositories.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;

import java.util.Collection;

import ma.premo.production.backend_prodctiont_managment.repositories.Notification_heurs_Rep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.domain.PageRequest.of;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class notification_heures_serviceImpli implements notification_heures_service {

    private final Notification_heurs_Rep notifRep;

    @Override
    public Notification_Heures save(Notification_Heures notif) {
        log.info("saving notification:{}", notif.getOF());
        return notifRep.save(notif);
    }

    // get un nombre de  notifications
    @Override
    public Collection<Notification_Heures> list(int limit) {
        return notifRep.findAll(of(0,limit)).toList();
    }

    //get toutes les notifications
    @Override
    public Collection<Notification_Heures> getALL() {
        log.info("fetching all products ");
        return notifRep.findAll();
    }

    // get notification par id
    @Override
    public Notification_Heures get(String id) {
        return notifRep.findById(String.valueOf(id)).get();
    }
    // get notification par chef d'équipe
    @Override
    public Collection<Notification_Heures> getNotif_heurByHE(String chef_equipe)
    {
     Collection<Notification_Heures> listNotifs = notifRep.findNotification_HeuresByChefEquipe(chef_equipe);
        return listNotifs;
    }

    @Override
    public Notification_Heures update(String id, Notification_Heures n) {
        Notification_Heures notif = notifRep.findById(id).orElseThrow();
        notif.setDate(n.getDate());
        notif.setOF(n.getOF());
        notif.setLigne(n.getLigne());
        notif.setShift(n.getShift());
        notif.setNbr_operateurs(n.getNbr_operateurs());
        notif.setTotal_h(n.getTotal_h());
        notif.setH_sup(n.getH_sup());
        notif.setH_arrete(n.getH_arrete());
        notif.setH_devolution(n.getH_devolution());
        notif.setH_nouvau_projet(n.getH_nouvau_projet());
        return notifRep.save(notif);
    }

    @Override
    public Boolean delete(String id) {
        log.info("delete notification by id: {} ",id);
        notifRep.deleteById(id);
        return true;
    }


}
