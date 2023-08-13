package app.entities.diagnostic;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import app.entities.animal.Animal;
import app.entities.animal.AnimalService;
import app.entities.categorieDiagnostic.CategorieDiagnostic;
import app.entities.categorieDiagnostic.CategorieDiagnosticService;
import app.entities.diagnostic.dt.DiagnosticInput;

@Service
public class DiagnosticService {

    private DiagnosticRepository diagnosticRepository;
    private AnimalService animalService;
    private CategorieDiagnosticService categorieDiagnosticService;

    DiagnosticService(DiagnosticRepository diagnosticRepository,AnimalService animalService, CategorieDiagnosticService categorieDiagnosticService){
        this.diagnosticRepository = diagnosticRepository;
        this.animalService = animalService;
        this.categorieDiagnosticService = categorieDiagnosticService;
    }

    public Optional<Diagnostic> getDiagnosticById(long idDiagnostic) {
        return diagnosticRepository.findById(idDiagnostic);
    }

    public void createDiagnostic(DiagnosticInput diagnosticInput){
        
        Optional<Animal> animal = animalService.getAnimalById(Long.parseLong(diagnosticInput.idAnimal()));
        if(animal.isEmpty())
            return;
        
        Optional<CategorieDiagnostic> categorieDiagnostic = categorieDiagnosticService.getCategorieDiagnosticById(diagnosticInput.idCategorieD());
        if(categorieDiagnostic.isEmpty())
        return;

        Diagnostic diagnostic = new Diagnostic(diagnosticInput.numeDiagnostic(),diagnosticInput.specificatiiDiagnostic(),animal.get(),categorieDiagnostic.get());

        diagnosticRepository.save(diagnostic);

    }

    public List<Diagnostic> getAllDiagnostice() {
        return diagnosticRepository.findAll();
    }

    public Page<Diagnostic> getDiagnosticePage(int page) {
        return diagnosticRepository.findAll(PageRequest.of(page, 10));
    }
}
