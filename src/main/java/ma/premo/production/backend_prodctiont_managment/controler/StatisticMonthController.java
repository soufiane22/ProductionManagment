package ma.premo.production.backend_prodctiont_managment.controler;


import lombok.RequiredArgsConstructor;
import ma.premo.production.backend_prodctiont_managment.models.ListStatistic;
import ma.premo.production.backend_prodctiont_managment.models.Response;
import ma.premo.production.backend_prodctiont_managment.models.StatisticMonth;
import ma.premo.production.backend_prodctiont_managment.repositories.StatisticMonthRep;
import ma.premo.production.backend_prodctiont_managment.services.StatisticMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/statisticMonth")
@RequiredArgsConstructor
public class StatisticMonthController {

    @Autowired
    private final StatisticMonthRep statisticMonthRep;

    private final StatisticMonthService statisticMonthService;

    @PostMapping("/save")
    public ResponseEntity<Response> SaveStatisticMonth(@RequestBody StatisticMonth statisticMonth ) throws ParseException {
        Date createdAt =new SimpleDateFormat("yyyy-MM-dd").parse(statisticMonth.getDate());
        //System.out.println("createdAt "+createdAt);
        Calendar cal = Calendar.getInstance();
        cal.setTime(createdAt);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date modifiedDate = cal.getTime();
        //  System.out.println("modifiedDate "+modifiedDate);
        statisticMonth.setCreatedAt(modifiedDate);

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("statistic",statisticMonthService.save(statisticMonth)))
                        .message("notification craeted")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/getbymonth/{startdate}/{enddate}/{type}")
    public  List<ListStatistic> getStatisticByMonth(@PathVariable("startdate") String startDate ,
                                                  @PathVariable("enddate") String endtDate ,
                                                  @PathVariable("type") String type ) throws ParseException{

        Date dateS =new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date dateE =new SimpleDateFormat("yyyy-MM-dd").parse(endtDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateE);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date modifiedDate = cal.getTime();
        //System.out.println("startDate "+dateS+"\n"+"endDate "+modifiedDate);
        return statisticMonthService.getBetweenDateshByType(dateS , modifiedDate, type);
    }

    @GetMapping("/get/reference/{year}/{id}/{type}")
    public ListStatistic  getStatisticByYearAndRef(@PathVariable("year") int year ,
                                                    @PathVariable("id") String id ,
                                                    @PathVariable("type") String type ) {


        return statisticMonthService.getByYearAndReference(year ,id , type);
    }

    @GetMapping("/get/month/{month}/{type}")
    public List<ListStatistic>  getStatisticByMonthAndType(@PathVariable("month") String month ,
                                                   @PathVariable("type") String type ) {


        return statisticMonthService.getByMothAndType(month , type);
    }

    @GetMapping("/get/line/{id}/{month}")
    public List<ListStatistic>   getStatisticByLineAndMonth(@PathVariable("id") String id ,
                                                           @PathVariable("month") String month ) {

        return statisticMonthService.getByLineAndMonth(id,month,"line");
    }

    @GetMapping("/get/reference/{id}/{month}")
    public List<ListStatistic>   getStatisticByReferenceAndMonth(@PathVariable("id") String id ,
                                                            @PathVariable("month") String month ) {

        return statisticMonthService.getByReferneceAndMonth(id,month,"reference");
    }



}
