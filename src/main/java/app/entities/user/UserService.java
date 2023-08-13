package app.entities.user;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import app.entities.user.dt.UserInput;
import app.entities.user.dt.UserSignIn;

@Service
public class UserService {

    private UserRepository userRepo;

    UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> createUser(UserInput input) {
        Optional<User> user = getUserByCNP(input.cnp());
        if (user.isPresent())
            return Optional.empty();

        return Optional
                .of(userRepo.save(new User(input.userName(), DigestUtils.sha256Hex(input.password()), input.cnp())));
    }

    public Optional<User> getUserByCNP(long cnp) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnorePaths("uuid", "password", "userName");
        Example<User> example = Example.of(new User(cnp), matcher);

        return userRepo.findOne(example);
    }

    @Deprecated
    public boolean createUser(UserInterface user) {
        if (getUserByUserName(user).isPresent())
            return false;
        userRepo.save(new User(user.userName(), DigestUtils.sha256Hex(user.password())));
        return true;
    }

    public Optional<User> LogInUser(UserInterface user) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnorePaths("uuid", "cnp");
        Example<User> example = Example.of(new User(user.userName(), DigestUtils.sha256Hex(user.password())), matcher);

        return userRepo.findOne(example);
    }

    public Optional<User> getUserByUserName(UserInterface user) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnorePaths("uuid", "password", "cnp");
        Example<User> example = Example.of(new User(user.userName(), null), matcher);

        return userRepo.findOne(example);
    }

    public Optional<User> getUserByUserName(String userName) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnorePaths("uuid", "password", "cnp");
        Example<User> example = Example.of(new User(userName, null), matcher);

        return userRepo.findOne(example);
    }

    public Optional<User> getUserByUUID(String uuid) {
        return userRepo.findById(uuid);
    }

    public Page<User> getUserPage(int page) {
        return userRepo.findAll(PageRequest.of(page, 10));
    }

    public List<User> getAllUsers() {
        List<User> users = userRepo.findAll();

        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getCnp() == 0) {
                iterator.remove();
            }
        }

        return users;
    }

    public void updateUser(UserSignIn user) {
        userRepo.updateUserCredentialsByCnp(user.userName(), DigestUtils.sha256Hex(user.password()),
                Long.parseLong(user.cnp()));
    }

    public void update(User user) {

        Optional<User> userExist = getUserByCNP(user.getCnp());

        if(userExist.isPresent())
            if(!user.getUUID().equals(userExist.get().getUUID()))
                return;

        if ("".equals(user.password()) || Optional.of(user.password()).isEmpty())
            userRepo.update(user);
        else
            userRepo.updateWithPassword(user,DigestUtils.sha256Hex(user.password()));
    }
}
