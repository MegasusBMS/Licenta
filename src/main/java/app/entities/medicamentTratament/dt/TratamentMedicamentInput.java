package app.entities.medicamentTratament.dt;

import java.util.HashSet;

public record TratamentMedicamentInput(long idTratament,HashSet<Long> medicamente) {
    
}
