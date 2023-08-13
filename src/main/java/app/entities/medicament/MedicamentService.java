package app.entities.medicament;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import app.entities.categorieMedicament.CategorieMedicament;
import app.entities.categorieMedicament.CategorieMedicamentService;
import app.entities.medicament.dt.MedicamentInput;

@Service
public class MedicamentService {
    MedicamentRepository medicamentRepo;
    CategorieMedicamentService categorieMedicamentService;

    MedicamentService(MedicamentRepository medicamentRepo, CategorieMedicamentService categorieMedicamentService) {
        this.medicamentRepo = medicamentRepo;
        this.categorieMedicamentService = categorieMedicamentService;
    }

    public void createMedicamentIfNotExist(MedicamentInput input) {

        Optional<CategorieMedicament> categorieMedicament = categorieMedicamentService
                .getCategorieMedicamentById(input.idCategorieMedicament());
        if (categorieMedicament.isEmpty())
            return;

        try {
            double pret = Double.parseDouble(input.pret());

            medicamentRepo.save(new Medicament(input.numeMedicament(), categorieMedicament.get(), pret));
        } catch (NumberFormatException e) {
        }
    }

    public boolean exist(Medicament medicament) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnorePaths("id");
        Example<Medicament> example = Example.of(medicament, matcher);

        return medicamentRepo.findOne(example).isPresent();
    }

    @Deprecated
    public Optional<Medicament> getMedicamentByName(String numeMedicament) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnorePaths("id", "categorieMedicament", "pret");
        Example<Medicament> example = Example.of(new Medicament(numeMedicament, null, 0), matcher);

        return medicamentRepo.findOne(example);
    }

    public Page<Medicament> getMedicamentPage(int page) {
        return medicamentRepo.findAll(PageRequest.of(page, 10));
    }

    public Optional<Medicament> getMedicamentById(Long id) {
        return Optional.of(medicamentRepo.getReferenceById(id));
    }

    public List<Medicament> getAllMedicamente() {
        return medicamentRepo.findAll();
    }
}
