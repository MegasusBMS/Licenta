package app.entities.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User,String> {

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.userName = :userName, u.password = :password WHERE u.cnp = :cnp")
    void updateUserCredentialsByCnp(String userName, String password, long cnp);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.userName = :#{#user.userName}, u.cnp = :#{#user.cnp} WHERE u.UUID= :#{#user.UUID}")
    public void update(@Param("user") User user);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.userName = :#{#user.userName}, u.cnp = :#{#user.cnp}, u.password=:password WHERE u.UUID= :#{#user.UUID}")
    public void updateWithPassword(@Param("user") User user,@Param("password") String password);
}