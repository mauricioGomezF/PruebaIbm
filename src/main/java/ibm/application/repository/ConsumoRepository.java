package ibm.application.repository;

import ibm.application.domain.Consumo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Consumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumoRepository extends JpaRepository<Consumo, Long> {

}
