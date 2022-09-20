package ma.premo.production.backend_prodctiont_managment.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.Presence;
import ma.premo.production.backend_prodctiont_managment.models.PresenceGroup;
import ma.premo.production.backend_prodctiont_managment.repositories.PresenceGroupRep;
import ma.premo.production.backend_prodctiont_managment.repositories.PresenceRep;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.PageRequest.of;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PresenceGroupServiceImpli implements PresenceGroupService{
    private final PresenceGroupRep presenceGroupRep;
    private final PresenceRep presenceRep;

    @Override
    public PresenceGroup save(PresenceGroup presence) throws ParseException {
        Date createdAt =new SimpleDateFormat("yyyy-MM-dd").parse(presence.getDate());
        Calendar cal = Calendar.getInstance();
        cal.setTime(createdAt);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date modifiedDate = cal.getTime();
        presence.setCreatedAt(modifiedDate);
        log.info("Saving presenceGroup",presence);

        for (Presence p : presence.getListPresence()) {
            p.setDate(presence.getDate());
            p.setCreatedAt(modifiedDate);
        }
      //  presenceRep.saveAll(presence.getListPresence());

        return presenceGroupRep.save(presence);
    }

    @Override
    public List<PresenceGroup> getList(int limit ,String leaderId) {
        List<PresenceGroup> sublist = new ArrayList<>();
        List<PresenceGroup> list =   presenceGroupRep.findByLeaderId(leaderId);
        Collections.reverse(list);
       //Collections.sort(list, Collections.reverse());
        if(limit > list.size()){
             sublist = list.subList(0,list.size());

        }else{
            sublist = list.subList(0,limit);
        }


       //cllection<PresenceGroup> list =  presenceGroupRep.findAll(of(0,limit,Sort.by(Sort.Direction.DESC, "id"))).toList();

       // return  presenceGroupRep.findAll(of(0,limit)).toList();
        return sublist;

    }


    @Override
    public Collection<PresenceGroup> getALL() {
        log.info("Fetching all presence");
        return presenceGroupRep.findAll();
    }

    @Override
    public Collection<PresenceGroup> getPresenceGrouptByDate(String date) {
        return presenceGroupRep.findByDate(date);
    }

    @Override
    public Collection<PresenceGroup> getByLeaderAndDate(String id , String date) {
        return  presenceGroupRep.findByLeaderIdAndDate(id,date);
    }

    @Override
    public Collection<PresenceGroup> getByLeaderAndBetweenTwoDates(String id, Date startDate, Date endDate) {
        Collection<PresenceGroup> listPresences = presenceGroupRep.findPresenceByLeaderAndTwoDates(id,startDate,endDate);
        //log.info("listPresences {}"+listPresences);
        return listPresences;
    }

    @Override
    public Collection<PresenceGroup> getByLineAndBetweenTwoDates(Date startDate, Date endDate, String idLine) {
        return presenceGroupRep.findPresenceByLineAndTwoDates(startDate,endDate,idLine);
    }

    @Override
    public Collection<PresenceGroup> getByDate(String date) {
        log.info("list presence group by date {}");
        return presenceGroupRep.findByDate(date);
    }

    @Override
    public Collection<PresenceGroup> getBetweenTwoDates(Date startDate, Date endDate) {

        return presenceGroupRep.findPresenceByCreatedAtBetween(startDate ,endDate);
    }

    @Override
    public PresenceGroup get(String id) {
        return presenceGroupRep.findById(id).orElseThrow();
    }

    @Override
    public PresenceGroup update(String id, PresenceGroup presenceGroup) throws ParseException {
        Date createdAt =new SimpleDateFormat("yyyy-MM-dd").parse(presenceGroup.getDate());
        Calendar cal = Calendar.getInstance();
        cal.setTime(createdAt);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date modifiedDate = cal.getTime();

        PresenceGroup presenceGroup1 = new PresenceGroup();

        for (Presence p: presenceGroup.getListPresence()){
            p.setDate(presenceGroup.getDate());
            p.setCreatedAt(modifiedDate);
        }


         presenceGroup1 = presenceGroupRep.findById(id).orElseThrow();
         presenceGroup1.setGroup(presenceGroup.getGroup());
         presenceGroup1.setDate(presenceGroup.getDate());
         presenceGroup1.setLeaderId(presenceGroup.getLeaderId());
         presenceGroup1.setEngineer(presenceGroup.getEngineer());
         presenceGroup1.setTotalOperators(presenceGroup.getTotalOperators());
         presenceGroup1.setSumHours(presenceGroup.getSumHours());
         presenceGroup1.setListPresence(presenceGroup.getListPresence());
         presenceGroup1.setCreatedAt(modifiedDate);
         presenceGroup1.setStatus(presenceGroup.getStatus());

        return presenceGroupRep.save(presenceGroup1);
    }

    @Override
    public PresenceGroup updatestatus(String id, PresenceGroup presence) throws ParseException {
        PresenceGroup presenceGroup = new PresenceGroup();
        presenceGroup  =   presenceGroupRep.findById(id).orElseThrow();
        presenceGroup.setStatus(presence.getStatus());

        return presenceGroupRep.save(presenceGroup);
    }

    @Override
    public Boolean delete(String id) {
        PresenceGroup pg = presenceGroupRep.findById(id).orElseThrow();
        /*
        for (Presence p : pg.getListPresence()){
            presenceRep.deleteById(p.getId());
        }

         */
        presenceGroupRep.deleteById(id);

        return true;
    }
}
