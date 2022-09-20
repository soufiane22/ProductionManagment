package ma.premo.production.backend_prodctiont_managment.controler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ma.premo.production.backend_prodctiont_managment.models.Account;
import ma.premo.production.backend_prodctiont_managment.models.Groupe;
import ma.premo.production.backend_prodctiont_managment.models.Presence;
import ma.premo.production.backend_prodctiont_managment.models.Response;
import ma.premo.production.backend_prodctiont_managment.repositories.GroupeRep;
import ma.premo.production.backend_prodctiont_managment.services.GroupeService;
import ma.premo.production.backend_prodctiont_managment.services.PresenceService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groupe")

public class GroupeController {
    @Autowired
    private final GroupeService groupeService;

    private final GroupeRep groupeRep;
    @GetMapping(value="/")
    //@RequestMapping(value="/",method=RequestMethod.GET)
    public String hello(){
        return "Hello World!!";
    }




    @PostMapping("/save")
    //@Secured(value={"ROLE_ADMIN"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Response> saveGroup(@RequestBody Groupe g){
        Optional<Groupe> group = Optional.ofNullable(groupeRep.findGroupeByChefEquipe(g.getChefEquipe()));
        if(group.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Leader has already a group!");
        }else{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("Groupe",groupeService.save(g)))
                            .message("Groupe craeted")
                            .status(CREATED)
                            .statusCode(CREATED.value())
                            .build()
            );
        }

    }

    //get All groups

    @GetMapping("/getAll")
    public    ResponseEntity<Response>  getAllGroups() {
        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1(groupeService.getALL())
                        .message("get all Presences")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    // get Products par id

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getGroupById(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Group", groupeService.get(id)))
                        .message("group retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    // get Products par Designation

    @GetMapping("/get/designation/{designation}")
    public ResponseEntity<Response> getGroupByDesignation(@PathVariable("designation") String designation) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .object(groupeService.getByDesignation(designation))
                        .message("group retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    /**** Get groups by line ****/

    @GetMapping("/get/line/{idline}")
    public ResponseEntity<Response> getGroupByLine(@PathVariable("idline") String idline) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .object(groupeService.getByLine(idline))
                        .message("groups retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    // get Groups par Designation

    @GetMapping("/get/leader/{leader}")
    @Secured(value={"ROLE_ADMIN"})
    public ResponseEntity<Response> getGroupByChefEquipe(@PathVariable("leader") String leader) {
        ObjectMapper mapper = new ObjectMapper();

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .object(groupeService.getByChefEquipe(leader))
                        .message("group retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    //@CrossOrigin(origins = "*")
    @PutMapping("/update/{id}")
    // @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Response> UpdateGroup(@PathVariable("id") String id ,@RequestBody Groupe groupe) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Group updated",groupeService.update(id , groupe)))
                        .message("Group is updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> DeleteGroup(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Group deleted",groupeService.delete(id)))
                        .message("Group is deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}
