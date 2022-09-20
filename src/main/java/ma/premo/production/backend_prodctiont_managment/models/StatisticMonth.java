package ma.premo.production.backend_prodctiont_managment.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatisticMonth {

    @Id
    @GeneratedValue(strategy = AUTO)
    private String id;
    private Line line;
    private Produit reference;
    private String month;
    private String date;
    private String type;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    private double standardHours;
    private int year;
    private int totalHours;
    private int nvah;
    private int output =0 ;
    private int scrap =0;
    private double productivity =0;
    private double tauxScrap =0;
}
