package ma.premo.production.backend_prodctiont_managment.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Document(collection = "produit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Produit implements Comparable<Produit>  {

    @Id
    @GeneratedValue(strategy = AUTO)
    private String id;
    private List<Line> listLines;
    private String designation;
    private String reference;
    private double tc;

    @Override
    public int compareTo(Produit p) {
        return this.getReference().compareTo(p.getReference());
    }
}
