package fr.paris8.iutmontreuil.mysmallbank.account.infrastructure;

import fr.paris8.iutmontreuil.mysmallbank.account.HolderMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class HolderRepository {

    private final HolderDAO holderDAO;

    public HolderRepository(HolderDAO holderDAO) {
        this.holderDAO = holderDAO;
    }

    public Holder getHolder(String id) {
        return HolderMapper.toHolder(holderDAO.getOne(id));
    }

    public List<Holder> listAllHolders() {
        return holderDAO.findAll().stream()
                .map(HolderMapper::toHolder)
                .collect(Collectors.toList());
    }

    public Holder save(Holder holder) {
        HolderEntity entityToSave = HolderMapper.toEntity(holder);
        return HolderMapper.toHolder(holderDAO.save(entityToSave));
    }

    public Holder delete(Holder holder) {
        HolderEntity entityToDelete = HolderMapper.toEntity(holder);
        Holder deletedHolder = HolderMapper.toHolder(entityToDelete);
        holderDAO.delete(entityToDelete);
        return deletedHolder;
    }
}
