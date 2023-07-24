package com.starsoft1.bms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.starsoft1.bms.model.UserModel;

@Repository
public class LoginDAO {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/sampleDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    
    public LoginDAO(JdbcTemplate jdbcTemplate) {
    }
    
    public UserModel getUserByEmail(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserModel user = null;

        try {
            // データベースへの接続
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // SQL文の準備と実行
            String sql = "SELECT * FROM user WHERE user_email = ? AND user_deleteFlag = 0";
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
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
}
