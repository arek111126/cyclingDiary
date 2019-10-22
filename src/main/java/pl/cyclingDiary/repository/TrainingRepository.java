package pl.cyclingDiary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cyclingDiary.entity.Training;
import pl.cyclingDiary.entity.User;
import pl.cyclingDiary.model.Result;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Integer> {
    List<Training> findAllByUser(User u);

    @Query("SELECT t FROM Training t WHERE t.user.login = :login")
    List<Training> findAllByUserId(@Param("login") String login);

    Training findFirstById(int id);

    int deleteTrainingById(int id);


    @Query("SELECT new pl.cyclingDiary.model.Result(" +
            "u.login," +
            "count(t.distance) ," +
            "sum(t.distance) ," +
            "t.kcal ," +
            "avg(t.averageNew)) from Training t  join User u on u.id = t.user.id " +
            "where t.trainingDate BETWEEN :dateOne AND :dateTwo GROUP BY u.login ")
    List<Result> findByMonthAndYear(@Param("dateOne") String dateOne,@Param("dateTwo") String dateTwo);


}
