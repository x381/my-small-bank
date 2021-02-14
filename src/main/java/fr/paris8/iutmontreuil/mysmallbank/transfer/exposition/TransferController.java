package fr.paris8.iutmontreuil.mysmallbank.transfer.exposition;

import fr.paris8.iutmontreuil.mysmallbank.transfer.TransferMapper;
import fr.paris8.iutmontreuil.mysmallbank.transfer.domain.TransferService;
import fr.paris8.iutmontreuil.mysmallbank.transfer.domain.model.Order;
import fr.paris8.iutmontreuil.mysmallbank.transfer.domain.model.Transfer;
import fr.paris8.iutmontreuil.mysmallbank.transfer.exposition.dto.TransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping
    public List<TransferDTO> listAllTransfers(@RequestParam(name = "sort") Order order) {
        return TransferMapper.toDTOs(transferService.listAllTransfers(order));
    }

    @PostMapping
    public ResponseEntity<TransferDTO> create(@RequestBody TransferDTO transferDTO) throws URISyntaxException {
        Transfer transfer = transferService.createTransfer(TransferMapper.toTransfer(transferDTO));
        return ResponseEntity.created(new URI("/transfers/" + transfer.getId())).body(TransferMapper.toDTO(transfer));
    }


}
