package ma.premo.production.backend_prodctiont_managment.controler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.Account;
import ma.premo.production.backend_prodctiont_managment.models.Response;
import ma.premo.production.backend_prodctiont_managment.models.User;
import ma.premo.production.backend_prodctiont_managment.repositories.AccountRep;
import ma.premo.production.backend_prodctiont_managment.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService  accountService;
    private final AccountRep accountRep;

    @PostMapping("/save")
    public ResponseEntity<Response> saveAccount(@RequestBody Account account){
        Optional<Account> account1 = Optional.ofNullable(accountRep.findByUsername(account.getUsername()));
        Optional<Account> account2 = Optional.ofNullable(accountRep.findAccountByUser(account.getUser()));
        if(account2.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This User has already account!");
        }else{
            if(account1.isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!");
            }else{
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(now())
                                .data(Map.of("account",accountService.save(account)))
                                .message("account created")
                                .status(CREATED)
                                .statusCode(CREATED.value())
                                .build()
                );
            }
        }
    }

    /* *************  update password for account *********/

    @PutMapping ("/updatepassword")
    public ResponseEntity<Response> updatepassword(@RequestBody Account account){
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(now())
                                .data(Map.of("account",accountService.updatePassword(account)))
                                .message("account created")
                                .status(CREATED)
                                .statusCode(CREATED.value())
                                .build()
                );
    }


    @GetMapping("/getAll")
    public    ResponseEntity<Response>  getAllAccounts() {

        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1(accountService.getALL())
                        .message("get all account")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> DeleteAccount(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("User deleted",accountService.delete(id)))
                        .message("User is deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}
