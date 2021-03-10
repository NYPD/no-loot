package rip.noloot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import rip.noloot.domain.NoLootUser;

public interface UsersRepository extends CrudRepository<NoLootUser, Integer> {

    @Query("SELECT u" + " FROM NoLootUser u" + " WHERE u.battlenetId = :battlenetId")
    public NoLootUser findByBattleNetId(@Param("battlenetId") int battlenetId);

}
