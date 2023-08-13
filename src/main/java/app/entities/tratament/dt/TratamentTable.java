package app.entities.tratament.dt;

import java.util.Date;
import java.util.List;

public record TratamentTable(Long id, String numeMedic, String numeAnimal,List<String> medicamente,Date dataTratament, double pretManopera) {
    
}
