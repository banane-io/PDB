package banane.io.pdb.model;

import javax.persistence.*;

@Entity
public class Player {

    private Long id;

    private String username;

    private User owner;

    private MapPoint currentZone;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",unique = true)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    private User user;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "map_point_id", nullable = false)
    public MapPoint getCurrentZone() {
        return currentZone;
    }

    public void setCurrentZone(MapPoint currentZone) {
        this.currentZone = currentZone;
    }
}
