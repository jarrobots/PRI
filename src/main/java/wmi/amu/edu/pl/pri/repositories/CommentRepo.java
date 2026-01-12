package wmi.amu.edu.pl.pri.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wmi.amu.edu.pl.pri.models.CommentModel;

import java.util.List;
import java.util.Optional;


public interface CommentRepo extends JpaRepository<CommentModel,Long> {

    @Query("SELECT c FROM CommentModel c WHERE c.versionModel.id= :versionId")
    CommentModel findCommentByVersionId(@Param("versionId") Long versionId);

    @Query("SELECT c FROM CommentModel c WHERE c.chapterModel.id = :chapterId")
    CommentModel findCommentByChapterId(@Param("chapterId") Long chapterId);

    @Query("SELECT c FROM CommentModel c WHERE (:versionId IS NULL OR c.versionModel.id = :versionId)"+
            " AND (:chapterId IS NULL OR c.chapterModel.id = :chapterId)")
    Optional<CommentModel> findCommentByVersionIdOrChapterId(@Param("versionId") Long versionId, @Param("chapterId") Long chapterId);
}