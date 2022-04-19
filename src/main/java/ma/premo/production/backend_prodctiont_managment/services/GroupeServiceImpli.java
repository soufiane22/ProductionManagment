package ma.premo.production.backend_prodctiont_managment.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.Groupe;
import ma.premo.production.backend_prodctiont_managment.models.User;
import ma.premo.production.backend_prodctiont_managment.repositories.GroupeRep;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GroupeServiceImpli implements GroupeService{

    private final GroupeRep groupeRep;

    @Override
    public Groupe save(Groupe g) {
        log.info("saving group {}",g);
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
        log.info("get groupe by designation "+chefEquipe);
        return groupeRep.getGroupeByChefEquipe(chefEquipe);
    }

    @Override
    public Groupe update(String id, Groupe g) {
        Groupe groupe = groupeRep.findById(id).orElseThrow();
        groupe.setDesignation(g.getDesignation());
        groupe.setShift(g.getShift());
        groupe.setIngenieur(g.getIngenieur());
        groupe.setChefEquipe(g.getChefEquipe());
        groupe.setListLine(g.getListLine());
        groupe.setListOperateurs(g.getListOperateurs());
        return groupeRep.save(groupe);
    }

    @Override
    public Boolean delete(String id) {
        groupeRep.deleteById(id);
        return true;
    }
}
