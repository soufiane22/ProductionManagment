package ma.premo.production.backend_prodctiont_managment.repositories;

import ma.premo.production.backend_prodctiont_managment.models.Account;
import ma.premo.production.backend_prodctiont_managment.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRep extends MongoRepository<Account,String> {
    public Account findByUsername(String name);
    public  Account findAccountByUser(User user);
}
