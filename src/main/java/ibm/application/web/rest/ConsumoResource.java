package ibm.application.web.rest;
import ibm.application.domain.Consumo;
import ibm.application.repository.ConsumoRepository;
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
 * REST controller for managing Consumo.
 */
@RestController
@RequestMapping("/api")
public class ConsumoResource {

    private final Logger log = LoggerFactory.getLogger(ConsumoResource.class);

    private static final String ENTITY_NAME = "consumo";

    private final ConsumoRepository consumoRepository;

    public ConsumoResource(ConsumoRepository consumoRepository) {
        this.consumoRepository = consumoRepository;
    }

    /**
     * POST  /consumos : Create a new consumo.
     *
     * @param consumo the consumo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consumo, or with status 400 (Bad Request) if the consumo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consumos")
    public ResponseEntity<Consumo> createConsumo(@Valid @RequestBody Consumo consumo) throws URISyntaxException {
        log.debug("REST request to save Consumo : {}", consumo);
        if (consumo.getId() != null) {
            throw new BadRequestAlertException("A new consumo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Consumo result = consumoRepository.save(consumo);
        return ResponseEntity.created(new URI("/api/consumos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consumos : Updates an existing consumo.
     *
     * @param consumo the consumo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consumo,
     * or with status 400 (Bad Request) if the consumo is not valid,
     * or with status 500 (Internal Server Error) if the consumo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consumos")
    public ResponseEntity<Consumo> updateConsumo(@Valid @RequestBody Consumo consumo) throws URISyntaxException {
        log.debug("REST request to update Consumo : {}", consumo);
        if (consumo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Consumo result = consumoRepository.save(consumo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consumo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consumos : get all the consumos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of consumos in body
     */
    @GetMapping("/consumos")
    public List<Consumo> getAllConsumos() {
        log.debug("REST request to get all Consumos");
        return consumoRepository.findAll();
    }

    /**
     * GET  /consumos/:id : get the "id" consumo.
     *
     * @param id the id of the consumo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consumo, or with status 404 (Not Found)
     */
    @GetMapping("/consumos/{id}")
    public ResponseEntity<Consumo> getConsumo(@PathVariable Long id) {
        log.debug("REST request to get Consumo : {}", id);
        Optional<Consumo> consumo = consumoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(consumo);
    }

    /**
     * DELETE  /consumos/:id : delete the "id" consumo.
     *
     * @param id the id of the consumo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consumos/{id}")
    public ResponseEntity<Void> deleteConsumo(@PathVariable Long id) {
        log.debug("REST request to delete Consumo : {}", id);
        consumoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
