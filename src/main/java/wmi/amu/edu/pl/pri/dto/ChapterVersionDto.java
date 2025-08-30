package wmi.amu.edu.pl.pri.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChapterVersionDto {

    private Long id;
    private Date uploadTime;
    private String link;
    private String fileName;
    private Long uploaderId;
    private String uploaderFName;
    private String uploaderLName;

}
