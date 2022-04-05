package ma.premo.production.backend_prodctiont_managment.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;
import ma.premo.production.backend_prodctiont_managment.models.OF;
import ma.premo.production.backend_prodctiont_managment.repositories.Notification_heurs_Rep;
import ma.premo.production.backend_prodctiont_managment.repositories.OfRep;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class OfServiceImli implements OfService{

    private final OfRep ofRep;

    @Override
    public OF save(OF of) {
        log.info("saving OF:{}", of);
        return ofRep.save(of);
    }

    //get toutes les OFs
    @Override
    public Collection<OF> getALL() {
        log.info("fetching all of ");
        return ofRep.findAll();
    }

    @Override
    public Collection<OF> getOfByProduct(String id) {
        log.info("fetching of  by product");
        return ofRep.findOFByIdProduit(id);
    }

    @Override
    public OF get(String id) {
        log.info("fetching of  "+id);
        return ofRep.findById(String.valueOf(id)).get();
    }

    @Override
    public OF update(String id, OF Of) {
        OF of = ofRep.findById(id).orElseThrow();
        of.setIdProduit(Of.getIdProduit());
        of.setReference(Of.getReference());
        of.setDateDebut(Of.getDateDebut());
        of.setDateFin(Of.getDateFin());
        of.setQuantiteDemande(Of.getQuantiteDemande());
        of.setQuantiteFabriqué(Of.getQuantiteFabriqué());

        return ofRep.save(of);
    }

    @Override
    public Boolean delete(String id) {
        log.info("delete OF by id: {} ",id);
        ofRep.deleteById(id);
        return true;
    }
}
