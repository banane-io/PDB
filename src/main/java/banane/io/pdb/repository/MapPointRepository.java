package banane.io.pdb.repository;

import banane.io.pdb.model.MapPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MapPointRepository extends JpaRepository<MapPoint, Long> {
    @Query("select m from MapPoint m where m.x >= :x1 and m.x <= :x2 and m.y >= :y1 and m.y <= :y2 order by m.y, m.x ")
    List<MapPoint> loadGrid(@Param("x1") Integer x1, @Param("x2") Integer x2, @Param("y1") Integer y1, @Param("y2") Integer y2);

    Optional<MapPoint> findMapPointByXAndY(Integer x, Integer y);
}
