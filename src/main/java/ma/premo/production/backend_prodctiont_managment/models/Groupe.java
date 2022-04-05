package ma.premo.production.backend_prodctiont_managment.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Document(collection = "groupe")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Groupe {

    @Id()
    @GeneratedValue(strategy = AUTO)
    private String id;
    private String designation;
    private String shift;

    @OneToOne
    private User chefEquipe;
    @OneToMany
    private List<Line> listLine;
    @OneToMany
    private List<User> listOperateurs;

}
