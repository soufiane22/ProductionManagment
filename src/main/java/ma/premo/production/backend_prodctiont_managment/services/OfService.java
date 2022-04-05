package ma.premo.production.backend_prodctiont_managment.services;

import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;
import ma.premo.production.backend_prodctiont_managment.models.OF;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.Collection;

public interface OfService {
    OF save(OF of);
    Collection<OF> getALL();
    Collection<OF> getOfByProduct(String id);
    OF get(String id);
    OF update(String id, OF of );
    Boolean delete(String id);
}
