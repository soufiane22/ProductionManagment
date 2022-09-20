package ma.premo.production.backend_prodctiont_managment.repositories;

import ma.premo.production.backend_prodctiont_managment.models.Line;
import ma.premo.production.backend_prodctiont_managment.models.Presence;
import ma.premo.production.backend_prodctiont_managment.models.Produit;
import ma.premo.production.backend_prodctiont_managment.models.StatisticMonth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface StatisticMonthRep extends MongoRepository<StatisticMonth,String> {

    @Query( value="{ 'line.id': ?0 , 'month':  ?1,  'type':  ?2}")
    StatisticMonth findStatinsticsByLineAndMonthandType(String idLine , String month , String type);

    @Query( value="{'reference.id': ?0 , 'month':  ?1 , 'type':  ?2}")
    StatisticMonth findStatinsticsByReferenceAndMonthAndType(String idReference , String month , String type);

    @Query( value="{'createdAt' : { $gt:?0 , $lte: ?1 } ,'type': ?2 }")
    List<StatisticMonth> findStatisticsByCreatedAtBetweenAndType(Date dateStart , Date dateEnd ,String type);

   // @Query( value ="{'line.id': ?0}")
    StatisticMonth findStatisticMonthByDateAndReferenceAndType(String date , Produit p, String type);

    StatisticMonth findStatisticMonthByMonthAndReferenceAndType(String month , Produit p, String type);

    StatisticMonth findStatisticMonthByMonthAndLineAndType(String month , Line l, String type);


    StatisticMonth findStatisticMonthByMonthAndType(String month , String type);
    List<StatisticMonth>  findStatisticMonthByYearAndType(int year ,String type);


    @Query( value="{'year':?0 , 'reference.id': ?1 , 'type':  ?2}")
    List<StatisticMonth>  findStatisticMonthByYearAndReferenceAndType(int year ,String id , String type);

    @Query( value="{'month':?0 , 'type':  ?1}")
    List<StatisticMonth> getStatisticMonthByMonthAndType(String month , String type);

}