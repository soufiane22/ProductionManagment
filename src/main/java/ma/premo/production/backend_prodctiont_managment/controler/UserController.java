package ma.premo.production.backend_prodctiont_managment.controler;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.Produit;
import ma.premo.production.backend_prodctiont_managment.models.Response;
import ma.premo.production.backend_prodctiont_managment.models.User;
import ma.premo.production.backend_prodctiont_managment.models.Role;
import ma.premo.production.backend_prodctiont_managment.repositories.UserRep;
import ma.premo.production.backend_prodctiont_managment.services.ProduitService;
import ma.premo.production.backend_prodctiont_managment.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static java.util.Arrays.stream;
import static ma.premo.production.backend_prodctiont_managment.filter.CustomAuthorizationFilter.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;



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


    @GetMapping("/test")
    public String testUser(){
        return "test get";
    }

    //get all users
    //@CrossOrigin(origins = "*")
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


    @GetMapping("/employees")
    public    ResponseEntity<Response>  getEmployees() {
        Collection<User> operators = userService.getByFunction("OPERATEUR");
        Collection<User> maintenance = userService.getByFunction("MAINTENANCE");
        Collection<User> employees = new ArrayList<>();
        employees.addAll(operators);
        employees.addAll(maintenance);
        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1(employees)
                        .message("get all employees")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    // get Users by id
    //@CrossOrigin(origins = "*")
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
    //@CrossOrigin(origins = "*")
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

    /****************** Total of user and lines and products  ***********************/

    @GetMapping("/get/statistics")
    public ResponseEntity<Response> getStatistics() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(userService.getStatistics())
                        .message("statistics retrieve")
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



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> DeleteAccount(@PathVariable("id") String id) {

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
@Data
 class RoleToUser {
    private String userName;
    private String roleName;
}