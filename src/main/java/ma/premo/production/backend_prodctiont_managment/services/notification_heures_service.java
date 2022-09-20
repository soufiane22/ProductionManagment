package ma.premo.production.backend_prodctiont_managment.services;

import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;
import ma.premo.production.backend_prodctiont_managment.models.Statistic;
import org.springframework.boot.configurationprocessor.json.JSONArray;


import java.util.Collection;
import java.util.Date;
import java.util.List;


public interface  notification_heures_service {
    Notification_Heures save(Notification_Heures notif);

    Collection<Notification_Heures> list(String idLeader ,int limit );

    Collection<Notification_Heures> getALL();

    Notification_Heures get(String id);

    Notification_Heures update(Notification_Heures produit,String idNotif );

    Boolean delete(String id);

    Collection<Notification_Heures> getNotif_heurByHE(String chef_equipe);

    Collection<Notification_Heures> getNotif_heurByDate(String date);

    Collection<Notification_Heures> getTodayNotif(String id ,String date);
    Collection<Notification_Heures> getNotif_heurByOf(int of);

    Collection<Notification_Heures> getNotifBetweenTowDate(Date startDate , Date endDate);
    Collection<Notification_Heures> getNotifBetweenTowDateAndIDLeader(Date startDate , Date endDate , String id);

    Collection<Notification_Heures> getNotifBetweenTowDateAndIDLeaderAndIdLine(Date startDate , Date endDate , String idLeader ,String idLine);

    Collection<Notification_Heures> getNotifBetweenTowDateAndIDLine(Date startDate , Date endDate , String idLine);

    Collection<Notification_Heures> getNotifBetweenTowDateAndIDProduct(Date startDate , Date endDate , String idProduct);
    Collection<Notification_Heures> getNotif_heurByOfAndLeader(int of , String idLeader);

    Collection<Notification_Heures> getNotif_heurByLeaderAndStatus(String idLeader, String status);

    List<Statistic> getStatistics(Date startDate , Date endDate, String idLine);
}
