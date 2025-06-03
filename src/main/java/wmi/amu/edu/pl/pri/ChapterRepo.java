package wmi.amu.edu.pl.pri;

import wmi.amu.edu.pl.pri.Chapters;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository is an interface that provides access to data in a database
 */
public interface ChapterRepo extends JpaRepository<Chapters, Integer> {
}
