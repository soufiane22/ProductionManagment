package ma.premo.production.backend_prodctiont_managment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.AUTO;

@Document(collection = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private String id;
    private String nom;
    private String prenom;
    private String tele;
    private String fonction;
    private String password;
    private String email;
    private int matricule;

}
