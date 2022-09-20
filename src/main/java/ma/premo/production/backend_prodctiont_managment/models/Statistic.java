package ma.premo.production.backend_prodctiont_managment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Statistic  {

    private String leaderName;
    private int sumOutput;
    private float productivity;
    private float tauxScrap;

}


