package com.starsoft1.bms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.starsoft1.bms.model.UserModel;

@Repository
public class UserDAO {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/sampleDB";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";

	private final JdbcTemplate jdbcTemplate;

	public UserDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public boolean checkEmailExists(String email) {
		String sql = "SELECT COUNT(*) FROM user WHERE user_email = ? AND user_deleteFlag = 0";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
		return count > 0;
	}

	public void saveUser(UserModel user) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// データベースへの接続
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// SQL文の準備と実行
			String sql = "INSERT INTO user (user_companyName, user_departmentName, user_lastName"
					+ ", user_firstName, user_lastNameKana, user_firstNameKana"
					+ ", user_email, user_password, user_registrationDate"
					+ ", user_role, user_deleteFlag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getUserCompanyName());
			statement.setString(2, user.getUserDepartmentName());
			statement.setString(3, user.getUserLastName());
			statement.setString(4, user.getUserFirstName());
			statement.setString(5, user.getUserLastNameKana());
			statement.setString(6, user.getUserFirstNameKana());
			statement.setString(7, user.getUserEmail());
			statement.setString(8, user.getUserPassword());
			statement.setTimestamp(9, user.getUserRegistrationDate());
			statement.setString(10, user.getUserRole());
			statement.setInt(11, user.getUserDeleteFlag());
			statement.executeUpdate();
			ResultSet res = statement.getGeneratedKeys();
			while(res.next()) {
				String userId = res.getString(1);
				System.out.println("Generated User Id :"+ userId);
				String roleUptSql = "INSERT INTO users_roles (role_id, user_id) VALUES(1,"+userId+");";
				PreparedStatement roleUptStatement = null;
				roleUptStatement = connection.prepareStatement(roleUptSql);
				roleUptStatement.executeUpdate();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("データベースエラー: " + e.getMessage());
		} finally {
			// リソースの解放
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//    public UserModel getUserByEmail(String email) {
	//        Connection connection = null;
	//        PreparedStatement statement = null;
	//        ResultSet resultSet = null;
	//        UserModel user = null;
	//
	//        try {
	//            // データベースへの接続
	//            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	//
	//            // SQL文の準備と実行
	//            String sql = "SELECT * FROM user WHERE user_email = ? AND user_deleteFlag = 0";
	//            statement = connection.prepareStatement(sql);
	//            statement.setString(1, email);
	//            resultSet = statement.executeQuery();
	//
	//            if (resultSet.next()) {
	//                user = new UserModel();
	//                user.setUser_id(resultSet.getInt("user_id"));
	//                user.setUser_companyName(resultSet.getString("user_companyName"));
	//                user.setUser_departmentName(resultSet.getString("user_departmentName"));
	//                user.setUser_lastName(resultSet.getString("user_lastName"));
	//                user.setUser_firstName(resultSet.getString("user_firstName"));
	//                user.setUser_lastNameKana(resultSet.getString("user_lastNameKana"));
	//                user.setUser_firstNameKana(resultSet.getString("user_firstNameKana"));
	//                user.setUser_email(resultSet.getString("user_email"));
	//                user.setUser_password(resultSet.getString("user_password"));
	//                user.setUser_registrationDate(resultSet.getTimestamp("user_registrationDate"));
	//                user.setUser_role(resultSet.getString("user_role"));
	//                user.setUser_deleteFlag(resultSet.getInt("user_deleteFlag"));
	//            }
	//        } catch (SQLException e) {
	//            e.printStackTrace();
	//            throw new RuntimeException("データベースエラー: " + e.getMessage());
	//        } finally {
	//            // リソースの解放
	//            try {
	//                if (resultSet != null) {
	//                    resultSet.close();
	//                }
	//                if (statement != null) {
	//                    statement.close();
	//                }
	//                if (connection != null) {
	//                    connection.close();
	//                }
	//            } catch (SQLException e) {
	//                e.printStackTrace();
	//            }
	//        }
	//
	//        return user;
	//    }

	public static UserModel getUserById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		UserModel user = null;

		try {
			// データベースへの接続
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// SQL文の準備と実行
			 String sql = "SELECT * FROM user u " + "INNER JOIN users_roles ur ON u.user_id = ur.user_id "
				     + "INNER JOIN roles r ON ur.role_id = r.role_id "
				     + "Where u.user_deleteFlag = 0 AND u.user_id = ?;";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				user = new UserModel();
				user.setUserId(resultSet.getInt("user_id"));
				user.setUserCompanyName(resultSet.getString("user_companyName"));
				user.setUserDepartmentName(resultSet.getString("user_departmentName"));
				user.setUserLastName(resultSet.getString("user_lastName"));
				user.setUserFirstName(resultSet.getString("user_firstName"));
				user.setUserLastNameKana(resultSet.getString("user_lastNameKana"));
				user.setUserFirstNameKana(resultSet.getString("user_firstNameKana"));
				user.setUserEmail(resultSet.getString("user_email"));
				user.setUserPassword(resultSet.getString("user_password"));
				user.setUserRegistrationDate(resultSet.getTimestamp("user_registrationDate"));
				user.setUserRole(resultSet.getString("user_role"));
				user.setUserDeleteFlag(resultSet.getInt("user_deleteFlag"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("データベースエラー: " + e.getMessage());
		} finally {
			// リソースの解放
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return user;
	}

	public void updateUser(UserModel user) {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// データベースへの接続
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// SQL文の準備と実行
			String sql = "UPDATE user SET user_companyName = ?, user_departmentName = ?, user_lastName = ?, "
					+ "user_firstName = ?, user_lastNameKana = ?, user_firstNameKana = ?,"
					+ "user_email = ?, user_password = ?, user_registrationDate = ?, "
					+ "user_role = ?, user_deleteFlag = ? WHERE user_id = ?";

			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getUserCompanyName());
			statement.setString(2, user.getUserDepartmentName());
			statement.setString(3, user.getUserLastName());
			statement.setString(4, user.getUserFirstName());
			statement.setString(5, user.getUserLastNameKana());
			statement.setString(6, user.getUserFirstNameKana());
			statement.setString(7, user.getUserEmail());
			statement.setString(8, user.getUserPassword());
			statement.setTimestamp(9, user.getUserRegistrationDate());
			statement.setString(10, user.getUserRole());
			statement.setInt(11, user.getUserDeleteFlag());
			statement.setInt(12, user.getUserId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("データベースエラー: " + e.getMessage());
		} finally {
			// リソースの解放
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//    //つかわなくなっちゃった；；
	//    public void deleteUser(UserModel user) {
	//
	//    	
	//    	System.out.println("DAOのほうのdeleteUserきたよー");
	//    	
	//		Connection connection = null;
	//		PreparedStatement statement = null;
	//
	//		try {
	//			
	//			System.out.println(user.getUser_id());
	//			
	//			// データベースへの接続
	//			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	//
	//			// SQL文の準備と実行
	//			String sql = "DELETE FROM user WHERE user_id = ?";
	//
	//			statement = connection.prepareStatement(sql);
	//			statement.setInt(1, user.getUser_id());
	//			statement.executeUpdate();
	//		} catch (SQLException e) {
	//			e.printStackTrace();
	//			throw new RuntimeException("データベースエラー: " + e.getMessage());
	//		} finally {
	//			// リソースの解放
	//			try {
	//				if (statement != null) {
	//					statement.close();
	//				}
	//				if (connection != null) {
	//					connection.close();
	//				}
	//			} catch (SQLException e) {
	//				e.printStackTrace();
	//			}
	//		}
	//    }

	public ArrayList<UserModel> getUsersByNameAndCompany(String companyName, String lastName, String firstName) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList<UserModel> users = new ArrayList<>();

		try {
			// データベースへの接続
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// SQL文の準備と実行
			String sql = "SELECT * FROM user "
					+ "WHERE user_companyName LIKE ? AND user_lastName LIKE ? AND user_firstName LIKE ? AND user_deleteFlag = 0;";
			statement = connection.prepareStatement(sql);
			statement.setString(1, "%".concat(companyName).concat("%"));
			statement.setString(2, "%".concat(lastName).concat("%"));
			statement.setString(3, "%".concat(firstName).concat("%"));
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				UserModel user = new UserModel();

				user.setUserId(resultSet.getInt("user_id"));
				user.setUserCompanyName(resultSet.getString("user_companyName"));
				user.setUserDepartmentName(resultSet.getString("user_departmentName"));
				user.setUserLastName(resultSet.getString("user_lastName"));
				user.setUserFirstName(resultSet.getString("user_firstName"));
				user.setUserLastNameKana(resultSet.getString("user_lastNameKana"));
				user.setUserFirstNameKana(resultSet.getString("user_firstNameKana"));
				user.setUserEmail(resultSet.getString("user_email"));
				user.setUserPassword(resultSet.getString("user_password"));
				user.setUserRole(resultSet.getString("user_role"));


				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("データベースエラー: " + e.getMessage());
		} finally {
			// リソースの解放
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return users;
	}

	public ArrayList<UserModel> getAllUsers() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList<UserModel> users = new ArrayList<>();

		try {
			// データベースへの接続
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// SQL文の準備と実行
			String sql = 
					"SELECT u.user_id,u.user_email,u.user_password,r.role_name "
							+ "FROM user u "
							+ "INNER JOIN users_roles ur ON u.user_id = ur.user_id "
							+ "INNER JOIN roles r ON ur.role_id = r.role_id "
							+ "Where u.user_deleteFlag = 0;";
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				UserModel user = new UserModel();

				user.setUserEmail(resultSet.getString("user_email"));
				user.setUserPassword(resultSet.getString("user_password"));
				user.setUserRole(resultSet.getString("role_name"));                

				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("データベースエラー: " + e.getMessage());
		} finally {
			// リソースの解放
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return users;
	}

	public List<String> getDisRoles() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList<String> roles = new ArrayList<>();

		try {
			// データベースへの接続
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// SQL文の準備と実行
			String sql = "SELECT DISTINCT role_name FROM roles ";
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				roles.add(resultSet.getString("role_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("データベースエラー: " + e.getMessage());
		} finally {
			// リソースの解放
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return roles;
	}

	public void updateUser(UserModel user, int roleId) {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// データベースへの接続
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// SQL文の準備と実行
			String sql = "UPDATE user u "
					+ "SET user_companyName = ?, user_departmentName = ?, user_lastName = ?, "
					+ "user_firstName = ?, user_lastNameKana = ?, user_firstNameKana = ?,"
					+ "user_email = ?, user_password = ?, user_registrationDate = ?, "
					+ "user_role = ?, user_deleteFlag = ? WHERE user_id = ?; ";

			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getUserCompanyName());
			statement.setString(2, user.getUserDepartmentName());
			statement.setString(3, user.getUserLastName());
			statement.setString(4, user.getUserFirstName());
			statement.setString(5, user.getUserLastNameKana());
			statement.setString(6, user.getUserFirstNameKana());
			statement.setString(7, user.getUserEmail());
			statement.setString(8, user.getUserPassword());
			statement.setTimestamp(9, user.getUserRegistrationDate());
			statement.setString(10, user.getUserRole());
			statement.setInt(11, user.getUserDeleteFlag());
			statement.setInt(12, user.getUserId());
			statement.addBatch();
			//sql =  "UPDATE user_roles SET role_id = ? WHERE  user_id = ?;";
			statement.addBatch("UPDATE users_roles SET role_id = "+roleId+" WHERE  user_id = "+user.getUserId()+";");
			//            statement.setString(1, user.getUserRole());
			//            statement.setInt(2, user.getUserId());
			//            statement.addBatch();
			System.out.println(statement);
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("データベースエラー: " + e.getMessage());
		} finally {
			// リソースの解放
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int getRoleId(String userRole) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// データベースへの接続
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// SQL文の準備と実行
			String sql = "SELECT * FROM roles WHERE role_name =?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, userRole);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				return resultSet.getInt("role_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("データベースエラー: " + e.getMessage());
		} finally {
			// リソースの解放
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
}
