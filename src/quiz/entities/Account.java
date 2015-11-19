package quiz.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account",schema="app")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@JsonIgnore
	//@OneToOne
	private int id; 
	//create QS??????????????
	//@OneToMany//??????????????
	//@JoinColumn(name = "username")//????????
	private String username; 
	//@OneToMany//??????????????
	//@JoinColumn(name = "password")//????????
	private String password;
	
	
//	@ManyToMany(mappedBy = "accounts")
//	@JsonIgnore
//	private List<Quiz> quizzes;
	
	
//	public Account(String username, String password) {
//		super();
//		this.username = username;
//		this.password = password;
//	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	} 
}
