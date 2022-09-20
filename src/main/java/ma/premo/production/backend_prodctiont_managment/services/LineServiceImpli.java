package ma.premo.production.backend_prodctiont_managment.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.premo.production.backend_prodctiont_managment.models.Groupe;
import ma.premo.production.backend_prodctiont_managment.models.Line;
import ma.premo.production.backend_prodctiont_managment.models.Produit;
import ma.premo.production.backend_prodctiont_managment.models.User;
import ma.premo.production.backend_prodctiont_managment.repositories.GroupeRep;
import ma.premo.production.backend_prodctiont_managment.repositories.LineRep;
import ma.premo.production.backend_prodctiont_managment.repositories.ProduitRep;
import ma.premo.production.backend_prodctiont_managment.repositories.UserRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class LineServiceImpli implements LineService{
    private final LineRep lineRep;
    private final ProduitRep produitRep;
    private final GroupeRep groupeRep;

    private final UserRep userRep;
    private final UserService userService;

    @Override
    public Line save(Line line) {
        log.info("saving line",line.getDesignation());
        return lineRep.save(line);
    }

    @Override
    public Collection<Line> getALL() {

        List<Line> listLines = lineRep.findAll();
        Collections.sort((List)listLines);
        return listLines;
    }

    @Override
    public Line get(String id) {
        log.info("fetching line of  "+id);
        return lineRep.findById(id).orElseThrow();
    }

    @Override
    public Line update(String id, Line line) {
        Line l = new Line();
        l= lineRep.findById(id).orElseThrow();
        l.setDesignation(line.getDesignation());
        l.setCentre(line.getCentre());

        /*************** update line in products ***************/
        List<Produit> listProduct = produitRep.findProduitByLineById(l.getId());

        if(listProduct != null && listProduct.size()>0)
        for(Produit p:listProduct){
            int index = IntStream.range(0, p.getListLines().size())
                    .filter(i -> p.getListLines().get(i).getId().equals(line.getId()))
                    .findFirst()
                    .orElse(-1);
            if(index != -1)
            p.getListLines().set(index,l);
            produitRep.save(p);
        }

        /*************** update line in groups ***************/
        List<Groupe> listGroups = groupeRep.getGroupeByLine(l.getId());
        if(listGroups != null && listGroups.size()>0)
            for(Groupe g:listGroups){
                int index = IntStream.range(0, g.getListLine().size())
                        .filter(i -> g.getListLine().get(i).getId().equals(line.getId()))
                        .findFirst()
                        .orElse(-1);
                if(index != -1)
                g.getListLine().set(index,l);
                groupeRep.save(g);
            }
        /*************** update line in Users ***************/
         List<User> listUsers = userRep.findByLine(l.getId());
        if(listUsers != null  && listUsers.size()>0)
            for(User u:listUsers){
                u.setLine(l);
                userService.update(u.getId(),u);
            }
        return lineRep.save(l);
    }

    @Override
    public Boolean delete(String id) {

        /*************** delete line in products ***************/
        List<Produit> listProduct = produitRep.findProduitByLineById(id);

        if(listProduct != null && listProduct.size()>0)
            for(Produit p:listProduct){
                int index = IntStream.range(0, p.getListLines().size())
                        .filter(i -> p.getListLines().get(i).getId().equals(id))
                        .findFirst()
                        .orElse(-1);
                System.out.println("index "+index);
                if(index != -1)
                    p.getListLines().remove(index);
                produitRep.save(p);
            }

        /*************** delete line in groups ***************/
        List<Groupe> listGroups = groupeRep.getGroupeByLine(id);

        if(listGroups != null && listGroups.size()>0)
            for(Groupe g:listGroups){
                int index = IntStream.range(0, g.getListLine().size())
                        .filter(i -> g.getListLine().get(i).getId().equals(id))
                        .findFirst()
                        .orElse(-1);
                if(index != -1)
                    g.getListLine().remove(index);
                groupeRep.save(g);
            }

        lineRep.deleteById(id);
        return true;
    }
}
