package ma.premo.production.backend_prodctiont_managment.controler;


import lombok.RequiredArgsConstructor;
import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;
import ma.premo.production.backend_prodctiont_managment.models.Response;
import ma.premo.production.backend_prodctiont_managment.repositories.Notification_heurs_Rep;
import ma.premo.production.backend_prodctiont_managment.services.notification_heures_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

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

    @CrossOrigin(origins = "*")
    @PostMapping("/save/{idLine}/{idProduit}/{idOf}")
    public ResponseEntity<Response> SaveNotification(@RequestBody Notification_Heures notif ,
                                                     @PathVariable("idLine") String idLine ,
                                                     @PathVariable("idProduit") String idProduit,
                                                     @PathVariable("idOf") String idOf) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("notification",notifServices.save(notif,idLine,idProduit,idOf)))
                        .message("notification craeted")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    //get toutes les notifications
    @CrossOrigin(origins = "*")
    @GetMapping("/getAll")
    public    ResponseEntity<Response>  getAllNotifications() {

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
    @CrossOrigin(origins = "*")
    @GetMapping("/list/{limit}")
    public ResponseEntity<Response> getProduit(@PathVariable("limit") int limit) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1( notifServices.list(limit))
                        .message("notifications retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    // get notification par id
    @CrossOrigin(origins = "*")
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
    @CrossOrigin(origins = "*")
    @GetMapping("/getlist/{idlaeder}")
    public Collection<Notification_Heures> getNotifications(@PathVariable("idlaeder") String idlaeder) {
        return  notifServices.getNotif_heurByHE(idlaeder);
    }

    //@CrossOrigin(origins = "*")
    @PutMapping("/update/{idNotif}/{idLine}/{idProduit}/{idOf}")
    // @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Response> UpdateNotification(@PathVariable("idNotif") String idNotif,
                                                       @PathVariable("idLine") String idLine,
                                                       @PathVariable("idProduit") String idProduit ,
                                                       @PathVariable("idOf") String idOf ,
                                                       @RequestBody Notification_Heures notif) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("notification updated",notifServices.update(notif,idNotif,idLine,idProduit,idOf)))
                        .message("notification is updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    @CrossOrigin(origins = "*")
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
