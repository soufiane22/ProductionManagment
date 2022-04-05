package ma.premo.production.backend_prodctiont_managment;

import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;
import ma.premo.production.backend_prodctiont_managment.repositories.Notification_heurs_Rep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.Calendar;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BackendProdctiontManagmentApplication implements CommandLineRunner {

    @Autowired
    private Notification_heurs_Rep notifRep;
    Calendar calendar = Calendar. getInstance();
    public static void main(String[] args) {

        SpringApplication.run(BackendProdctiontManagmentApplication.class, args);
    }

    Notification_Heures notif1 ;

    @Override
    public void run(String... args) throws Exception {

        //notif1 = new Notification_Heures("1","2HG4FKJHD","Faiza","Nuit",calendar.getTime(),6,63,8,10,0,10);
      //  System.out.println(notif1.toString());
        // this.notifRep.save(notif1);
        System.out.println("Server is running...");
    }
}
