package ma.premo.production.backend_prodctiont_managment.controler;


import lombok.RequiredArgsConstructor;
import ma.premo.production.backend_prodctiont_managment.models.Produit;
import ma.premo.production.backend_prodctiont_managment.models.Response;
import ma.premo.production.backend_prodctiont_managment.models.User;
import ma.premo.production.backend_prodctiont_managment.services.ProduitService;
import ma.premo.production.backend_prodctiont_managment.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @CrossOrigin(origins = "*")
    @PostMapping("/save")
    public ResponseEntity<Response> saveUser(@RequestBody User u){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("user",userService.save(u)))
                        .message("user craeted")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    //get all users
    @CrossOrigin(origins = "*")
    @GetMapping("/getAll")
    public    ResponseEntity<Response>  getAllUsers() {

        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1(userService.getALL())
                        .message("get all Users")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    // get Users par id
    @CrossOrigin(origins = "*")
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getUsertById(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("User", userService.get(id)))
                        .message("user retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    // get Users by function
    @CrossOrigin(origins = "*")
    @GetMapping("/get/function/{function}")
    public ResponseEntity<Response> getUsersByFunction(@PathVariable("function") String function) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1( userService.getByFunction(function))
                        .message("user retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    //@CrossOrigin(origins = "*")
    @PutMapping("/update/{id}")
    // @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Response> UpdateProduct(@PathVariable("id") String id ,@RequestBody User user) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("User updated",userService.update(id , user)))
                        .message("User is updated")
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
                        .data(Map.of("User deleted",userService.delete(id)))
                        .message("User is deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}
