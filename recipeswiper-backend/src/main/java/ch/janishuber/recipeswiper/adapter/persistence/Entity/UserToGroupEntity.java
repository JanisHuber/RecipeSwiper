package ch.janishuber.recipeswiper.adapter.persistence.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usertogroup")
public class UserToGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "group_id")
    private int group_id;

    public UserToGroupEntity() {
    }

    public UserToGroupEntity(int user_id, int group_id) {
        this.user_id = user_id;
        this.group_id = group_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
}
