package ma.premo.production.backend_prodctiont_managment.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor
@Data
public class ListStatistic {
    private String line;
    private List<StatisticMonth> listStatistics;
}
