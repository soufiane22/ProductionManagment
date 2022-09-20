package ma.premo.production.backend_prodctiont_managment.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.*;
import ma.premo.production.backend_prodctiont_managment.repositories.LineRep;
import ma.premo.production.backend_prodctiont_managment.repositories.ProduitRep;
import ma.premo.production.backend_prodctiont_managment.repositories.StatisticMonthRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class StatisricMonthServiceImli implements StatisticMonthService {

    private final StatisticMonthRep statisticMonthRep;
    private final LineRep lineRep ;

    private final ProduitRep produitRep;
    Comparator<StatisticMonth> compareByDate = (StatisticMonth o1, StatisticMonth o2) -> o1.getDate().compareTo( o2.getDate() );

    @Override
    public StatisticMonth save(StatisticMonth st) {
        log.info("saving new statisticMonth ",st);
        return statisticMonthRep.save(st);
    }

    @Override
    public ListStatistic getByYearAndReference(int year, String id, String type) {
        List<StatisticMonth> statisticsRef = statisticMonthRep.findStatisticMonthByYearAndReferenceAndType(year,id,type);
        statisticsRef.sort(compareByDate.reversed());
        if(statisticsRef.size() > 6)
            statisticsRef = statisticsRef.subList(0, 6);

        ListStatistic listStatistic = new ListStatistic();
        listStatistic.setLine(statisticsRef.get(0).getReference().getReference());
        listStatistic.setListStatistics(statisticsRef);

        return listStatistic;
    }

    @Override
    public   List<ListStatistic>  getByMothAndType(String month, String type) {
        List<Line> listLines = lineRep.findAll();
          List<StatisticMonth> listStatistic = statisticMonthRep.getStatisticMonthByMonthAndType(month, type);
          Collections.sort((List)listLines);
        List<ListStatistic> listStatisticsLine = new ArrayList<>();
        for(Line l:listLines) {
            List<StatisticMonth> list = new ArrayList<>();
            ListStatistic statisticLine = new ListStatistic();
            for (StatisticMonth st : listStatistic) {
                if (st.getLine().getId().equals(l.getId())) {
                    list.add(st);
                }
            }
            statisticLine.setLine(l.getDesignation());
            if(list.size() > 6)
                list = list.subList(0, 6);
            statisticLine.setListStatistics(list);
            listStatisticsLine.add(statisticLine);
        }

        return listStatisticsLine;
    }

    List<Map<String, List<StatisticMonth>>> listStatisticLineGrouped = new ArrayList<>();
    @Override
    public     List<ListStatistic>  getBetweenDateshByType(Date startDate, Date endDate, String type) {
        List<Line> listLines = lineRep.findAll();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int year = calendar.getWeekYear();


        Collections.sort((List)listLines);
        List<ListStatistic> listStatisticsLine = new ArrayList<>();
        listStatisticLineGrouped = new ArrayList<>();

        List<StatisticMonth> listStatistic = statisticMonthRep.findStatisticMonthByYearAndType(year,type);

        List<StatisticMonth> listStatisticGlobal = statisticMonthRep.findStatisticMonthByYearAndType(year,"global");

        ListStatistic statisticsGlobal = new ListStatistic();

        listStatisticGlobal.sort(compareByDate.reversed());
        listStatistic.sort(compareByDate.reversed());

        if(listStatisticGlobal.size() > 6)
            listStatisticGlobal = listStatisticGlobal.subList(0, 6);

        statisticsGlobal.setLine("");
        statisticsGlobal.setListStatistics(listStatisticGlobal);
        listStatisticsLine.add(statisticsGlobal);

        for(Line l:listLines) {
            List<StatisticMonth> list = new ArrayList<>();
            ListStatistic statisticLine = new ListStatistic();
            for (StatisticMonth st : listStatistic) {
                if (st.getLine().getId().equals(l.getId())) {
                    list.add(st);
                }
            }
            statisticLine.setLine(l.getDesignation());
            if(list.size() > 6)
                list = list.subList(0, 6);
            statisticLine.setListStatistics(list);
            listStatisticsLine.add(statisticLine);

        }

        return listStatisticsLine;
    }

    @Override
    public  List<ListStatistic> getByLineAndMonth(String id, String month, String type) {
        Optional<Line> line = lineRep.findById(id);
        Optional<StatisticMonth>  st = Optional.ofNullable(statisticMonthRep.findStatinsticsByLineAndMonthandType(id,month,type));
        List<StatisticMonth> list = new ArrayList<>();
        ListStatistic statistics = new ListStatistic();
        if(st.isPresent()){
            list.add(st.get());
            statistics.setLine(st.get().getLine().getDesignation());
            statistics.setListStatistics(list);
        }else {
            statistics.setLine(line.get().getDesignation());
            statistics.setListStatistics(list);
        }

        List<ListStatistic> listStatisticsLine = new ArrayList<>();
        listStatisticsLine.add(statistics);
        return listStatisticsLine;
    }

    @Override
    public List<ListStatistic> getByReferneceAndMonth(String id, String month, String type) {

        Optional<Produit> produit = produitRep.findById(id);
        Optional<StatisticMonth>  st = Optional.ofNullable(statisticMonthRep.findStatinsticsByReferenceAndMonthAndType(id,month,type));
        List<StatisticMonth> list = new ArrayList<>();
        ListStatistic statistics = new ListStatistic();
        if(st.isPresent()){
            list.add(st.get());
            statistics.setLine(st.get().getReference().getReference());
            statistics.setListStatistics(list);
        }else {
            statistics.setLine(produit.get().getReference());
           // statistics.setListStatistics(list);
        }
        List<ListStatistic> listStatisticsLine = new ArrayList<>();
        listStatisticsLine.add(statistics);
        return listStatisticsLine;
    }

    private List getListMonth(){
        String[] shortMonths = new DateFormatSymbols().getShortMonths();
        Date date = new Date();
        int year = date.getYear()+1900;
        List monthList = new ArrayList();
        for (int i = 0 ; i<shortMonths.length -1 ; i++){
            String monthYear = shortMonths[i]+" "+year ;
            monthList.add(monthYear);
        }
        return monthList;
    }


    @Override
    public StatisticMonth update(StatisticMonth st) {
        StatisticMonth s = statisticMonthRep.findById(st.getId()).orElseThrow();
        s.setOutput(st.getOutput());
        s.setScrap(st.getScrap());
        s.setStandardHours(st.getStandardHours());
        s.setTotalHours(st.getTotalHours());
        s.setNvah(st.getNvah());
        s.setTauxScrap(st.getTauxScrap());
        s.setProductivity(s.getProductivity());

        return  statisticMonthRep.save(s);
    }
}
