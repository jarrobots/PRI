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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader", referencedColumnName = "id", insertable=false, updatable=false)
    private UserDataModel uploader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version", referencedColumnName = "id", insertable=false, updatable=false)
    private ChapterVersionModel versionModel;

    private String text;

    public CommentDto toCommentDto(){
        return CommentDto.builder()
                .id(id)
                .uploader(getUploader())
                .text(text)
                .versionId(versionModel.getId())
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
