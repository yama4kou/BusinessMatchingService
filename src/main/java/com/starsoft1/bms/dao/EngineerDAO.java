package com.starsoft1.bms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.starsoft1.bms.model.EngineerModel;

public class EngineerDAO {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/sampleDB";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";

	public static List<EngineerModel> getEngineersByUserId(int user_id) {
		List<EngineerModel> engineers = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM engineer WHERE user_id = ?")) {
			statement.setInt(1, user_id);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					EngineerModel engineer = new EngineerModel();
					engineer.setEngineer_id(resultSet.getInt("engineer_id"));
					engineer.setUser_id(resultSet.getInt("user_id"));
					engineer.setEngineer_name(resultSet.getString("engineer_name"));
					engineer.setEngineer_companyName(resultSet.getString("engineer_companyName"));
					engineers.add(engineer);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("データベースエラー: " + e.getMessage());
		}

		return engineers;
	}
}
