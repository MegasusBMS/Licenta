package app.entities.medicamentTratament.dt;

import java.util.HashSet;

public record MedicamentTratamentInput(long idMedicament,HashSet<Long> tratamente) {
    
}
