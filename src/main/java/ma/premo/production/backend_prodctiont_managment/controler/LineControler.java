package ma.premo.production.backend_prodctiont_managment.controler;


import lombok.RequiredArgsConstructor;
import ma.premo.production.backend_prodctiont_managment.models.Line;
import ma.premo.production.backend_prodctiont_managment.models.Produit;
import ma.premo.production.backend_prodctiont_managment.models.Response;
import ma.premo.production.backend_prodctiont_managment.repositories.LineRep;
import ma.premo.production.backend_prodctiont_managment.repositories.ProduitRep;
import ma.premo.production.backend_prodctiont_managment.services.LineService;
import ma.premo.production.backend_prodctiont_managment.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/line")
@RequiredArgsConstructor
public class LineControler {

    private final LineService lineService;

    @CrossOrigin(origins = "*")
    @PostMapping("/save")
    public ResponseEntity<Response> saveLine(@RequestBody Line l){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Line",lineService.save(l)))
                        .message("Line crated")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    //get toutes les OFs
    @CrossOrigin(origins = "*")
    @GetMapping("/getAll")
    public    ResponseEntity<Response>  getAllLines() {

        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1(lineService.getALL())
                        .message("get all Lines")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    // get Products par id
    @CrossOrigin(origins = "*")
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getLinetById(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Line", lineService.get(id)))
                        .message("Line retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    //@CrossOrigin(origins = "*")
    @PutMapping("/update/{id}")
    // @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Response> UpdateLine(@PathVariable("id") String id ,@RequestBody Line line) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("line updated",lineService.update(id , line)))
                        .message("line is updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> DeleteLine(@PathVariable("id") String id) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("line deleted",lineService.delete(id)))
                        .message("line is deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );

    }
}
