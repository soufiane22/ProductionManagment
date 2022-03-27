package ma.premo.production.backend_prodctiont_managment.controler;


import lombok.RequiredArgsConstructor;
import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;
import ma.premo.production.backend_prodctiont_managment.models.Response;
import ma.premo.production.backend_prodctiont_managment.repositories.Notification_heurs_Rep;
import ma.premo.production.backend_prodctiont_managment.repositories.services.notification_heures_service;
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
    @PostMapping("/save")
    public ResponseEntity<Response> SaveNotification(@RequestBody Notification_Heures notif) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("notification",notifServices.save(notif)))
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
    public ResponseEntity<Response> getProduitById(@PathVariable("id") String id) {
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
    @GetMapping("/getlist/{chef}")
    public Collection<Notification_Heures> getProducts(@PathVariable("chef") String chef_equipe) {
        return  notifServices.getNotif_heurByHE(chef_equipe);
    }

    //@CrossOrigin(origins = "*")
    @PutMapping("/update/{id}")
    // @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Response> UpdateProduit(@PathVariable("id") String id ,@RequestBody Notification_Heures notif) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("product updated",notifServices.update(id , notif)))
                        .message("product is updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    @CrossOrigin(origins = "*")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> DeleteProduit(@PathVariable("id") String id) {

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
