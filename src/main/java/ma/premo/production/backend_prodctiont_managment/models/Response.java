package ma.premo.production.backend_prodctiont_managment.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@SuperBuilder
@JsonInclude(NON_NULL )
public class Response {
    protected LocalDateTime timeStamp ;
    protected  int statusCode ;
    protected HttpStatus status;
    protected  String reason ;
    protected String message ;
    protected String developerMessage;
    protected Map<? , ?> data;
    protected Collection data1;
    protected Object object = new Object();
    protected JSONObject json;


}
