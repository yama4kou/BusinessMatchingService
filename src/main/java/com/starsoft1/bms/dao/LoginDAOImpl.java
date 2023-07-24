//package com.starsoft1.bms.dao;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import com.starsoft1.bms.model.UserModel;
//
//// リポジトリ（データアクセス層）としてマークされたクラス
//@Repository
//public class LoginDAOImpl implements LoginDAO {
//
//	// JdbcTemplateを使用してデータベースとのやり取りを行う
//	private final JdbcTemplate jdbcTemplate;
//
//	// JdbcTemplateをインジェクションするコンストラクタ
//	public LoginDAOImpl(JdbcTemplate jdbcTemplate) {
//		this.jdbcTemplate = jdbcTemplate;
//	}
//
//	// メールアドレスを指定して、ユーザー情報を取得するメソッド
//	@SuppressWarnings("deprecation")
//	@Override
//	public UserModel getUserByEmail(String email) {
//		String sql = "SELECT * FROM user WHERE user_email = ?";
//
//		// JdbcTemplateを使用してSQLクエリを実行し、結果セットをUserRowMapperによりUserModelオブジェクトにマッピングして返す
//		return jdbcTemplate.queryForObject(sql, new Object[]{email}, new UserRowMapper());
//	}
//
//	// 内部クラス：ResultSetをUserModelにマッピングするためのRowMapper
//	private static class UserRowMapper implements RowMapper<UserModel> {
//		@Override
//		public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
//			// 新しいUserModelオブジェクトを作成し、ResultSetから取得したデータを設定して返す
//			UserModel userModel = new UserModel();
//			userModel.setUser_id(rs.getInt("id")); 
//			userModel.setUser_email(rs.getString("email"));
//			userModel.setUser_password(rs.getString("password"));
//			// 他のユーザープロパティを必要に応じて設定します
//			return userModel;
//		}
//	}
//}
