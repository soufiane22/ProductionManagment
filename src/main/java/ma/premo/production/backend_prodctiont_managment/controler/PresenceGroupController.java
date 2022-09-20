package ma.premo.production.backend_prodctiont_managment.controler;


import lombok.RequiredArgsConstructor;
import ma.premo.production.backend_prodctiont_managment.models.Presence;
import ma.premo.production.backend_prodctiont_managment.models.PresenceGroup;
import ma.premo.production.backend_prodctiont_managment.models.Response;
import ma.premo.production.backend_prodctiont_managment.services.PresenceGroupService;
import ma.premo.production.backend_prodctiont_managment.services.PresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/presencegroup")
@Secured("ROLE_LEADER")
public class PresenceGroupController {

    @Autowired
    private final PresenceGroupService presenceGroupService;


    @PostMapping("/save")
    public ResponseEntity<Response> savePresenceGroup(@RequestBody PresenceGroup p) throws ParseException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Presence group",presenceGroupService.save(p)))
                        .message("Presence craeted")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    //get toutes les OFs

    @GetMapping("/getAll")
    public    ResponseEntity<Response>  getAllPresencesGroup() {
        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1(presenceGroupService.getALL())
                        .message("get all Presences")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    //get Presence Group by idLeader and date

    @GetMapping("/search/{id}/{date}")
    public    ResponseEntity<Response>  getByLeaderAndDate(@PathVariable("id") String id ,@PathVariable("date") String date) {
        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1(presenceGroupService.getByLeaderAndDate(id,date))
                        .message("get presences by leader and date")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }


    //get Presence Group by date

    @GetMapping("/getbydate/{date}")
    public    ResponseEntity<Response>  getPresenceDate(@PathVariable("date") String date) {
        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1(presenceGroupService.getPresenceGrouptByDate(date))
                        .message("get presences by date")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }


    @GetMapping("/getbetween/{startdate}/{enddate}")
    public Collection<PresenceGroup> getPresenceBetweenDates(@PathVariable("startdate") String startDate , @PathVariable("enddate") String endtDate ) throws ParseException {
        Date dateS =new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date dateE =new SimpleDateFormat("yyyy-MM-dd").parse(endtDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateE);
        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date modifiedDate = cal.getTime();
        return  presenceGroupService.getBetweenTwoDates(dateS,modifiedDate);
    }

    @GetMapping("/get/line/{startdate}/{enddate}/{idline}")
    public Collection<PresenceGroup> getPresenceBetweenDatesAndLine(@PathVariable("startdate") String startDate,
                                                                    @PathVariable("enddate") String endtDate,
                                                                    @PathVariable("idline") String idline) throws ParseException {
        Date dateS =new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date dateE =new SimpleDateFormat("yyyy-MM-dd").parse(endtDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateE);
        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date modifiedDate = cal.getTime();
        return  presenceGroupService.getByLineAndBetweenTwoDates(dateS,modifiedDate,idline);
    }


    @GetMapping("/get/leader/{idleader}/{startdate}/{enddate}")
    public Collection<PresenceGroup> getPresenceByLeaderAndDates(@PathVariable("idleader") String idLeader ,
                                                                 @PathVariable("startdate") String startDate ,
                                                                 @PathVariable("enddate") String endtDate ) throws ParseException {
        Date dateS =new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date dateE =new SimpleDateFormat("yyyy-MM-dd").parse(endtDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateE);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date modifiedDate = cal.getTime();
        return  presenceGroupService.getByLeaderAndBetweenTwoDates(idLeader,dateS,modifiedDate);
    }

    //get list limited of Presences

    @GetMapping("/getlist/{leaderid}/{limit}")
    public    ResponseEntity<Response>  getByListPresence(@PathVariable("limit") int limit , @PathVariable("leaderid") String leaderId) {
        return  ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data1(presenceGroupService.getList(limit , leaderId))
                        .message("get list limited of presences ")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    // get Presence Group By id

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getPresencetById(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Presence", presenceGroupService.get(id)))
                        .message("presence retrieve")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    @PutMapping("/update/{id}")
    // @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Response> UpdatePresence(@PathVariable("id") String id ,@RequestBody PresenceGroup presence) throws ParseException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Presence updated",presenceGroupService.update(id , presence)))
                        .message("Presence is updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PutMapping("/updatestatus/{id}")
    // @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Response> UpdateStatusPresence(@PathVariable("id") String id ,@RequestBody PresenceGroup presence) throws ParseException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Presence updated",presenceGroupService.updatestatus(id , presence)))
                        .message("Presence is updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> DeletePresence(@PathVariable("id") String id) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Presence deleted",presenceGroupService.delete(id)))
                        .message("Presence is deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}
