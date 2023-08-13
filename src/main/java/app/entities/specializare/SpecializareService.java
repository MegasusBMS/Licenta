package app.entities.specializare;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import app.entities.specializare.dt.SpecializareInput;

@Service
public class SpecializareService {
    
    SpecializareRepository specializareRepository;

    SpecializareService(SpecializareRepository specializareRepository){
        this.specializareRepository=specializareRepository;
    }

    public void createSpecializare(SpecializareInput input){
        specializareRepository.save(new Specializare(input.numeSpecializare()));
    }

    public Optional<Specializare> getSpecializareById(long idSpecializare) {
        return specializareRepository.findById(idSpecializare);
    }

    public List<Specializare> getAllSpecializari(){
        return specializareRepository.findAll();
    }

}
