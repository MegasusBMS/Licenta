package app.entities.user;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.stereotype.Component;

import app.entities.animal.Animal;
import app.entities.user.dt.UserTable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter

public class User implements UserInterface{

    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
        generator = "UUID"
    )
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
        parameters = {
            @Parameter(
                name = "uuid_gen_strategy_class",
                value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
            )
        }

    )
    @Id
    private String UUID;
    @Column(unique = true)
    private long cnp;
    private String userName;
    private String password;
    @OneToMany(mappedBy = "stapan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Animal> animale;

    public User(String userName,String password){
        this.userName = userName;
        this.password = password;
    }

    public User(String userName,String password,long cnp){
        this.userName = userName;
        this.password = password;
        this.cnp=cnp;
    }

    public User(long cnp){
        this.cnp=cnp;
    }

    @Override
    public String userName() {
        return getUserName();
    }
    
    @Override
    public String password() {
        return getPassword();
    }

    public UserTable toUserTable(){
        return new UserTable(UUID,userName,cnp);
    }
}
