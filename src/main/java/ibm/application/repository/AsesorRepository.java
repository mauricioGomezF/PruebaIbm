package ibm.application.repository;

import ibm.application.domain.Asesor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Asesor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AsesorRepository extends JpaRepository<Asesor, Long> {

}
