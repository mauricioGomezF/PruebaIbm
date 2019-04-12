package ibm.application.web.rest;
import ibm.application.domain.Asesor;
import ibm.application.repository.AsesorRepository;
import ibm.application.web.rest.errors.BadRequestAlertException;
import ibm.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Asesor.
 */
@RestController
@RequestMapping("/api")
public class AsesorResource {

    private final Logger log = LoggerFactory.getLogger(AsesorResource.class);

    private static final String ENTITY_NAME = "asesor";

    private final AsesorRepository asesorRepository;

    public AsesorResource(AsesorRepository asesorRepository) {
        this.asesorRepository = asesorRepository;
    }

    /**
     * POST  /asesors : Create a new asesor.
     *
     * @param asesor the asesor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new asesor, or with status 400 (Bad Request) if the asesor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/asesors")
    public ResponseEntity<Asesor> createAsesor(@Valid @RequestBody Asesor asesor) throws URISyntaxException {
        log.debug("REST request to save Asesor : {}", asesor);
        if (asesor.getId() != null) {
            throw new BadRequestAlertException("A new asesor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Asesor result = asesorRepository.save(asesor);
        return ResponseEntity.created(new URI("/api/asesors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /asesors : Updates an existing asesor.
     *
     * @param asesor the asesor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated asesor,
     * or with status 400 (Bad Request) if the asesor is not valid,
     * or with status 500 (Internal Server Error) if the asesor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/asesors")
    public ResponseEntity<Asesor> updateAsesor(@Valid @RequestBody Asesor asesor) throws URISyntaxException {
        log.debug("REST request to update Asesor : {}", asesor);
        if (asesor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Asesor result = asesorRepository.save(asesor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, asesor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /asesors : get all the asesors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of asesors in body
     */
    @GetMapping("/asesors")
    public List<Asesor> getAllAsesors() {
        log.debug("REST request to get all Asesors");
        return asesorRepository.findAll();
    }

    /**
     * GET  /asesors/:id : get the "id" asesor.
     *
     * @param id the id of the asesor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the asesor, or with status 404 (Not Found)
     */
    @GetMapping("/asesors/{id}")
    public ResponseEntity<Asesor> getAsesor(@PathVariable Long id) {
        log.debug("REST request to get Asesor : {}", id);
        Optional<Asesor> asesor = asesorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(asesor);
    }

    /**
     * DELETE  /asesors/:id : delete the "id" asesor.
     *
     * @param id the id of the asesor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/asesors/{id}")
    public ResponseEntity<Void> deleteAsesor(@PathVariable Long id) {
        log.debug("REST request to delete Asesor : {}", id);
        asesorRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
