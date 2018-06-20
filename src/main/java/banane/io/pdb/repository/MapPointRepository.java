package banane.io.pdb.repository;

import banane.io.pdb.model.MapPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapPointRepository extends JpaRepository<MapPoint, Long> {
}
