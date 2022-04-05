package ma.premo.production.backend_prodctiont_managment.controler;


import lombok.RequiredArgsConstructor;
import ma.premo.production.backend_prodctiont_managment.models.OF;
import ma.premo.production.backend_prodctiont_managment.models.Produit;
import ma.premo.production.backend_prodctiont_managment.models.Response;
import ma.premo.production.backend_prodctiont_managment.repositories.OfRep;
import ma.premo.production.backend_prodctiont_managment.repositories.ProduitRep;
import ma.premo.production.backend_prodctiont_managment.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/produit")
@RequiredArgsConstructor
public class ProduitControler {

    private final ProduitService produitService;

    @CrossOrigin(origins = "*")
    @PostMapping("/save")
    public ResponseEntity<Response> saveProduct(@RequestBody Produit p){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Product",produitService.save(p)))
                        .message("Product craeted")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    //get toutes les OFs
    @CrossOrigin(origins = "*")
    @GetMapping("/getAll")
    public    ResponseEntity<Response>  getAllProducts() {

        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1(produitService.getALL())
                        .message("get all Products")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    // get Products par id
    @CrossOrigin(origins = "*")
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getProductById(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Product", produitService.get(id)))
                        .message("product retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    // get Products by line
    @CrossOrigin(origins = "*")
    @GetMapping("/get/line/{idLine}")
    public ResponseEntity<Response> getProductByLine(@PathVariable("idLine") String idLine) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1( produitService.getProductByLine(idLine))
                        .message("products by line retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    //@CrossOrigin(origins = "*")
    @PutMapping("/update/{id}")
    // @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Response> UpdateProduct(@PathVariable("id") String id ,@RequestBody Produit produit) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Product updated",produitService.update(id , produit)))
                        .message("Product is updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> DeleteProduct(@PathVariable("id") String id) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Product deleted",produitService.delete(id)))
                        .message("Product is deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}
