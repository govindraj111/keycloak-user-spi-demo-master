package nxtgen.keycoak.user;

/**
 * 
 */
public class DemoUser {

	private String id;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private boolean enabled;
	private Long created;
	private String city;

	public DemoUser() {
	}

	public DemoUser(String id, String firstName, String lastName, boolean enabled, Long created, String city) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = (firstName + "." + lastName).toLowerCase();
		this.email = this.username + "@abc.com";
		this.password = firstName.toLowerCase();
		this.enabled = enabled;
		this.created = created;
		this.city = city;
	}

	public void setId(String id) {
		this.id = id;

	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
