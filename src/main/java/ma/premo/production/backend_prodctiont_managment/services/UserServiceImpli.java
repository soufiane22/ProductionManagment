package ma.premo.production.backend_prodctiont_managment.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.Groupe;
import ma.premo.production.backend_prodctiont_managment.models.Produit;
import ma.premo.production.backend_prodctiont_managment.models.User;
import ma.premo.production.backend_prodctiont_managment.models.Role;
import ma.premo.production.backend_prodctiont_managment.repositories.*;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpli implements UserService {
    private final UserRep userRep;
    private final LineRep lineRep;
    private final ProduitRep produitRep;
    private final RoleRep roleRep;
    private final GroupeRep groupeRep;


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
    public HashMap getStatistics() {
        HashMap objet = new HashMap();
        objet.put("totalEmployees",userRep.count());
        objet.put("totalLines",lineRep.count());
        objet.put("totalProduct",produitRep.count());
        return objet;
    }

    @Override
    public List<User> getByFunction(String function){
        log.info("fetching  user by function"+function);
        return userRep.findByFonction(function);
    }


    @Override
    public User update(String id, User u) {
        User user = new User();
        System.out.println("User to update "+u);
         user = userRep.findById(id).orElseThrow();
         user.setNom(u.getNom());
         user.setPrenom(u.getPrenom());
         user.setEmail(u.getEmail());
         user.setFonction(u.getFonction());
         user.setMatricule(u.getMatricule());
         user.setTele(u.getTele());
         user.setLine(u.getLine());

        /*************** update user in groups ***************/
        List<Groupe> listGroups = groupeRep.getGroupeByUser(id);
        if(listGroups != null && listGroups.size()>0)
            for(Groupe g:listGroups){
                int index = IntStream.range(0, g.getListOperateurs().size())
                        .filter(i -> g.getListOperateurs().get(i).getId().equals(id))
                        .findFirst()
                        .orElse(-1);
                if(index != -1)
                    g.getListOperateurs().set(index,user);
                groupeRep.save(g);
            }
        return userRep.save(user);
    }

    @Override
    public Boolean delete(String id) {
        log.info("delete user by id: {} ",id);

        /*************** delete user in Group ***************/
        List<Groupe> listGroups = groupeRep.getGroupeByUser(id);
        if(listGroups != null && listGroups.size()>0)
            for(Groupe g:listGroups){
                int index = IntStream.range(0, g.getListOperateurs().size())
                        .filter(i -> g.getListOperateurs().get(i).getId().equals(id))
                        .findFirst()
                        .orElse(-1);
                if(index != -1)
                    g.getListOperateurs().remove(index);
                groupeRep.save(g);
            }
        userRep.deleteById(id);
        return true;
    }

    @Override
    public Role saveRole(Role role){
        log.info("saving new role {}",role.getName());
        return roleRep.save(role);
    }




}
