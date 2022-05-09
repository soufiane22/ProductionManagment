package ma.premo.production.backend_prodctiont_managment.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.User;
import ma.premo.production.backend_prodctiont_managment.models.Role;
import ma.premo.production.backend_prodctiont_managment.repositories.RoleRep;
import ma.premo.production.backend_prodctiont_managment.repositories.UserRep;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpli implements UserService , UserDetailsService {
    private final UserRep userRep;
    private final RoleRep roleRep;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRep.findByUsername(username);
      if(user == null){
          log.error("user not found in the database");
          throw new UsernameNotFoundException("user not found in the database");
      }else{
          log.info("user found in the database {}",username);
      }
      Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
      user.getRoles().forEach(role ->
      {authorities.add(new SimpleGrantedAuthority(role.getName()));
      });
      return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

    @Override
    public User save(User user) {
        if(!user.getUsername().equals(null)){
            User user1 = userRep.findByUsername(user.getUsername());
            if (user1!=null)
                throw new RuntimeException("User already exist");
            else{
                log.info("saving user:{}", user.toString());
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRep.save(user);
            }
        }else{
            log.info("saving user:{}", user.toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRep.save(user);
        }

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
    public User findByUsername(String username) {
        return userRep.findByUsername(username);
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
         user.setRoles(u.getRoles());
        return userRep.save(user);
    }

    @Override
    public Boolean delete(String id) {
        log.info("delete user by id: {} ",id);
        userRep.deleteById(id);
        return true;
    }

    @Override
    public Role saveRole(Role role){
        log.info("saving new role {}",role.getName());
        return roleRep.save(role);
    }

    @Override
    public void addRoleToUser(String username , String roleName){
        log.info("Adding role {} to user  {}",roleName,username);
        User user = userRep.findByUsername(username);
        Role role = roleRep.findByName(roleName);
        user.getRoles().add(role);
        log.info("user after adding role",user.toString());
    }


}
