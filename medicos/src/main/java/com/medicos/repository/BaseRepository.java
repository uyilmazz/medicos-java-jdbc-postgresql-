package com.medicos.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepository <T>{

	private String url = "jdbc:postgresql://localhost/medicos";
	private String user = "postgres";
	private String password = "12345";
	private String driver = "org.postgresql.Driver";
	
	protected Connection connection;
	
	public BaseRepository() {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void connect() throws SQLException{
		connection = DriverManager.getConnection(url, user, password);
	}
	
	protected void disconnect() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
	
	protected List<T> findAll(String sql) throws SQLException{
		connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		List<T> entityList = parseList(resultSet);
		disconnect();
		return entityList;
	}
	
	protected T findById(String sql,long id) throws SQLException {
		T entity = null;
		connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()) {
			entity = parse(resultSet);
		}
		disconnect();
		return entity;
	}
	
	protected boolean remove(String sql,long id) throws SQLException {
		connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	protected List<T> parseList(ResultSet resultSet) throws SQLException{
		List<T> entityList = new ArrayList<>();
		while(resultSet.next()) {
			T entity = parse(resultSet);
			entityList.add(entity);
		}
		return entityList;
	}
	
	protected abstract T parse(ResultSet resultSet) throws SQLException;
}
