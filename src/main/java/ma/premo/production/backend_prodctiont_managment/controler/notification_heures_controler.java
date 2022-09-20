package ma.premo.production.backend_prodctiont_managment.controler;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;
import ma.premo.production.backend_prodctiont_managment.models.Response;
import ma.premo.production.backend_prodctiont_managment.models.Statistic;
import ma.premo.production.backend_prodctiont_managment.models.StatisticMonth;
import ma.premo.production.backend_prodctiont_managment.repositories.Notification_heurs_Rep;
import ma.premo.production.backend_prodctiont_managment.services.notification_heures_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/notification_heures")
@RequiredArgsConstructor
public class notification_heures_controler {

    @Autowired
    Notification_heurs_Rep notifRep;
    private final notification_heures_service notifServices;


    @PostMapping("/save")
    public ResponseEntity<Response> SaveNotification(@RequestBody Notification_Heures notif ) throws ParseException {
        Date createdAt =new SimpleDateFormat("yyyy-MM-dd").parse(notif.getDate());
        //System.out.println("createdAt "+createdAt);
        Calendar cal = Calendar.getInstance();
        cal.setTime(createdAt);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date modifiedDate = cal.getTime();
      //  System.out.println("modifiedDate "+modifiedDate);
        notif.setCreatedAt(modifiedDate);

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("notification ",notifServices.save(notif)))
                        .message("notification craeted")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    //get toutes les notifications

    @GetMapping("/getAll")
    //@PreAuthorize("hasRole('ROLE_LEADER')")
    public  ResponseEntity<Response> getAllNotifications() {  //
        System.out.println("get all notifs");

        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1(notifServices.getALL())
                        .message("get all notifications")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build());
        }




    // get un nombre de  notifications

    @GetMapping("/list/{idleader}")
    public ResponseEntity<Response> getListNotification(@PathVariable("idleader") String idleader) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1( notifServices.list(idleader,5))
                        .message("notifications retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    // get notification par id

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getNotificationtById(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("notification", notifServices.get(id)))
                        .message("notification retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    // get notification par chef d'équipe

    @GetMapping("/getlist/{idlaeder}")
    public Collection<Notification_Heures> getNotifications(@PathVariable("idlaeder") String idlaeder) {
        return  notifServices.getNotif_heurByHE(idlaeder);
    }

    // get Notifs by Leader id and Today date

    @GetMapping("/gettodaylist/{idlaeder}/{date}")
    public Collection<Notification_Heures> getTodayNotifications(@PathVariable("idlaeder") String idlaeder, @PathVariable("date") String date) {
        return  notifServices.getTodayNotif(idlaeder,date);
    }


    @GetMapping("/getbydate/{date}")
    public Collection<Notification_Heures> getNotificationsByDate(@PathVariable("date") String date) {
        return  notifServices.getNotif_heurByDate(date);
    }


    @GetMapping("/getbyof/{of}")
    public Collection<Notification_Heures> getNotificationsByOf(@PathVariable("of") int of) {
        return  notifServices.getNotif_heurByOf(of);
    }


    @GetMapping("/getbyof/leader/{idLeader}/{of}")
    public Collection<Notification_Heures> getNotificationsByOfAndLeader(@PathVariable("idLeader") String idLeader,@PathVariable("of") int of) {
        return  notifServices.getNotif_heurByOfAndLeader(of,idLeader);
    }

    @GetMapping("/get/state/{idLeader}/{state}")
    public Collection<Notification_Heures> getNotificationsByLeaderAndStatus(@PathVariable("idLeader") String idLeader ,@PathVariable("state") String state) {
        return  notifServices.getNotif_heurByLeaderAndStatus(idLeader,state);
    }

    @GetMapping("/getbetween/{startdate}/{enddate}")
    public Collection<Notification_Heures> getNotificationsBetweenDates(@PathVariable("startdate") String startDate , @PathVariable("enddate") String endtDate ) throws ParseException {
        Date dateS =new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date dateE =new SimpleDateFormat("yyyy-MM-dd").parse(endtDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateE);
        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date modifiedDate = cal.getTime();
        return  notifServices.getNotifBetweenTowDate(dateS,modifiedDate);
    }


    @GetMapping("/getbetween/leader/{startdate}/{enddate}/{idLeader}")
    public Collection<Notification_Heures> getNotificationsBetweenDatesByLeader(@PathVariable("startdate") String startDate ,
                                                                                @PathVariable("enddate") String endtDate ,
                                                                                @PathVariable("idLeader") String idLeader ) throws ParseException {
        Date dateS =new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date dateE =new SimpleDateFormat("yyyy-MM-dd").parse(endtDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateE);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date modifiedDate = cal.getTime();
        return  notifServices.getNotifBetweenTowDateAndIDLeader(dateS,modifiedDate ,idLeader);
    }


    @GetMapping("/getbetween/line/{startdate}/{enddate}/{idLine}")
    public Collection<Notification_Heures> getNotificationsBetweenDatesByLine(@PathVariable("startdate") String startDate ,
                                                                                @PathVariable("enddate") String endtDate ,
                                                                                @PathVariable("idLine") String idLine ) throws ParseException {
        Date dateS =new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date dateE =new SimpleDateFormat("yyyy-MM-dd").parse(endtDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateE);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date modifiedDate = cal.getTime();
        return  notifServices.getNotifBetweenTowDateAndIDLine(dateS,modifiedDate ,idLine);
    }


    @GetMapping("/getbetween/product/{startdate}/{enddate}/{idProduct}")
    public Collection<Notification_Heures> getNotificationsBetweenDatesByProduct(@PathVariable("startdate") String startDate ,
                                                                              @PathVariable("enddate") String endtDate ,
                                                                              @PathVariable("idProduct") String idProduct ) throws ParseException {
        Date dateS =new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date dateE =new SimpleDateFormat("yyyy-MM-dd").parse(endtDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateE);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date modifiedDate = cal.getTime();
        return  notifServices.getNotifBetweenTowDateAndIDProduct(dateS,modifiedDate ,idProduct);
    }

    @GetMapping(value = "/getstatistic/{startdate}/{enddate}/{idLine}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Statistic> getStatistics (@PathVariable("startdate") String startDate ,
                                          @PathVariable("enddate") String endtDate ,
                                          @PathVariable("idLine") String idLine ) throws ParseException {

        Date dateS =new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date dateE =new SimpleDateFormat("yyyy-MM-dd").parse(endtDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateE);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date modifiedDate = cal.getTime();
        return  notifServices.getStatistics(dateS,modifiedDate,idLine);
    }




    //@CrossOrigin(origins = "*")
    @PutMapping("/update/{idNotif}")
    // @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Response> UpdateNotification(@PathVariable("idNotif") String idNotif, @RequestBody Notification_Heures notif) throws ParseException {
        Date createdAt =new SimpleDateFormat("yyyy-MM-dd").parse(notif.getDate());
        //System.out.println("createdAt "+createdAt);
        Calendar cal = Calendar.getInstance();
        cal.setTime(createdAt);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date modifiedDate = cal.getTime();
        notif.setCreatedAt(modifiedDate);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("notification updated",notifServices.update(notif,idNotif)))
                        .message("notification is updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


   // @CrossOrigin(origins = "*")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> DeleteNotification(@PathVariable("id") String id) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("notification deleted",notifServices.delete(id)))
                        .message("notification is deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }



}
