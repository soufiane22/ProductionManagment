package ma.premo.production.backend_prodctiont_managment.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.Line;
import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;

import java.util.Collection;

import ma.premo.production.backend_prodctiont_managment.models.OF;
import ma.premo.production.backend_prodctiont_managment.models.Produit;
import ma.premo.production.backend_prodctiont_managment.repositories.LineRep;
import ma.premo.production.backend_prodctiont_managment.repositories.Notification_heurs_Rep;
import ma.premo.production.backend_prodctiont_managment.repositories.OfRep;
import ma.premo.production.backend_prodctiont_managment.repositories.ProduitRep;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.domain.PageRequest.of;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class notification_heures_serviceImpli implements notification_heures_service {

    private final Notification_heurs_Rep notifRep;
    private final LineRep lineRep;
    private final ProduitRep produitRep;
    private final OfRep ofRep;

    @Override
    public Notification_Heures save(Notification_Heures notif ,String idLine, String idProduit  ,String idOf) {
        Line line = new Line();
        Produit produit = new Produit();
        OF of = new OF();
        line = lineRep.findById(idLine).orElseThrow();
        produit = produitRep.findById(idProduit).orElseThrow();
        of = ofRep.findById(idOf).orElseThrow();
        notif.setLigne(line);
        notif.setProduit(produit);
        notif.setOF(of);
        log.info("saving notification:{}", notif.toString());
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
     Collection<Notification_Heures> listNotifs = notifRep.findNotification_HeuresByIdLeader(chef_equipe);
        return listNotifs;
    }

    @Override
    public Notification_Heures update(Notification_Heures n , String idNotif ,String idLine , String idProduct, String idOf) {
        Notification_Heures notif = notifRep.findById(idNotif).orElseThrow();
        Line line = new Line();
        Produit produit = new Produit();
        OF of = new OF();
        line = lineRep.findById(idLine).orElseThrow();
        produit = produitRep.findById(idProduct).orElseThrow();
        of = ofRep.findById(idOf).orElseThrow();

        notif.setDate(n.getDate());
        notif.setOF(of);
        notif.setLigne(line);
        notif.setProduit(produit);
        notif.setShift(n.getShift());
        notif.setNbr_operateurs(n.getNbr_operateurs());
        notif.setTotal_h(n.getTotal_h());
        notif.setH_normal(n.getH_normal());
        notif.setH_sup(n.getH_sup());
        notif.setH_arrete(n.getH_arrete());
        notif.setH_devolution(n.getH_devolution());
        notif.setH_nouvau_projet(n.getH_nouvau_projet());
        notif.setRemark(n.getRemark());
        return notifRep.save(notif);
    }

    @Override
    public Boolean delete(String id) {
        log.info("delete notification by id: {} ",id);
        notifRep.deleteById(id);
        return true;
    }


}
