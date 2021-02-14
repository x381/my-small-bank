package fr.paris8.iutmontreuil.mysmallbank.account.exposition;

import fr.paris8.iutmontreuil.mysmallbank.account.HolderMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.HolderService;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AddressDTO;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.HolderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/holders")
public class HolderController {

    private final HolderService holderService;

    public HolderController(HolderService holderService) {
        this.holderService = holderService;
    }

    @GetMapping
    public List<HolderDTO> listAllHolders() {
        return HolderMapper.toDTOs(holderService.listAllHolders());
    }

    @GetMapping("/{id}")
    public HolderDTO getHolder(@PathVariable("id") String id) {
        return HolderMapper.toDTO(holderService.getHolder(id));
    }

    @PostMapping
    public ResponseEntity<HolderDTO> create(@RequestBody HolderDTO holderDTO) throws URISyntaxException {
        Holder holder = holderService.create(HolderMapper.toHolder(holderDTO));
        return ResponseEntity.created(new URI("/holders/" + holder.getId())).body(HolderMapper.toDTO(holder));
    }

    @PutMapping("/{id}/address")
    public HolderDTO updateAddress(@PathVariable("id") String id, @RequestBody AddressDTO addressDTO) {
        Holder holder = holderService.updateAddress(id, HolderMapper.toAddress(addressDTO));
        return HolderMapper.toDTO(holder);
    }

    @DeleteMapping("/{id}")
    public HolderDTO deleteHolder(@PathVariable("id") String id) {
        Holder holder = holderService.deleteHolder(id);
        return HolderMapper.toDTO(holder);
    }

    @PatchMapping("/{id}")
    public HolderDTO updateHolder(@PathVariable("id") String id, @RequestBody HolderDTO holderDTO) {
        Holder holder = holderService.updateHolder(id, HolderMapper.toHolder(holderDTO));
        return HolderMapper.toDTO(holder);
    }
}
