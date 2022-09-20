package ma.premo.production.backend_prodctiont_managment.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.AUTO;

@Document(collection = "line")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Line implements Comparable<Line> {

    @Id
    @GeneratedValue(strategy = AUTO)
    private String id;
    private String designation;
    private int centre;

    @Override
    public int compareTo(Line line) {
        return this.getDesignation().compareTo(line.getDesignation());
    }
}
