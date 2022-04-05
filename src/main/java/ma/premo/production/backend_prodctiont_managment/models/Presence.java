package ma.premo.production.backend_prodctiont_managment.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Document(collection = "presence")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Presence {
    @Id
    @GeneratedValue(strategy = AUTO)
    private String id;
    private String date;
    private String shift;
    private String etat;
    private int nbrHeurs;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ligne",referencedColumnName = "id")
    private Line line;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user",referencedColumnName = "id")
    private User chefEquipe;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user",referencedColumnName = "id")
    private User operateur;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user",referencedColumnName = "id")
    private User ingenieur;

}
