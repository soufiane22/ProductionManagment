package ma.premo.production.backend_prodctiont_managment.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import ma.premo.production.backend_prodctiont_managment.models.Account;
import ma.premo.production.backend_prodctiont_managment.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ToString
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
    private String id;
    private String username;

    private String nom;
    private String prenom;
    private String tele;
    private String fonction;
    private String email;
    private int matricule;
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    public UserDetailsImpl(String id, String username, String nom, String prenom,String tele,String fonction, String email,int matricule, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nom = nom;
        this.prenom  = prenom;
        this.password = password;
        this.tele = tele;
        this.fonction = fonction;
        this.matricule = matricule;
        this.authorities = authorities;
    }




    public static UserDetailsImpl build(Account account) {
        List<GrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(
                account.getUser().getId(),
                account.getUsername(),
                account.getUser().getNom(),
                account.getUser().getPrenom(),
                account.getUser().getTele(),
                account.getUser().getFonction(),
                account.getUser().getEmail(),
                account.getUser().getMatricule(),
                account.getPassword(),
                authorities);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTele() {
        return tele;
    }

    public String getFonction() {
        return fonction;
    }

    public String getEmail() {
        return email;
    }

    public int getMatricule() {
        return matricule;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
