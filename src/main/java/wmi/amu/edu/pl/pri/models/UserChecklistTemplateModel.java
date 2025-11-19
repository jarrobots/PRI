package wmi.amu.edu.pl.pri.models;

import jakarta.persistence.*;
import lombok.*;
import wmi.amu.edu.pl.pri.models.pri.UserDataModel;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Getter
@Setter
@Table(name = "thm_checklist_templates")
public class UserChecklistTemplateModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserDataModel user;

    @Column(name = "question")
    private String question;

}
