package ma.premo.production.backend_prodctiont_managment.services;

import ma.premo.production.backend_prodctiont_managment.models.Account;
import ma.premo.production.backend_prodctiont_managment.models.User;
import ma.premo.production.backend_prodctiont_managment.repositories.AccountRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public class jwtService implements UserDetailsService {

    @Autowired
    AccountRep accountRep;
    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRep.findByUsername(username);
        return  UserDetailsImpl.build(account);
    }
}
