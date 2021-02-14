package fr.paris8.iutmontreuil.mysmallbank.account.domain;

import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Address;
import fr.paris8.iutmontreuil.mysmallbank.common.exception.ValidationErrorException;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.common.ValidationError;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.HolderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HolderService {

    private final HolderRepository holderRepository;

    public HolderService(HolderRepository holderRepository) {
        this.holderRepository = holderRepository;
    }

    public List<Holder> listAllHolders() {
        return holderRepository.listAllHolders();
    }

    public Holder getHolder(String id) {
        return holderRepository.getHolder(id);
    }

    public Holder create(Holder holder) {
        List<ValidationError> validationErrors = validateHolder(holder);
        if (!validationErrors.isEmpty())
            throw new ValidationErrorException(validationErrors);

        return holderRepository.save(holder);
    }

    public Holder updateAddress(String id, Address address) {
        Holder holderToUpdate = getHolder(id);

        List<ValidationError> validationErrors = validateAddress(address);
        if (!validationErrors.isEmpty())
            throw new ValidationErrorException(validationErrors);
        else
            holderToUpdate = holderToUpdate.updateAddress(address);

        return holderRepository.save(holderToUpdate);
    }

    public Holder updateHolder(String id, Holder holder) {
        Holder holderToUpdate = getHolder(id);
        holderToUpdate = holderToUpdate.merge(holder);

        return holderRepository.save(holderToUpdate);
    }

    public Holder deleteHolder(String id) {
        Holder holderToDelete = getHolder(id);
        List<ValidationError> validationErrors = validateDelete(holderToDelete);
        if (!validationErrors.isEmpty())
            throw new ValidationErrorException(validationErrors);
        else
            return holderRepository.delete(holderToDelete);
    }

    private List<ValidationError> validateDelete(Holder holder) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if(!holder.getAccounts().isEmpty())
            validationErrors.add(new ValidationError("you still have account(s) in our bank."));

        return validationErrors;
    }

    private List<ValidationError> validateAddress(Address address) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if (address.getStreet() == null || address.getStreet().isEmpty())
            validationErrors.add(new ValidationError("address : street is not defined or empty."));

        if (address.getZipCode() == null || address.getZipCode().isEmpty())
            validationErrors.add(new ValidationError("address : zipCode is not defined or empty."));

        if (address.getCity() == null || address.getCity().isEmpty())
            validationErrors.add(new ValidationError("address : city is not defined or empty."));

        if (address.getCountry() == null || address.getCountry().isEmpty())
            validationErrors.add(new ValidationError("address : country is not defined or empty."));

        return validationErrors;
    }

    private List<ValidationError> validateHolder(Holder holder) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if (holder.getFirstName() == null || holder.getFirstName().isEmpty())
            validationErrors.add(new ValidationError("holder : firstName is not defined or empty."));

        if (holder.getLastName() == null || holder.getLastName().isEmpty())
            validationErrors.add(new ValidationError("holder : lastName is not defined or empty."));

        if (holder.getBirthDate() == null || holder.getBirthDate().toString().isEmpty())
            validationErrors.add(new ValidationError("holder : birthDate is not defined or empty."));
        else if (holder.getBirthDate().isAfter(LocalDate.now().minusYears(18)))
            validationErrors.add(new ValidationError("birthDate : you are too young."));

        if (holder.getAddress() == null)
            validationErrors.add(new ValidationError("holder : address is not defined."));
        else
            validationErrors.addAll(validateAddress(holder.getAddress()));

        return validationErrors;
    }

}
