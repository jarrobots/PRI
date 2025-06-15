package wmi.amu.edu.pl.pri.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChapterVersionsDto {

    private List<ChapterVersionDto> versions;

}
