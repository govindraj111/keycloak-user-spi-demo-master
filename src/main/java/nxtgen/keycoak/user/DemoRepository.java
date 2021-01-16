package nxtgen.keycoak.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 */
class DemoRepository {

    private final List<DemoUser> users = null;
    
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
//			List<Map<String, Object>> list = new ArrayList<>();
			
			System.out.println("resultSet : " + resultSet);
			
			while (resultSet.next()) {
				
				Map<String, Object> row = new HashMap<>(columns);
		        for (int i = 1; i <= columns; ++i) {
		            row.put(md.getColumnName(i), resultSet.getObject(i));
		        }
		        users.add((DemoUser) row);
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
    	
//        Long created = System.currentTimeMillis();
//        users = Arrays.asList(
//                new DemoUser("1", "Fred", "Flintstone", true, created),
//                new DemoUser("2", "Wilma", "Flintstone", true, created),
//                new DemoUser("3", "Pebbles", "Flintstone", true, created),
//                new DemoUser("4", "Barney", "Rubble", true, created),
//                new DemoUser("5", "Betty", "Rubble", true, created),
//                new DemoUser("6", "Bam Bam", "Rubble", false, created)
//        );
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
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username) || user.getEmail().equalsIgnoreCase(username))
                .findFirst().orElse(null);
    }

    List<DemoUser> findUsers(String query) {
        return users.stream()
                .filter(user -> user.getUsername().contains(query) || user.getEmail().contains(query))
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
