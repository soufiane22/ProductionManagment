package ma.premo.production.backend_prodctiont_managment.controler;

import lombok.RequiredArgsConstructor;
import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;
import ma.premo.production.backend_prodctiont_managment.models.OF;
import ma.premo.production.backend_prodctiont_managment.models.Response;
import ma.premo.production.backend_prodctiont_managment.repositories.Notification_heurs_Rep;
import ma.premo.production.backend_prodctiont_managment.repositories.OfRep;
import ma.premo.production.backend_prodctiont_managment.services.OfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/of")
@RequiredArgsConstructor
public class OfControler {
    @Autowired
    OfRep ofRep;
    private final OfService ofService;


    @PostMapping("/save")
    public ResponseEntity<Response> saveOf(@RequestBody OF of){
       return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("OF",ofService.save(of)))
                        .message("OF craeted")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }


    //get toutes les OFs

    @GetMapping("/getAll")
    public    ResponseEntity<Response>  getAllOFs() {

        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1(ofService.getALL())
                        .message("get all OFs")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build());
    }

    // get notification par id

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getOfId(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("OF", ofService.getOfByProduct(id)))
                        .message("of retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    // get notification par id

    @GetMapping("/get/produit/{id}")
    public ResponseEntity<Response> getOfByProduct(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1(ofService.getOfByProduct(id))
                        .message("of by product retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    //@CrossOrigin(origins = "*")
    @PutMapping("/update/{id}")
    // @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Response> UpdateNotification(@PathVariable("id") String id ,@RequestBody OF of) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("OF updated",ofService.update(id , of)))
                        .message("OF is updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> DeleteNotification(@PathVariable("id") String id) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("OF deleted",ofService.delete(id)))
                        .message("OF is deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}


