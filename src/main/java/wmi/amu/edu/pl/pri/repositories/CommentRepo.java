package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.CommentModel;

import java.util.List;


public interface CommentRepo extends JpaRepository<CommentModel,Long> {

    @Query("SELECT c FROM CommentModel c WHERE c.versionModel.id= :versionId")
    List<CommentModel> findCommentById(@Param("versionId") Long versionId);
}
