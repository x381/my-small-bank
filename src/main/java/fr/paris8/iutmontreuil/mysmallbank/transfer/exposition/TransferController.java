package fr.paris8.iutmontreuil.mysmallbank.transfer;

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

    @GetMapping("/transfers")
    public List<TransferDTO> getTransfers(@RequestParam(name = "tri") String tri) {
        return TransferMapper.toD
    }

    @PostMapping
    public ResponseEntity<TransferDTO> create(@RequestBody TransferDTO transferDTO) throws URISyntaxException {
        Transfer transfer = transferService.createTransfer(TransferMapper.toTransfer(transferDTO));
        return ResponseEntity.created(new URI("/transfers/" + transfer.getId())).body(TransferMapper.toDTO(transfer));
    }


}
