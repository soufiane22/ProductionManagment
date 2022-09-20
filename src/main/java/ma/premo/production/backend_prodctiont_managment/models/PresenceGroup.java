package ma.premo.production.backend_prodctiont_managment.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;


@Document(collection = "presenceGroup")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PresenceGroup {
    @Id
    @GeneratedValue(strategy = AUTO)
    private String id;
    private String group;
    private String shift;
    private String date;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    private String leaderId;
    private String leaderName;
    private String engineer;
    private int totalOperators;
    private Double sumHours;
    private List<Presence> listPresence;
    private String status = "Invalidate";
}
