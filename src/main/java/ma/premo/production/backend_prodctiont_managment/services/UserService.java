package ma.premo.production.backend_prodctiont_managment.services;

import ma.premo.production.backend_prodctiont_managment.models.Produit;
import ma.premo.production.backend_prodctiont_managment.models.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface UserService {

    User save(User user );

    Collection<User> getALL();

    User get(String id);

    User update(String id, User user);

    Boolean delete(String id);
}
