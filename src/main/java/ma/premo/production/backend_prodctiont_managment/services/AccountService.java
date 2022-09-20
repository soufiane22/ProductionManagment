package ma.premo.production.backend_prodctiont_managment.services;

import ma.premo.production.backend_prodctiont_managment.models.Account;
import ma.premo.production.backend_prodctiont_managment.models.Role;
import ma.premo.production.backend_prodctiont_managment.models.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface AccountService {

    Account save(Account account );

    Collection<Account> getALL();

    Account get(String id);

    Account findByUsername(String username);
    Account updatePassword(Account account);

    Boolean delete(String id);

    Role saveRole(Role role);

    void addRoleToUser(String username , String roleName);
}
