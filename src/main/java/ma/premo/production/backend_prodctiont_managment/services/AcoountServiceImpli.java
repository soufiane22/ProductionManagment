package ma.premo.production.backend_prodctiont_managment.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.Account;
import ma.premo.production.backend_prodctiont_managment.models.Role;
import ma.premo.production.backend_prodctiont_managment.models.User;
import ma.premo.production.backend_prodctiont_managment.repositories.AccountRep;
import ma.premo.production.backend_prodctiont_managment.repositories.RoleRep;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AcoountServiceImpli implements AccountService , UserDetailsService {

    private final AccountRep accountRep;
    private final PasswordEncoder passwordEncoder;
    private final RoleRep roleRep;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRep.findByUsername(username);
        if(account == null){
            log.error("user not found in the database");
            throw new UsernameNotFoundException("user not found in the database");
        }else{
            log.info("user found in the database {}",username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        account.getRoles().forEach(role ->
        {authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return  UserDetailsImpl.build(account);

    }

    @Override
    public Account save(Account account) {
                log.info("saving user:{}", account.toString());
                account.setPassword(passwordEncoder.encode(account.getPassword()));
                return accountRep.save(account);

        }



    @Override
    public Collection<Account> getALL() {
        log.info("fetching all users ");
        return accountRep.findAll();
    }

    @Override
    public Account get(String id) {
        log.info("fetching  user {}"+id);
        return accountRep.findById(id).orElseThrow();
    }

    @Override
    public Account findByUsername(String username) {
        accountRep.findByUsername(username);
        return null;
    }

    @Override
    public Account updatePassword(Account account) {
        Account account1 = accountRep.findById(account.getId()).orElseThrow();
        account1.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRep.save(account1);
    }

    @Override
    public Boolean delete(String id) {
        accountRep.deleteById(id);
        return true;
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {}",role.getName());
        return roleRep.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user  {}",roleName,username);
        Account account = accountRep.findByUsername(username);
        Role role = roleRep.findByName(roleName);
        account.getRoles().add(role);
        log.info("user after adding role {}",account.toString());
    }


}
