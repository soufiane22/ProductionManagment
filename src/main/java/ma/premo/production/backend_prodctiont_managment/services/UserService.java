package ma.premo.production.backend_prodctiont_managment.services;

import ma.premo.production.backend_prodctiont_managment.models.Produit;
import ma.premo.production.backend_prodctiont_managment.models.Role;
import ma.premo.production.backend_prodctiont_managment.models.User;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public interface UserService {

    User save(User user );

    Collection<User> getALL();

    User get(String id);

    HashMap getStatistics();

    List<User> getByFunction(String function);

    User update(String id, User user);

    Boolean delete(String id);

    Role saveRole(Role role);

}
