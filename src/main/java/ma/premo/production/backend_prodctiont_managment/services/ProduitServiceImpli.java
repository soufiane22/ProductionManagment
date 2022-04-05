package ma.premo.production.backend_prodctiont_managment.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.Produit;
import ma.premo.production.backend_prodctiont_managment.repositories.ProduitRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.springframework.data.domain.PageRequest.of;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ProduitServiceImpli implements ProduitService{
    private final ProduitRep produitRep;
    @Override
    public Produit save(Produit produit) {
        log.info("saving product:{}", produit.toString());
        return produitRep.save(produit);
    }

    @Override
    public Collection<Produit> list(int limit) {
        return  produitRep.findAll(of(0,limit)).toList();
    }

    @Override
    public Collection<Produit> getALL() {
        log.info("fetching all products ");
        return  produitRep.findAll();
    }

    @Override
    public Collection<Produit> getProductByLine(String idLine) {
        log.info("fetching products by line ");
        return produitRep.findProduitByIdLigne(idLine);
    }

    @Override
    public Produit get(String id) {
        log.info("fetching all products",id);
        return produitRep.findById(String.valueOf(id)).get();
    }

    @Override
    public Produit update(String id, Produit produit) {
        Produit p = new Produit();
        p = produitRep.findById(id).orElseThrow();
        p.setDesignation(produit.getDesignation());
        p.setReference(produit.getReference());
        p.setIdLigne(produit.getIdLigne());
        return produitRep.save(p);
    }

    @Override
    public Boolean delete(String id) {
        log.info("delete produit by id: {} ",id);
        produitRep.deleteById(id);
        return true;
    }

}
