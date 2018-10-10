package pl.abbl.reactchat.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String username;
	private String password;
	private int active;

	public User(){}
}
