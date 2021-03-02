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

				DemoUser user = new DemoUser();
				user.setId(resultSet.getString("id"));
				user.setUsername(resultSet.getString("username"));
				user.setFirstName(resultSet.getString("firstname"));
				user.setLastName(resultSet.getString("lastname"));
				user.setEmail(resultSet.getString("email"));
				user.setPassword(resultSet.getString("password"));
				user.setEnabled(resultSet.getBoolean("enabled"));
				user.setCreated(resultSet.getLong("created"));
				user.setCity(resultSet.getString("city"));

				users.add(user);
				System.out.println(users);
			}

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
