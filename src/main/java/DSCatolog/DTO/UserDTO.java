package DSCatolog.DTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import DSCatolog.entities.Role;
import DSCatolog.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO implements Serializable {

	
	private static final long serialVersionUID = 1L;

	
	private Long id;
	
	@Size(min = 2, message = "Minimum 2 characters" )
	@NotBlank(message = "Required field")
	private String firstName;
	
	@Size(min = 2, message = "Minimum 2 characters" )
	@NotBlank(message = "Required field")
	private String lastName;
	
	@NotBlank(message = "Required field")
	@Email(message = "Invalid imail")
	private String email;
	
	Set<RoleDTO> roles = new HashSet<>();
	
	public UserDTO() {
		
	}

	public UserDTO(Long id, String firstName, String lastName, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public UserDTO(User entity) {
		id = entity.getId();
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		email = entity.getEmail();
		
		for (Role role : entity.getRoles()) {
			RoleDTO roleDTO = new RoleDTO(role);
			this.roles.add(roleDTO);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}
	
	
}
