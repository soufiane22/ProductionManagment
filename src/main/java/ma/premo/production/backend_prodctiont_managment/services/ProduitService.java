package ma.premo.production.backend_prodctiont_managment.services;

import ma.premo.production.backend_prodctiont_managment.models.Notification_Heures;
import ma.premo.production.backend_prodctiont_managment.models.Produit;

import java.util.Collection;

public interface ProduitService {


    Produit save( Produit produit );

    Collection<Produit> list(int limit);

    Collection<Produit> getALL();

    Collection<Produit> getProductByLine(String idLine);

    Produit get(String id);

    Produit update(String id, Produit produit );

    Boolean delete(String id);


}
