package ibm.application.repository;

import ibm.application.domain.Tarjeta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tarjeta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {

}
