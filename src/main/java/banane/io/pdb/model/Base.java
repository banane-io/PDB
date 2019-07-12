package banane.io.pdb.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Base {

    private Long id;

    private MapPoint location;

    private Integer wood;

    private Integer stone;

    @JsonIgnore
    private Hero owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "map_point_id", nullable = false)
    public MapPoint getLocation() {
        return location;
    }

    public void setLocation(MapPoint location) {
        this.location = location;
    }

    @Transient
    @JsonGetter("location")
    public Long getLocationeJson() {
        return location.getId();
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hero_id",unique = true)
    public Hero getOwner() {
        return owner;
    }

    public void setOwner(Hero owner) {
        this.owner = owner;
    }

    public Integer getWood() {
        return wood;
    }

    public void setWood(Integer wood) {
        this.wood = wood;
    }

    public Integer getStone() {
        return stone;
    }

    public void setStone(Integer stone) {
        this.stone = stone;
    }

}