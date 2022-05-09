package ma.premo.production.backend_prodctiont_managment;

import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;
import ma.premo.production.backend_prodctiont_managment.models.User;
import ma.premo.production.backend_prodctiont_managment.models.Role;
import ma.premo.production.backend_prodctiont_managment.repositories.Notification_heurs_Rep;
import ma.premo.production.backend_prodctiont_managment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Calendar;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BackendProdctiontManagmentApplication implements CommandLineRunner {

    @Autowired
    private Notification_heurs_Rep notifRep;
    Calendar calendar = Calendar. getInstance();


    @Autowired
    UserService userService;
    public static void main(String[] args) {

        SpringApplication.run(BackendProdctiontManagmentApplication.class, args);
    }

    Notification_Heures notif1 ;

    @Override
    public void run(String... args ) throws Exception {

         //userService.saveRole(new Role(null , "ROLE_USER"));

        //userService.saveRole(new Role(null , "ROLE_LEADER"));
       // ArrayList lisRole = new ArrayList();
        //lisRole.add(new Role(null , "ROLE_LEADER"));
     //   userService.saveRole(new Role(null , "ROLE_LEADER"));
       // userService.saveRole(new Role(null , "ROLE_CONTROLER"));
        //notif1 = new Notification_Heures("1","2HG4FKJHD","Faiza","Nuit",calendar.getTime(),6,63,8,10,0,10);
      //  System.out.println(notif1.toString());
        // this.notifRep.save(notif1);
      //  User user = new User(null ,"AIT TALEB","walid","walid","0756342323","Chef Equipe","1234","walid@premo.com","1103",6434,lisRole);
       // userService.save(user);
         System.out.println("Server is running...");
    }


}
