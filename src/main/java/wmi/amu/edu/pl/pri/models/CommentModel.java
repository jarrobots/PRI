package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wmi.amu.edu.pl.pri.dto.CommentDto;
import wmi.amu.edu.pl.pri.dto.modeldto.UserDataDto;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "thm_comment")
public class CommentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter", referencedColumnName = "id")
    private ChapterModel chapterModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version", referencedColumnName = "id")
    private ChapterVersionModel versionModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader", referencedColumnName = "id")
    private UserDataModel uploader;

    @Column(columnDefinition = "text")
    private String text;

    public CommentDto toCommentDto(){
        UserDataDto model = getUploader();

        Long versionId = (versionModel != null) ? versionModel.getId() : null;
        Long chapterId = (chapterModel != null) ? chapterModel.getId() : null;

        return CommentDto.builder()
                .id(id)
                .uploaderId(model.getId())
                .text(text)
                .versionId(versionId)
                .chapterId(chapterId)
                .fName(model.getFName())
                .lName(model.getLName())
                .build();
    }
    private UserDataDto getUploader(){
        if(uploader == null){
            uploader = new UserDataModel();
            uploader.setId((long) -1);
            uploader.setEmail("");
            uploader.setFirstName("");
            uploader.setLastName("");
            uploader.setIndexNumber("");
        }
        return uploader.toUserDataDto();
    }

}
