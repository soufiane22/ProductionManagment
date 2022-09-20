package ma.premo.production.backend_prodctiont_managment.repositories;

import ma.premo.production.backend_prodctiont_managment.models.Groupe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupeRep extends MongoRepository<Groupe,String> {
    public Groupe getGroupeByDesignation(String designation);
    public Groupe getGroupeByChefEquipe(String chefEquipe);

    @Query( value="{listLine: {$elemMatch: {id:?0}}}")
    List<Groupe> getGroupeByLine(String idLine);


    @Query( value="{listOperateurs: {$elemMatch: {id:?0}}}")
    List<Groupe> getGroupeByUser(String idUser);

    Groupe findGroupeByChefEquipe(String idLeader);



}
