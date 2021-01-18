package nxtgen.keycoak.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 */
class DemoRepository {

	private List<DemoUser> users = new ArrayList<>();

	Connection connection = null;
	PreparedStatement ptmt = null;
	ResultSet resultSet = null;

	DemoRepository() {
//      Long created = System.currentTimeMillis();

//      users = Arrays.asList(
//              new DemoUser("1", "Fred", "Flintstone", true, created),
//              new DemoUser("2", "Wilma", "Flintstone", true, created),
//              new DemoUser("3", "Pebbles", "Flintstone", true, created),
//              new DemoUser("4", "Barney", "Rubble", true, created),
//              new DemoUser("5", "Betty", "Rubble", true, created),
//              new DemoUser("6", "Bam Bam", "Rubble", false, created)
//      );

		try {
			String queryString = "SELECT * FROM user;";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			ResultSetMetaData md = resultSet.getMetaData();
			int columns = md.getColumnCount();

			System.out.println("resultSet : " + resultSet);

			while (resultSet.next()) {
				for (int j = 1; j <= columns; j++) {
					if (j > 1)
						System.out.print(",  ");
					String columnValue = resultSet.getString(j);
					System.out.print(md.getColumnName(j) + " " + columnValue);
				}
				System.out.println("");

//				System.out.println(resultSet.getString("id"));
//				System.out.println(resultSet.getString("username"));
//				System.out.println(resultSet.getString("firstname"));
//				System.out.println(resultSet.getString("lastname"));
//				System.out.println(resultSet.getString("email"));
//				System.out.println(resultSet.getString("password"));
//				System.out.println(resultSet.getString("enabled"));
//				System.out.println(resultSet.getString("created"));

				DemoUser user = new DemoUser();
				user.setId(resultSet.getString("id"));
				user.setUsername(resultSet.getString("username"));
				user.setFirstName(resultSet.getString("firstname"));
				user.setLastName(resultSet.getString("lastname"));
				user.setEmail(resultSet.getString("email"));
				user.setPassword(resultSet.getString("password"));
				user.setEnabled(resultSet.getBoolean("enabled"));
				user.setCreated(resultSet.getLong("created"));

				users.add(user);
				System.out.println(users);
			}

//			System.out.println(users.size());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

//	public static void main(String[] args) {
//
//		DemoRepository d = new DemoRepository();
//
//	}

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	List<DemoUser> getAllUsers() {
		return users;
	}

	int getUsersCount() {
		return users.size();
	}

	DemoUser findUserById(String id) {
		return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
	}

	DemoUser findUserByUsernameOrEmail(String username) {
		return users.stream().filter(
				user -> user.getUsername().equalsIgnoreCase(username) || user.getEmail().equalsIgnoreCase(username))
				.findFirst().orElse(null);
	}

	List<DemoUser> findUsers(String query) {
		return users.stream().filter(user -> user.getUsername().contains(query) || user.getEmail().contains(query))
				.collect(Collectors.toList());
	}

	boolean validateCredentials(String username, String password) {
		return findUserByUsernameOrEmail(username).getPassword().equals(password);
	}

	boolean updateCredentials(String username, String password) {
		findUserByUsernameOrEmail(username).setPassword(password);
		return true;
	}

}
