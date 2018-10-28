package pl.abbl.reactchat.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="authorities")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String authority;

    public Role(){}

    public Role(String username, String authority){
        this.username = username;
        this.authority = authority;
    }
}
