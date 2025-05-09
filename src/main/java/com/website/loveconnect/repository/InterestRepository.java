package com.website.loveconnect.repository;

import com.website.loveconnect.entity.Interest;
import com.website.loveconnect.repository.query.InterestQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Integer> {
    // Get All Interest
    @Query(value = "SELECT i.* FROM interests i JOIN user_interests ui ON ui.interest_id = i.interest_id WHERE ui.user_id = :idUser", nativeQuery = true)
    List<Interest> getAllInterest(@Param("idUser") int idUser);

    List<Interest> getByInterestNameIn(List<String> interestNames);

    @Query(value = InterestQueries.FIND_ALL_INTEREST_NAME,nativeQuery = true)
    List<String> findAllInterestName();

    Optional<Interest> findByInterestName(String name);

}
