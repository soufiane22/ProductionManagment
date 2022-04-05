package ma.premo.production.backend_prodctiont_managment.services;


import ma.premo.production.backend_prodctiont_managment.models.Groupe;
import ma.premo.production.backend_prodctiont_managment.models.User;

import java.util.Collection;

public interface GroupeService {
    Groupe save(Groupe g );

    Collection<Groupe> getALL();

    Groupe get(String id);

    Groupe update(String id, Groupe g);

    Boolean delete(String id);
}
