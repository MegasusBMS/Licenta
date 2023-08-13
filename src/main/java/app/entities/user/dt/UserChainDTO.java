package app.entities.user.dt;

import java.util.List;

import app.entities.animal.dt.AnimalChainDTO;

public record UserChainDTO (String UUID, String userName, List<AnimalChainDTO> animale){
    
}
