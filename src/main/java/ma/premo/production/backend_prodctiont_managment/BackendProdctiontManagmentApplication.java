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
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.Access;
import java.text.SimpleDateFormat;
import java.util.*;


@CrossOrigin(origins = "*")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BackendProdctiontManagmentApplication extends SpringBootServletInitializer implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(BackendProdctiontManagmentApplication.class, args);
    }

    @GetMapping(path = "/")
    public String getHellow(){
        return "Hello world from Tomcat";
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return  new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowCredentials(true).
                    allowedHeaders("*").allowedMethods("*" +"").allowedOriginPatterns("*");
            }
            //.allowedOrigins("http://localhost:4200/")
        };
    }




    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration configuration = new CorsConfiguration();

      //  configuration.setAllowedOrigins(Arrays.asList("*"));
       // configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("Origin","Access-Control-Allow-Origin","Content-Type","Accept","Authorization","X-Request-With," +
                "Access-Control-Request-Methode","Access-Control-Request-Headers"));
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT","OPTIONS"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }

    /*
    @Autowired
    Notification_heurs_Rep notifRep;

     */
    @Override
    public void run(String... args ) throws Exception {
         System.out.println("Server is running...");
         /*
        Date dateS =new SimpleDateFormat("yyyy-MM-dd").parse("2022-6-22");
        Date dateE =new SimpleDateFormat("yyyy-MM-dd").parse("2022-6-30");
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateE);
        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date modifiedDate = cal.getTime();
        System.out.println("date end "+modifiedDate);
        Collection<Notification_Heures> listNotif = new ArrayList<>();
        listNotif = notifRep.findNotification_HeuresByCreatedAtAndIdLeaderAndLine(dateS,modifiedDate,"62a8693e327839594df81375","62793afe4d90ef209ac98c82");
        if(listNotif != null)
        System.out.println("list notofications ======>"+listNotif);

          */
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BackendProdctiontManagmentApplication.class);
    }
}
