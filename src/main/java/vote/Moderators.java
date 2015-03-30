package vote;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

/*
 * @author Ashish Bende
 */
	
public class Moderators{
	@Id
	private  String id;
	
	@NotNull(message = "Name cannot be empty",groups={Checker.CheckerAll.class})
	@Length(max = 170)
	private  String name;
	@NotNull(message = "Email cannot be empty",groups={Checker.CheckerAll.class, Checker.CheckerFields.class})
	private  String email;
	
	@NotNull(message = "Password cannot be empty",groups={Checker.CheckerAll.class, Checker.CheckerFields.class})
	@Size(min=3,max=50)
	private  String password;		
	private  String created_at;
	
	
   Moderators() {
		super();
	}

	public Moderators(String id, String name , String email, String password, String currentTime){
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.created_at = currentTime;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
		
}