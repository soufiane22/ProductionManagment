package ma.premo.production.backend_prodctiont_managment.services;


import ma.premo.production.backend_prodctiont_managment.models.Groupe;

import org.springframework.boot.configurationprocessor.json.JSONObject;


import java.util.Collection;

public interface GroupeService {
    Groupe save(Groupe g );

    Collection<Groupe> getALL();

    Collection<Groupe> getByLine(String idLine);

    Groupe get(String id);

    Groupe getByDesignation(String designation);

    Groupe getByChefEquipe(String chefEquipe);

    Groupe update(String id, Groupe g);

    Boolean delete(String id);
}
