package ma.premo.production.backend_prodctiont_managment.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.Groupe;
import ma.premo.production.backend_prodctiont_managment.models.User;
import ma.premo.production.backend_prodctiont_managment.repositories.GroupeRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GroupeServiceImpli implements GroupeService{

    private final GroupeRep groupeRep;

    @Override
    public Groupe save(Groupe g) {
        log.info("saving group {}",g);
        Comparator<User> compareByLine = (User o1, User o2) -> o1.getLine().compareTo( o2.getLine() );
        g.getListOperateurs().sort(compareByLine);
        return groupeRep.save(g);
    }

    @Override
    public Collection<Groupe> getALL() {
        //Query query = new Query();
        //query.fields().include("name").exclude("id");
        log.info("Fetching all group ");
        return groupeRep.findAll();
    }

    @Override
    public Collection<Groupe> getByLine(String idLine) {
        log.info("Fetching groups by line ");
        return groupeRep.getGroupeByLine(idLine);
    }

    @Override
    public Groupe get(String id) {
        log.info("Get group by id"+id);
        return groupeRep.findById(id).orElseThrow();
    }

    @Override
    public Groupe getByDesignation(String designation){
        log.info("get groupe by designation "+designation);
        return groupeRep.getGroupeByDesignation(designation);
    }

    @Override
    public Groupe getByChefEquipe(String chefEquipe) {
        log.info("get groupe by leader "+chefEquipe);
        return groupeRep.getGroupeByChefEquipe(chefEquipe);
    }

    @Override
    public Groupe update(String id, Groupe g) {
        Groupe groupe = groupeRep.findById(id).orElseThrow();
        groupe.setDesignation(g.getDesignation());
        groupe.setShift(g.getShift());
        groupe.setIngenieur(g.getIngenieur());
        groupe.setChefEquipe(g.getChefEquipe());
        groupe.setLeaderName(g.getLeaderName());
        groupe.setListLine(g.getListLine());
        groupe.setListOperateurs(g.getListOperateurs());
        groupe.setTechnicalExpert(g.getTechnicalExpert());
        groupe.setZone(g.getZone());
        groupe.setPasswordGroup(g.getPasswordGroup());

        Comparator<User> compareByLine = (User o1, User o2) -> o1.getLine().compareTo( o2.getLine() );
        groupe.getListOperateurs().sort(compareByLine);
        return groupeRep.save(groupe);
    }

    @Override
    public Boolean delete(String id) {
        groupeRep.deleteById(id);
        return true;
    }
}
