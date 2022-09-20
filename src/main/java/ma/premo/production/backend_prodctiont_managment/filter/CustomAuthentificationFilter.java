package ma.premo.production.backend_prodctiont_managment.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.services.UserDetailsImpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j

public class CustomAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public static final String APPLICATION_JSON_VALUE = "application/json";
    ma.premo.production.backend_prodctiont_managment.models.User userConected = new ma.premo.production.backend_prodctiont_managment.models.User();


    String username;


    public  CustomAuthentificationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("username is {}",username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        //log.info("user ===>",userConected.toString());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        return authenticationManager.authenticate(authenticationToken);
       // throw  new RuntimeException("pb in request content");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
         UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        log.info("login with success");

        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() +4*60*60*1000))  //8*60*60*1000
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() +30*60*1000 ))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        /*
        response.setHeader("access_token",access_token);
        response.setHeader("refresh_token",refresh_token);

         */
        HashMap<String , String> tokens = new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);


        ObjectMapper mapper = new ObjectMapper();
        String userjsonstring = mapper.writeValueAsString(user);
        tokens.put("userjsonstring",userjsonstring);




        // Request methods you wish to allow
      //  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');

        // Request headers you wish to allow
       // res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');

        // Set to true if you need the website to include cookies in the requests sent
        // to the API (e.g. in case you use sessions)
     //   res.setHeader('Access-Control-Allow-Credentials', true);
        new ObjectMapper().writeValue(response.getOutputStream(),tokens);
    }
/*
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        response.setStatus(403);
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");

    }

 */


}
