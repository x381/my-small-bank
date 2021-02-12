package fr.paris8.iutmontreuil.mysmallbank.account.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferDAO extends JpaRepository<TransferEntity, String> {

    @Query(value = "DELETE FROM transfer WHERE account_from = :accountId OR account_to = :accountId", nativeQuery = true)
    void deleteTransfersOf(@Param("accountId") String accountId);
}
