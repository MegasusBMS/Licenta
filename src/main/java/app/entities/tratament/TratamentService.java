package app.entities.tratament;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import app.entities.animal.AnimalService;
import app.entities.diagnostic.Diagnostic;
import app.entities.diagnostic.DiagnosticService;
import app.entities.medic.Medic;
import app.entities.medic.MedicService;
import app.entities.medicament.Medicament;
import app.entities.medicament.MedicamentService;
import app.entities.medicamentTratament.MedicamentTratament;
import app.entities.medicamentTratament.MedicamentTratamentRepository;
import app.entities.medicamentTratament.dt.TratamentMedicamentInput;
import app.entities.tratament.dt.TratamentInput;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TratamentService {

    TratamentRepository tratamentRepo;
    MedicService medicService;
    MedicamentService medicamentService;
    AnimalService animalService;
    DiagnosticService diagnosticService;
    MedicamentTratamentRepository medicamentTratamentRepository;

    public Page<Tratament> getTratamentPage(int page) {
        return tratamentRepo.findAll(PageRequest.of(page, 10));
    }

    public void createTratement(TratamentInput tratament) {
        Optional<Medic> medic = medicService.getMedicById(tratament.idMedic());
        if (medic.isEmpty())
            return;

        Optional<Diagnostic> diagnostic = diagnosticService.getDiagnosticById(tratament.idDiagnostic());
        if (diagnostic.isEmpty())
            return;

        String medicamente = tratament.listaMedicamente().substring(0, tratament.listaMedicamente().length() - 1);
        String[] idMedicamente = medicamente.split(";");
        HashSet<Long> listaMedicamente = new HashSet<>();
        for (String string : idMedicamente) {
            if(string.equals(""))
                continue;
            Optional<Medicament> medicament = medicamentService.getMedicamentById(Long.parseLong(string));
            if (medicament.isPresent())
                listaMedicamente.add(medicament.get().getId());
        }

        Tratament tratamentAsTratament;

        try{
        tratamentAsTratament = tratamentRepo.save(new Tratament(medic.get(), diagnostic.get(),Double.parseDouble(tratament.pretManopera())));
        
        createMedicamentTratamentIfNotExist(
            new TratamentMedicamentInput(tratamentAsTratament.getId(), listaMedicamente));
        }catch(NumberFormatException e){

        }
    }

    public Optional<Tratament> getTratamentById(Long id) {
        return Optional.of(tratamentRepo.getReferenceById(id));
    }

    private void createMedicamentTratamentIfNotExist(TratamentMedicamentInput input) {

        Optional<Tratament> tratament = getTratamentById(input.idTratament());
        if (tratament.isEmpty())
            return;

        for (Long idMedicament : input.medicamente()) {
            Optional<Medicament> medicament = medicamentService.getMedicamentById(idMedicament);
            if (medicament.isEmpty())
                continue;

            MedicamentTratament mt = new MedicamentTratament(tratament.get(),medicament.get());
            medicamentTratamentRepository.save(mt);
        }

    }

    public List<Tratament> getAllTratamente() {
        return tratamentRepo.findAll();
    }
}
