package ch.janishuber.recipeswiper.adapter.persistence.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "groups")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "group_token")
    private String group_token;

    @Column(name = "name")
    private String name;

    public GroupEntity() {
    }

    public GroupEntity(String group_token, String name) {
        this.group_token = group_token;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup_token() {
        return group_token;
    }

    public void setGroup_token(String group_token) {
        this.group_token = group_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
