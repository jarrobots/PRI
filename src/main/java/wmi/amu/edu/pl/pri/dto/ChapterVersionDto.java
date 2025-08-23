package wmi.amu.edu.pl.pri.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChapterVersionDto {

    private Date uploadTime;
    private String link;
    private String fileName;
    private Long uploaderId;
    private String uploaderFName;
    private String uploaderLName;

}
