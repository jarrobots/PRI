package wmi.amu.edu.pl.pri.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChapterVersionsDto {

    private List<ChapterVersionDto> versions;

}
