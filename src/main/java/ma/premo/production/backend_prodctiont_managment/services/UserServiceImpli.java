package ma.premo.production.backend_prodctiont_managment.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.User;
import ma.premo.production.backend_prodctiont_managment.repositories.UserRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpli implements UserService {
    private final UserRep userRep;

    @Override
    public User save(User user) {
        log.info("saving user:{}", user.toString());
        return userRep.save(user);
    }

    @Override
    public Collection<User> getALL() {
        log.info("fetching all users ");
        return userRep.findAll();
    }

    @Override
    public User get(String id) {
        log.info("fetching  user "+id);
        return userRep.findById(id).orElseThrow();
    }

    @Override
    public List<User> getByFunction(String function){
        log.info("fetching  user by function"+function);
        return userRep.findByFonction(function);
    }

    @Override
    public User update(String id, User u) {
        User user = new User();
         user = userRep.findById(id).orElseThrow();
         user.setNom(u.getNom());
         user.setPrenom(u.getPrenom());
         user.setEmail(u.getEmail());
         user.setFonction(u.getFonction());
         user.setMatricule(u.getMatricule());
         user.setTele(u.getTele());
         user.setPassword(u.getPassword());
         user.setLine(u.getLine());
        return userRep.save(user);
    }

    @Override
    public Boolean delete(String id) {
        log.info("delete user by id: {} ",id);
        userRep.deleteById(id);
        return true;
    }
}
