package ma.premo.production.backend_prodctiont_managment.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.*;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import ma.premo.production.backend_prodctiont_managment.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class notification_heures_serviceImpli implements notification_heures_service {

    private final Notification_heurs_Rep notifRep;
    private final GroupeRep groupeRep;
    private final StatisticMonthRep statisticMonthRep;
    private final StatisticMonthService statisticMonthService;

    private final LineRep lineRep;

    private final ProduitRep produitRep;

    Date monthFirstDay;
    @Override
    public Notification_Heures save(Notification_Heures notif ) {
        double standardHoursNotif = notif.getTotalOutput()*notif.getProduit().getTc()/3600;
        standardHoursNotif = Double.parseDouble(String.format("%.2f", standardHoursNotif));
        notif.setStandar_hours(standardHoursNotif);
        notif.setProductivity(Double.parseDouble(String.format("%.2f", notif.getStandar_hours()*100/notif.getTotal_h())));

        if(notif.getTotalOutput() != 0)
        notif.setScrapRatio( Double.parseDouble(String.format("%.2f",(double) notif.getTotalScrap()*100/notif.getTotalOutput())));

        /******* get start date of the notification date ****************/
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(notif.getCreatedAt());
        calendar.add(Calendar.MONTH, 0);
        calendar.setTime(notif.getCreatedAt());
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        monthFirstDay = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = df.format(monthFirstDay);
        int month = monthFirstDay.getMonth();
        int year = monthFirstDay.getYear() + 1900;
        String monthYear = getShortMonth(month)+" "+year;

 /**************** get start date of the curent month ***********/
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date date = calendar1.getTime();
        calendar1.add(Calendar.DAY_OF_MONTH, 1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        String startDateStr1 = df.format(date);
        int month1 = date.getMonth();
        int year1 = date.getYear()+ 1900;
        String monthYear1 =  getShortMonth(month1)+" "+year1;

        monthFirstDay = calendar.getTime();

        /********** initialize global statistic  ********/
        Optional<StatisticMonth> statisticGlobal = Optional.ofNullable(statisticMonthRep.findStatisticMonthByMonthAndType(monthYear,"global"));
        if(!statisticGlobal.isPresent()){
            StatisticMonth stGl = new StatisticMonth();
            stGl.setType("global");
            stGl.setYear(year);
            Line line = new Line();
            line.setDesignation("");
            Produit p = new Produit();
            p.setReference("");
            stGl.setLine(line);
            stGl.setReference(p);
            stGl.setDate(startDateStr);
            stGl.setCreatedAt(monthFirstDay);
            stGl.setMonth(monthYear);
            stGl.setTotalHours(notif.getTotal_h());
            stGl.setStandardHours(notif.getStandar_hours());
            stGl.setNvah(notif.getH_arrete());
            stGl.setScrap(notif.getTotalScrap());
            stGl.setOutput(notif.getTotalOutput());
            stGl.setProductivity(notif.getProductivity());
            stGl.setTauxScrap(notif.getScrapRatio());
            statisticMonthRep.save(stGl);
        }else {
            statisticGlobal.get().setTotalHours(statisticGlobal.get().getTotalHours()+notif.getTotal_h());
            statisticGlobal.get().setNvah(statisticGlobal.get().getNvah()+notif.getH_arrete());
            statisticGlobal.get().setStandardHours(Double.parseDouble(String.format("%.2f",statisticGlobal.get().getStandardHours()+notif.getStandar_hours())) );
            statisticGlobal.get().setScrap(statisticGlobal.get().getScrap()+notif.getTotalScrap());
            statisticGlobal.get().setOutput(statisticGlobal.get().getOutput()+notif.getTotalOutput());

            double productivity = statisticGlobal.get().getStandardHours()*100/statisticGlobal.get().getTotalHours();
            double scrapRatio = (double) statisticGlobal.get().getScrap()*100/statisticGlobal.get().getOutput();
            productivity = Double.parseDouble(String.format("%.2f", productivity));
            scrapRatio = Double.parseDouble(String.format("%.2f", scrapRatio));
            statisticGlobal.get().setProductivity(productivity);
            statisticGlobal.get().setTauxScrap(scrapRatio);
            statisticMonthRep.save(statisticGlobal.get());
        }

        /********** initialize statistic for each line ********/
        lineRep.findAll().forEach(line -> {
            Optional<StatisticMonth> statistic = Optional.ofNullable(statisticMonthRep.findStatisticMonthByMonthAndLineAndType(monthYear,line,"line"));
            StatisticMonth stLine = new StatisticMonth();
            if(!statistic.isPresent()){
                stLine.setType("line");
                stLine.setYear(year);
                stLine.setLine(line);
                //stLine.setReference(notif.getProduit());
                stLine.setDate(startDateStr);
                stLine.setCreatedAt(monthFirstDay);
                stLine.setMonth(monthYear);
                stLine.setTotalHours(0);
                stLine.setStandardHours(0);
                stLine.setNvah(0);
                stLine.setScrap(0);
                stLine.setOutput(0);
                stLine.setProductivity(0);
                stLine.setTauxScrap(0);
                statisticMonthService.save(stLine);
            }

        });

        /********** initialize statistic for each Reference ********/
        produitRep.findAll().forEach(produit -> {
            Optional<StatisticMonth> statistic = Optional.ofNullable(statisticMonthRep.findStatisticMonthByMonthAndReferenceAndType(monthYear,produit,"reference"));
            StatisticMonth stRef = new StatisticMonth();
            if(!statistic.isPresent()){
                stRef.setType("reference");
                stRef.setYear(year);
                stRef.setLine(produit.getListLines().get(0));
                stRef.setReference(produit);
                stRef.setDate(startDateStr);
                stRef.setCreatedAt(monthFirstDay);
                stRef.setMonth(monthYear);
                stRef.setTotalHours(0);
                stRef.setStandardHours(0);
                stRef.setNvah(0);
                stRef.setScrap(0);
                stRef.setOutput(0);
                stRef.setProductivity(0);
                stRef.setTauxScrap(0);
                statisticMonthService.save(stRef);
            }

        });

        /****** add or update statistic for Line ****/
        Optional<StatisticMonth> statisticLine = Optional.ofNullable(statisticMonthRep.findStatisticMonthByMonthAndLineAndType(monthYear,notif.getLigne(),"line"));

        if(!statisticLine.isPresent()){
            StatisticMonth stLine = new StatisticMonth();
            stLine.setType("line");
            stLine.setYear(year);
            stLine.setLine(notif.getLigne());
            stLine.setReference(notif.getProduit());
            stLine.setDate(startDateStr);
            stLine.setCreatedAt(monthFirstDay);
            stLine.setTotalHours(notif.getTotal_h());
            stLine.setStandardHours(notif.getStandar_hours());
            stLine.setNvah(notif.getH_arrete());
            stLine.setScrap(notif.getTotalScrap());
            stLine.setOutput(notif.getTotalOutput());
            stLine.setProductivity(notif.getProductivity());
            stLine.setTauxScrap(notif.getScrapRatio());
            stLine.setMonth(monthYear);
            statisticMonthService.save(stLine);
        }else{
            int totalHours = statisticLine.get().getTotalHours() + notif.getTotal_h();
            double standardHours = statisticLine.get().getStandardHours() + notif.getStandar_hours();
            standardHours = Double.parseDouble(String.format("%.2f", standardHours));
            int nvah = statisticLine.get().getNvah() + notif.getH_arrete();
            int output =  statisticLine.get().getOutput() + notif.getTotalOutput();
            int scrap = statisticLine.get().getScrap() + notif.getTotalScrap();
            statisticLine.get().setTotalHours(totalHours);
            statisticLine.get().setStandardHours(standardHours);
            statisticLine.get().setNvah(nvah);
            statisticLine.get().setOutput(output);
            statisticLine.get().setScrap(scrap);
            double productivity = statisticLine.get().getStandardHours()*100/statisticLine.get().getTotalHours();
            double scrapRation = (double) statisticLine.get().getScrap()*100/statisticLine.get().getOutput();
            scrapRation = Double.parseDouble(String.format("%.2f", scrapRation));
            productivity = Double.parseDouble(String.format("%.2f", productivity));

            statisticLine.get().setProductivity(productivity);
            statisticLine.get().setTauxScrap(scrapRation);
            statisticMonthRep.save(statisticLine.get());
        }

        Optional<StatisticMonth> statisticRef = Optional.ofNullable(statisticMonthRep.findStatinsticsByReferenceAndMonthAndType(notif.getProduit().getId(),monthYear,"reference"));

        /****** add or update statistic for reference ****/
        if(!statisticRef.isPresent()){
            StatisticMonth st = new StatisticMonth();
            st.setType("reference");
            st.setYear(year);
            st.setLine(notif.getLigne());
            st.setReference(notif.getProduit());
            st.setDate(startDateStr);
            st.setCreatedAt(monthFirstDay);
            st.setTotalHours(notif.getTotal_h());
            st.setStandardHours(notif.getStandar_hours());
            st.setNvah(notif.getH_arrete());
            st.setScrap(notif.getTotalScrap());
            st.setOutput(notif.getTotalOutput());
            st.setProductivity(notif.getProductivity());
            st.setTauxScrap(notif.getScrapRatio());
            st.setMonth(monthYear);
            statisticMonthService.save(st);
        }else{
            int totalHours = statisticRef.get().getTotalHours() + notif.getTotal_h();
            double standardHours = statisticRef.get().getStandardHours() + notif.getStandar_hours();
            standardHours = Double.parseDouble(String.format("%.2f", standardHours));
            int nvah = statisticRef.get().getNvah() + notif.getH_arrete();
            int output =  statisticRef.get().getOutput() + notif.getTotalOutput();
            int scrap = statisticRef.get().getScrap() + notif.getTotalScrap();
            statisticRef.get().setTotalHours(totalHours);
            statisticRef.get().setStandardHours(standardHours);
            statisticRef.get().setNvah(nvah);
            statisticRef.get().setOutput(output);
            statisticRef.get().setScrap(scrap);
            double productivity = statisticRef.get().getStandardHours()*100/statisticRef.get().getTotalHours();
            productivity = Double.parseDouble(String.format("%.2f", productivity));
            double scrapRation = (double) statisticRef.get().getScrap()*100/statisticRef.get().getOutput();
            scrapRation = Double.parseDouble(String.format("%.2f", scrapRation));
            statisticRef.get().setProductivity(productivity);
            statisticRef.get().setTauxScrap(scrapRation);
            statisticMonthRep.save(statisticRef.get());
        }

        return notifRep.save(notif);
    }

     private String getShortMonth(int m){
          String[] shortMonths = new DateFormatSymbols().getShortMonths();

          return shortMonths[m];
      }
    // get un nombre de  notifications
    @Override
    public Collection<Notification_Heures> list(String idLeader ,int limit) {
        Collection<Notification_Heures> listNotifs = notifRep.findNotification_HeuresByIdLeader(idLeader);

        //notifRep.findAll(of(0,limit)).toList();
        return listNotifs;
    }

    //get toutes les notifications
    @Override
    public Collection<Notification_Heures> getALL() {
        return notifRep.findAll();
    }

    // get notification par id
    @Override
    public Notification_Heures get(String id) {

        return notifRep.findById(String.valueOf(id)).get();
    }
    // get notification par chef d'équipe
    @Override
    public Collection<Notification_Heures> getNotif_heurByHE(String chef_equipe)
    {
     Collection<Notification_Heures> listNotifs = notifRep.findNotification_HeuresByIdLeader(chef_equipe);
        Collections.sort((List)listNotifs);
        return listNotifs;
    }

    @Override
    public Collection<Notification_Heures> getNotif_heurByDate(String date) {
        Collection<Notification_Heures> notifs =  notifRep.findNotification_HeuresByDate(date);
        //log.info("get notification by date: {} ",notifs);
        return notifs;
    }

    // get Notifs by Leader id and Today date
    @Override
    public Collection<Notification_Heures> getTodayNotif(String id, String date) {
        return notifRep.findNotification_HeuresByIdLeaderAndDate(id,date);
    }

    @Override
    public Collection<Notification_Heures> getNotif_heurByOf(int of) {
        Collection<Notification_Heures> notifs =  notifRep.findNotification_HeuresByOF(of);
        Collections.sort((List)notifs);
        //nolog.info("get notification by OF: {} ",notifs);
        return notifs;
    }

    @Override
    public Collection<Notification_Heures> getNotif_heurByOfAndLeader(int of , String id) {
        Collection<Notification_Heures> notifs =  notifRep.findNotification_HeuresByOFAndIdLeader(of,id);
        Collections.sort((List)notifs);
        //nolog.info("get notification by OF: {} ",notifs);
        return notifs;
    }

    @Override
    public Collection<Notification_Heures> getNotif_heurByLeaderAndStatus(String idLeader,String status) {
        Collection<Notification_Heures> notifs =  notifRep.findNotification_HeuresByIdLeaderAndStatus(idLeader,status);
        Collections.sort((List)notifs);
        return notifs;
    }

    @Override
    public Collection<Notification_Heures> getNotifBetweenTowDateAndIDLeaderAndIdLine(Date startDate , Date endDate , String idLeader ,String idLine){
        return notifRep.findNotification_HeuresByCreatedAtAndIdLeaderAndLine(startDate ,endDate,idLeader,idLine);
    }

    int sumOutput = 0;
    int sumScrap = 0;
    int sumProductivity = 0;
    float tauxScrap = 0;
    float productivity = 0;
    @Override
    public  List<Statistic> getStatistics(Date startDate , Date endDate, String idLine) {
        List<Groupe> listGroupe = groupeRep.getGroupeByLine(idLine);
        List<Statistic> listStatisic = new ArrayList<>();
        if(listGroupe.size() > 0) {
            listGroupe.forEach( g -> {
                Statistic statistic = new Statistic();
                sumOutput = 0;
                sumScrap = 0;
                sumProductivity =0;
                tauxScrap=0;
                productivity=0;
                statistic.setLeaderName(g.getLeaderName());
                Collection<Notification_Heures> notifs = notifRep.findNotification_HeuresByCreatedAtAndIdLeaderAndLine(startDate,endDate,
                        g.getChefEquipe(),idLine);
                notifs.forEach(notif -> {
                    sumOutput += notif.getTotalOutput();
                    sumScrap += notif.getTotalScrap();
                    sumProductivity += notif.getProductivity();
                });
                if(sumOutput != 0)
                tauxScrap = (float) (sumScrap*100)/sumOutput;
                else
                    tauxScrap = 0;
                if (notifs.size() > 0)
                productivity = (float) sumProductivity/notifs.size();

                productivity = (float) Math.round(productivity * 100) / 100;
                tauxScrap = (float) Math.round(tauxScrap * 100) / 100;
                statistic.setSumOutput(sumOutput);
                statistic.setTauxScrap(tauxScrap);
                statistic.setProductivity(productivity);
                listStatisic.add(statistic);
            });
        }

        Comparator<Statistic> comparator = Comparator.comparing(st -> st.getProductivity());
        listStatisic.sort(comparator);
        Collections.reverse(listStatisic);
    //    System.out.println("list statistics "+listStatisic);
        return listStatisic;
    }

    @Override
    public Collection<Notification_Heures> getNotifBetweenTowDate(Date startDate, Date endDate) {
        Collection<Notification_Heures> notifs = notifRep.findNotification_HeuresByCreatedAtBetween(startDate,endDate);
        Collections.sort((List)notifs);
        return notifs;
    }

    @Override
    public Collection<Notification_Heures> getNotifBetweenTowDateAndIDLeader(Date startDate, Date endDate,String id) {
        Collection<Notification_Heures> notifs = notifRep.findNotification_HeuresByCreatedAtAndIdLeader(startDate,endDate,id);
        Collections.sort((List)notifs);
        log.info("notif between two dates {}",notifs);
        return notifs;
    }

    @Override
    public Collection<Notification_Heures> getNotifBetweenTowDateAndIDLine(Date startDate, Date endDate, String idLine) {
        Collection<Notification_Heures> notifs = notifRep.findNotification_HeuresByCreatedAtAndLine(startDate,endDate,idLine);
        Collections.sort((List)notifs);
        return notifs;
    }

    @Override
    public Collection<Notification_Heures> getNotifBetweenTowDateAndIDProduct(Date startDate, Date endDate, String idProduct) {
        Collection<Notification_Heures> notifs = notifRep.findNotification_HeuresByCreatedAtAndProduct(startDate,endDate,idProduct);
        Collections.sort((List)notifs);
        return notifs;
    }


    @Override
    public Notification_Heures update(Notification_Heures n , String idNotif ) {

        Notification_Heures notif = notifRep.findById(idNotif).orElseThrow();
        /******* get start in the current month ****************/
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.setTime(notif.getCreatedAt());
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date monthFirstDay = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        int month = monthFirstDay.getMonth();
        int year = monthFirstDay.getYear() + 1900;
        String monthYear = getShortMonth(month)+" "+year;

        System.out.println("monthYear "+monthYear);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = df.format(monthFirstDay);

        double standardHours = n.getTotalOutput()*n.getProduit().getTc()/3600;
        standardHours = Double.parseDouble(String.format("%.2f",  standardHours));

        Optional<StatisticMonth> statisticGlobal = Optional.ofNullable(statisticMonthRep.findStatisticMonthByMonthAndType(monthYear,"global"));
        Optional<StatisticMonth> statisticLine = Optional.ofNullable(statisticMonthRep.findStatisticMonthByMonthAndLineAndType(monthYear,notif.getLigne(),"line"));
        Optional<StatisticMonth> statisticRef = Optional.ofNullable(statisticMonthRep.findStatisticMonthByDateAndReferenceAndType(monthYear,notif.getProduit(),"reference"));

        if(statisticGlobal.isPresent()){
            int totalHours =  (statisticGlobal.get().getTotalHours() - notif.getTotal_h()) + n.getTotal_h();
            int nvah =  (statisticGlobal.get().getNvah() - notif.getH_arrete()) + n.getH_arrete();
            double  standardHoursStGlo =  (statisticGlobal.get().getStandardHours() - notif.getStandar_hours()) + standardHours;
            int output = ( statisticGlobal.get().getOutput() - notif.getTotalOutput()) + n.getTotalOutput();
            int scrap = (statisticGlobal.get().getScrap() - notif.getTotalScrap()) + n.getTotalScrap();
            double productivity = standardHoursStGlo*100/totalHours;
            double scrapRatio = (double) scrap*100/output;
            standardHoursStGlo = Double.parseDouble(String.format("%.2f",  standardHoursStGlo));
            productivity = Double.parseDouble(String.format("%.2f",productivity));
            scrapRatio = Double.parseDouble(String.format("%.2f",scrapRatio));

            statisticGlobal.get().setTotalHours(totalHours);
            statisticGlobal.get().setNvah(nvah);
            statisticGlobal.get().setStandardHours( standardHoursStGlo);
            statisticGlobal.get().setOutput(output);
            statisticGlobal.get().setScrap(scrap);
            statisticGlobal.get().setProductivity(productivity);
            statisticGlobal.get().setTauxScrap(scrapRatio);

            statisticMonthRep.save(statisticGlobal.get());
        }else{
            System.out.println("statistic global not found");
        }


        if(statisticRef.isPresent()){
            int totalHours =  (statisticRef.get().getTotalHours() - notif.getTotal_h()) + n.getTotal_h();
            int nvah =  (statisticRef.get().getNvah() - notif.getH_arrete()) + n.getH_arrete();
            double  standardHoursSt =  (statisticRef.get().getStandardHours() - notif.getStandar_hours()) + standardHours;
            int output = (statisticRef.get().getOutput() - notif.getTotalOutput()) + n.getTotalOutput();
            int scrap = (statisticRef.get().getScrap() - notif.getTotalScrap()) + n.getTotalScrap();
            double productivity = standardHoursSt*100/totalHours;
            double scrapRatio = (double) scrap*100/output;
            standardHoursSt = Double.parseDouble(String.format("%.2f",  standardHoursSt));
            productivity = Double.parseDouble(String.format("%.2f",productivity));
            scrapRatio = Double.parseDouble(String.format("%.2f",scrapRatio));
            statisticRef.get().setTotalHours(totalHours);
            statisticRef.get().setNvah(nvah);
            statisticRef.get().setStandardHours( standardHoursSt);
            statisticRef.get().setOutput(output);
            statisticRef.get().setScrap(scrap);
            statisticRef.get().setProductivity(productivity);
            statisticRef.get().setTauxScrap(scrapRatio);

            statisticMonthRep.save(statisticRef.get());
        }else{
            System.out.println("statistic ref not found");
        }
        if(statisticLine.isPresent()){
            int totalHours =  (statisticLine.get().getTotalHours() - notif.getTotal_h()) + n.getTotal_h();
            int nvah =  (statisticLine.get().getNvah() - notif.getH_arrete()) + n.getH_arrete();
            double  standardHoursStLine =  (statisticLine.get().getStandardHours() - notif.getStandar_hours()) + standardHours;
            int output = (statisticLine.get().getOutput() - notif.getTotalOutput()) + n.getTotalOutput();
            int scrap = (statisticLine.get().getScrap() - notif.getTotalScrap()) + n.getTotalScrap();
            double productivity =   standardHoursStLine *100/totalHours;
            double scrapRatio = (double) scrap*100/output;
            standardHoursStLine = Double.parseDouble(String.format("%.2f", standardHoursStLine));
            productivity = Double.parseDouble(String.format("%.2f",productivity));
            scrapRatio = Double.parseDouble(String.format("%.2f",scrapRatio));
            statisticLine.get().setTotalHours(totalHours);
            statisticLine.get().setNvah(nvah);
            statisticLine.get().setStandardHours(standardHoursStLine);
            statisticLine.get().setOutput(output);
            statisticLine.get().setScrap(scrap);
            statisticLine.get().setProductivity(productivity);
            statisticLine.get().setTauxScrap(scrapRatio);
            statisticMonthRep.save(statisticLine.get());
        }else{
            System.out.println("statistic line not found");
        }

        notif.setDate(n.getDate());
        notif.setOF(n.getOF());
        notif.setLigne(n.getLigne());
        notif.setProduit(n.getProduit());
        notif.setShift(n.getShift());
        notif.setNbr_operateurs(n.getNbr_operateurs());
        notif.setTotal_h(n.getTotal_h());
        notif.setH_normal(n.getH_normal());
        notif.setH_sup(n.getH_sup());
        notif.setH_arrete(n.getH_arrete());
        notif.setH_devolution(n.getH_devolution());
        notif.setH_nouvau_projet(n.getH_nouvau_projet());
        notif.setRemark(n.getRemark());
        notif.setStatus(n.getStatus());
        notif.setTotalOutput(n.getTotalOutput());
        notif.setTotalScrap(n.getTotalScrap());
        notif.setStandar_hours(n.getTotalOutput()*n.getProduit().getTc()/3600);
        notif.setProductivity(notif.getStandar_hours()*100/notif.getTotal_h());
        notif.setScrapRatio((float) notif.getTotalScrap()*100/notif.getTotalOutput());
        notif.setCreatedAt(n.getCreatedAt());
        return notifRep.save(notif);
    }

    @Override
    public Boolean delete(String id) {
        log.info("delete notification by id: {} ",id);
        notifRep.deleteById(id);
        return true;
    }


}


