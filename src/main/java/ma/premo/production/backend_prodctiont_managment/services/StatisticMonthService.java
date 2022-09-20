package ma.premo.production.backend_prodctiont_managment.services;


import ma.premo.production.backend_prodctiont_managment.models.ListStatistic;
import ma.premo.production.backend_prodctiont_managment.models.StatisticMonth;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatisticMonthService {

    StatisticMonth save(StatisticMonth st);

    ListStatistic  getByYearAndReference(int year , String id , String type);

    List<ListStatistic>   getByMothAndType(String month , String type);

    List<ListStatistic> getBetweenDateshByType(Date startDate , Date endDate , String type);

    List<ListStatistic> getByLineAndMonth(String id,String month , String type);

    List<ListStatistic> getByReferneceAndMonth(String id,String month , String type);

    StatisticMonth update(StatisticMonth st);
}
