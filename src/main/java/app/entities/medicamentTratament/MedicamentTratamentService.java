package app.entities.medicamentTratament;

import java.util.Optional;

import org.springframework.stereotype.Service;

import app.entities.medicament.Medicament;
import app.entities.medicament.MedicamentService;
import app.entities.medicamentTratament.dt.MedicamentTratamentInput;
import app.entities.medicamentTratament.dt.TratamentMedicamentInput;
import app.entities.tratament.Tratament;
import app.entities.tratament.TratamentService;

@Service
public class MedicamentTratamentService {

    MedicamentTratamentRepository medicamentTratamentRepository;
    TratamentService tratamentService;
    MedicamentService medicamentService;

    MedicamentTratamentService(MedicamentTratamentRepository medicamentTratamentRepository, TratamentService tratamentService, MedicamentService medicamentService){
        this.medicamentTratamentRepository = medicamentTratamentRepository;
        this.tratamentService = tratamentService;
        this.medicamentService = medicamentService;
    }

    public void createMedicamentTratamentIfNotExist(TratamentMedicamentInput input){
        
        Optional<Tratament> tratament = tratamentService.getTratamentById(input.idTratament());
        if(tratament.isEmpty())
            return;
        
        for (Long idMedicament : input.medicamente()) {
            Optional<Medicament> medicament = medicamentService.getMedicamentById(idMedicament);
            if(medicament.isEmpty())
                continue;
            medicamentTratamentRepository.save(new MedicamentTratament(tratament.get(),medicament.get()));
        }

    }
    @Deprecated
    public void createMedicamentTratamentIfNotExist(MedicamentTratamentInput input){

    }


}
