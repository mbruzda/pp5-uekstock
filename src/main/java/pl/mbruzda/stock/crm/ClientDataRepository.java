package pl.mbruzda.stock.crm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ClientDataRepository extends
        JpaRepository<ClientData, Integer> {
}