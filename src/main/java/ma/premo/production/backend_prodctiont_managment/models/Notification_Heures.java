package ma.premo.production.backend_prodctiont_managment.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Document(collection = "notification_heures")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Notification_Heures implements Comparable<Notification_Heures>  {

    @Id
    @GeneratedValue(strategy = AUTO)
    private String id;

    private int OF;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produit",referencedColumnName = "id")
    private Produit produit;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ligne",referencedColumnName = "id")
    private Line ligne;
    private String idLeader;

    private String leaderName;
    private String shift;
    private String date ;
    private String remark;
    private int nbr_operateurs;
    private int total_h;
    private int h_sup;
    private int h_devolution;
    private int h_nouvau_projet;
    private int h_arrete ;
    private int h_normal;
    private String status = "No Validate";

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdAt ;

    private int totalOutput;
    private int totalScrap;

    private double standar_hours ;
    private double productivity ;
    private double scrapRatio ;

    public Notification_Heures(int OF,Line l , Produit p, String chef_equipe, String shift, String date, String remark,
                               int nbr_operateurs, int total_h, int h_sup, int h_devolution,
                               int heures_nouvau_projet, int h_arrete , int hNormal , Date createdAt) {
        this.OF = OF;
        this.ligne = l;
        this.produit = p;
        this.idLeader = chef_equipe;
        this.shift = shift;
        this.date = date;
        this.nbr_operateurs = nbr_operateurs;
        this.total_h = total_h;
        this.h_sup = h_sup;
        this.h_devolution = h_devolution;
        this.h_nouvau_projet = heures_nouvau_projet;
        this.h_arrete = h_arrete;
        this.remark = remark;
        this.h_normal = hNormal;
        this.createdAt = createdAt;

    }




    @Override
    public int compareTo(Notification_Heures not) {
        return this.getCreatedAt().compareTo(not.createdAt);
    }
}
