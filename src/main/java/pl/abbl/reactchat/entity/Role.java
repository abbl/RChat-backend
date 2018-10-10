package pl.abbl.reactchat.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="authorities")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String authority;

    public Role(){}
}
