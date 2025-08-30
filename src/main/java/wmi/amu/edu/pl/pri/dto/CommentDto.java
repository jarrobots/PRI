package wmi.amu.edu.pl.pri.dto;

import lombok.*;
import wmi.amu.edu.pl.pri.dto.modeldto.UserDataDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentDto {
    private Long id;
    private String text;
    private Long uploaderId;
    private String fName;
    private String lName;
    private Long versionId;
}
