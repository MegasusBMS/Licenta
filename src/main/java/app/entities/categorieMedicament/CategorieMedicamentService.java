package app.entities.categorieMedicament;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import app.entities.categorieMedicament.dt.CategorieMedicamentInput;

@Service
public class CategorieMedicamentService {
    
    CategorieMedicamentRepository categorieMedicamentRepository;

    CategorieMedicamentService(CategorieMedicamentRepository categorieMedicamentRepository){
        this.categorieMedicamentRepository=categorieMedicamentRepository;
    }

    public void careateCategorieMedicament(CategorieMedicamentInput input){
        categorieMedicamentRepository.save(new CategorieMedicament(input.numeCategorieMedicament()));
    }

    public Optional<CategorieMedicament> getCategorieMedicamentById(long idCategorieMedicament) {
        return categorieMedicamentRepository.findById(idCategorieMedicament);
    }

    public List<CategorieMedicament> getAllCategoriiMedicamente() {
        return categorieMedicamentRepository.findAll();
    }

}
