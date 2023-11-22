package com.jsj.GTA.domain.stamps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Stamps 의 CRUD 를 책임짐
 * */
public interface StampsRepository extends JpaRepository<Stamps, Long> {

    @Query("SELECT s FROM Stamps s ORDER BY s.id DESC")
    List<Stamps> findAllDesc();

    @Query("SELECT s FROM Stamps s WHERE s.usersId = :id ORDER BY s.id DESC")
    List<Stamps> findByUserIdDesc(Long id);

    @Query("SELECT s FROM Stamps s WHERE s.touristAttractionsId = :id ORDER BY s.id DESC")
    List<Stamps> findByTouristAttractionsIdDesc(String id);

    @Query("SELECT s FROM Stamps s WHERE s.usersId = :userId and s.touristAttractionsId = :touristAttractionsId")
    Optional<Stamps> findByUserIdAndTouristAttractionsId(Long userId, String touristAttractionsId);


    @Query("SELECT COUNT(s.id) FROM Stamps s WHERE s.usersId = :id")
    int getUsersStampsCount(Long id);

    @Query("SELECT NEW com.jsj.GTA.domain.stamps.UsersStampCountDto(u.id, COUNT(s.id)) FROM Stamps s JOIN Users u ON u.id = s.usersId GROUP BY u.id")
    List<UsersStampCountDto> findByUsersRankWithStampCountDesc();

    @Query(value = "SELECT u.id, COUNT(s.id) FROM Stamps s JOIN Users u ON u.id = s.usersId GROUP BY u.id LIMIT :limit",nativeQuery = true)
    List<UsersStampCountDto> findByUsersRankWithStampCountLimitDesc(int limit);

    @Query("SELECT s FROM Stamps s WHERE s.touristAttractionsId = :id AND s.issueDate LIKE :lastMonth% ORDER BY s.id DESC")
    List<Stamps> findByTouristAttractionsIdDescLastMonth(String id, String lastMonth);
}