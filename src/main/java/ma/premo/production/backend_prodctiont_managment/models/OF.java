package ma.premo.production.backend_prodctiont_managment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.AUTO;

@Document(collection = "of")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OF {
    @Id
    @GeneratedValue(strategy = AUTO)
    private String id ;
    private String reference ;
    private String idProduit ;
    private String dateDebut;
    private String dateFin;
    private int quantiteDemande;
    private int quantiteFabriqué;

}
