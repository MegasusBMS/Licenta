package app.entities.categorieDiagnostic;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import app.entities.categorieDiagnostic.dt.CategorieDiagnosticInput;
import app.entities.categorieDiagnostic.dt.CategorieDiagnosticTable;

@Service
public class CategorieDiagnosticService {

    CategorieDiagnosticRepository categorieDiagnosticRepository;

    CategorieDiagnosticService(CategorieDiagnosticRepository categorieDiagnosticRepository){
        this.categorieDiagnosticRepository=categorieDiagnosticRepository;
    }

    public Optional<CategorieDiagnostic> getCategorieDiagnosticById(long idCategorieDiagnostic) {
        return categorieDiagnosticRepository.findById(idCategorieDiagnostic);
    }

    public void createCategorieDiagnostic(CategorieDiagnosticInput categorieDiagnosticInput){
        categorieDiagnosticRepository.save(new CategorieDiagnostic(categorieDiagnosticInput.numeCategorieDiagnostic()));
    }

    public List<CategorieDiagnostic> getAllCategoriiDiagnostice() {
        return categorieDiagnosticRepository.findAll();
    }
    
}
