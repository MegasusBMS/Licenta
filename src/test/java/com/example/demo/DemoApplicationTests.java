package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import app.entities.animal.AnimalService;
import app.entities.animal.dt.AnimalInput;
import app.entities.categorie.CategorieAnimal;
import app.entities.categorie.CategorieAnimalService;
import app.entities.manopera.ManoperaService;
import app.entities.manopera.dt.ManoperaInput;
import app.entities.medic.MedicService;
import app.entities.medic.dt.MedicSignIn;
import app.entities.medicament.MedicamentService;
import app.entities.medicament.dt.MedicamentInput;
import app.entities.rasa.RasaAnimalService;
import app.entities.rasa.dt.RasaAnimalInput;
import app.entities.reteta.RetetaService;
import app.entities.reteta.dt.RetetaInput;
import app.entities.tratament.TratamentService;
import app.entities.tratament.dt.TratamentInput;
import app.entities.user.UserService;
import app.entities.user.dt.UserSignIn;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	MedicService medicService;
	@Autowired
	CategorieAnimalService categorieAnimalService;
	@Autowired
	RasaAnimalService rasaAnimalServic;
	@Autowired
	UserService userService;
	@Autowired
	AnimalService animalService;
	@Autowired
	MedicamentService medicamentService;
	@Autowired
	ManoperaService manoperaService;
	@Autowired
	TratamentService tratamentService;
	@Autowired
	RetetaService retetaService;

	@Test
	void addToDb(MedicService medicService, CategorieAnimalService categorieAnimalService, RasaAnimalService rasaAnimalService, UserService userService, AnimalService animalService, MedicamentService medicamentService, ManoperaService manoperaService, TratamentService tratamentService, RetetaService retetaService) {
		System.out.print("test");
		//medici
		medicService.createMedicIfNotExist(new MedicSignIn("Boieteanu Florin", "21111", "asd", "Specializare1"));
		medicService.createMedicIfNotExist(new MedicSignIn("Grabril Alex", "11111", "asd", "Specializare2"));
		medicService.createMedicIfNotExist(new MedicSignIn("Goku Mirela", "31112", "asd", "Specializare3"));
		medicService.createMedicIfNotExist(new MedicSignIn("Balan Eugen", "81111", "asd", "Specializare3"));
		medicService.createMedicIfNotExist(new MedicSignIn("Popescu Gabriela", "71111", "asd", "Specializare2"));
		medicService.createMedicIfNotExist(new MedicSignIn("Dobrescu Ionut", "31111", "asd", "Specializare1"));
		medicService.createMedicIfNotExist(new MedicSignIn("Dedescu Cornel", "71113", "asd", "Specializare4"));
		medicService.createMedicIfNotExist(new MedicSignIn("Carlig Alex", "91111", "asd", "Specializare3"));
		medicService.createMedicIfNotExist(new MedicSignIn("Popescu Mihai", "11112", "asd", "Specializare5"));
		medicService.createMedicIfNotExist(new MedicSignIn("Stoicescu David", "11113", "asd", "Specializare5"));

		//categorii animale
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Caine"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Pisica"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Iepure"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Arici"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Porumbel"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Chinchilla"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Hamster"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Perus"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Papagal"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Sarpe"));

		//rase animal
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Bichon Maltez", "Caine"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Bichon Frise", "Caine"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Beagle", "Caine"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Boxer German", "Caine"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Brac German", "Caine"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Bulldog Francez", "Caine"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Cane Corso","Caine"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Chihuahua", "Caine"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Ciobanesc German", "Caine"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Ciobanesc Belgian", "Caine"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Dalmatian", "Caine"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Exotic Shorthair", "Pisica"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Ragdoll", "Pisica"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("British Shorthair", "Pisica"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Persana", "Pisica"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Main Coon", "Pisica"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("American Shorthair", "Pisica"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Scottish Fold", "Pisica"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Sphynx", "Pisica"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Abyssinian", "Pisica"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Devon rex", "Pisica"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Flander", "Iepure"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("German", "Iepure"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Chinchilla", "Iepure"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Berbec", "Iepure"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("African", "Arici"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Asiatic", "Arici"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("European", "Arici"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Amur", "Arici"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Burta Goala", "Arici"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Brandt", "Arici"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Daurian", "Arici"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Hugh", "Arici"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Donek", "Porumbel"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Ghent Owl", "Porumbel"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Strasser", "Porumbel"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Gascogne", "Porumbel"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Long-Tail", "Chinchilla"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Short-Tail", "Chinchilla"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Syrian", "Hamster"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Winter White", "Hamster"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Campbell's Dwarf", "Hamster"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Roborovski", "Hamster"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Chinese", "Hamster"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Fallow", "Perus"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Cinnamon", "Perus"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Motat", "Perus"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Opalin", "Perus"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Laurel", "Perus"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("African Grey", "Papagal"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Cockatoos", "Papagal"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Macaws", "Papagal"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Parrotlet", "Papagal"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Senegal", "Papagal"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Cobra", "Sarpe"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Python", "Sarpe"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Viper", "Sarpe"));
		
		//Utilizatori
		userService.createUser(new UserSignIn("Angelina Mihaela","asd"));
		userService.createUser(new UserSignIn("Alexandru Bogdan","asd"));
		userService.createUser(new UserSignIn("Mihail David","asd"));
		userService.createUser(new UserSignIn("Soica Alex","asd"));
		userService.createUser(new UserSignIn("Draghici Gabriel","asd"));
		userService.createUser(new UserSignIn("Boieteanu Florin","asd"));

		//Animale
		animalService.createAnimalIfNotExist(new AnimalInput("Wafi", "3","ac199001-8813-1012-8188-13c029fc0005","1" ));
		animalService.createAnimalIfNotExist(new AnimalInput("Oscar", "2","ac199001-8813-1012-8188-13c029fc0005","14"));
		animalService.createAnimalIfNotExist(new AnimalInput("Leon", "5","ac199001-8813-1012-8188-13c029fc0005","7"));
		animalService.createAnimalIfNotExist(new AnimalInput("Winny", "3","ac199001-8813-1012-8188-13c029fc0005","36"));
		animalService.createAnimalIfNotExist(new AnimalInput("Ricu", "3","ac199001-8813-1012-8188-13c029f50004","56"));
		animalService.createAnimalIfNotExist(new AnimalInput("Mitu", "2","ac199001-8813-1012-8188-13c029f50004","54"));
		animalService.createAnimalIfNotExist(new AnimalInput("Roto", "3","ac199001-8813-1012-8188-13c029ed0003","42"));
		animalService.createAnimalIfNotExist(new AnimalInput("Ciufy", "2","ac199001-8813-1012-8188-13c029e50002","14"));
		animalService.createAnimalIfNotExist(new AnimalInput("Grovy", "3","ac199001-8813-1012-8188-13c029dc0001","1"));
		animalService.createAnimalIfNotExist(new AnimalInput("Moto", "2","ac199001-8813-1012-8188-13c029d40000","14"));
		animalService.createAnimalIfNotExist(new AnimalInput("Otto", "3","ac199001-8813-1012-8188-13c029d40000","24"));
		animalService.createAnimalIfNotExist(new AnimalInput("Balan", "2","ac199001-8813-1012-8188-13c029d40000","9"));

		//Medicamente
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Antibiotic"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Vaccin"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Analgezic"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Anestezic"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Antiparazitare"));

		//Manopere
		manoperaService.createManoperaIfNotExist(new ManoperaInput("Castrare"));
		manoperaService.createManoperaIfNotExist(new ManoperaInput("Tratare Infectie"));
		manoperaService.createManoperaIfNotExist(new ManoperaInput("Vaccinare"));
		manoperaService.createManoperaIfNotExist(new ManoperaInput("Analize"));
		manoperaService.createManoperaIfNotExist(new ManoperaInput("Deparazitare"));
		
		//Tratamente
		tratamentService.createTratement(new TratamentInput("Boieteanu Florin", "Vaccinare", "Wafi"));
		tratamentService.createTratement(new TratamentInput("Grabril Alex", "Vaccinare", "Oscar"));
		tratamentService.createTratement(new TratamentInput("Boieteanu Florin", "Vaccinare", "Leon"));
		tratamentService.createTratement(new TratamentInput("Goku Mirela", "Deparazitare", "Balan"));

		//Retete
		retetaService.createReteta(new RetetaInput("Boieteanu Florin", "Leon"));

	}

}
