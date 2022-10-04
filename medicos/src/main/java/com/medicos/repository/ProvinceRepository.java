package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.medicos.entity.Province;

public class ProvinceRepository extends BaseRepository<Province>{

	public List<Province> findAll() throws SQLException{
		String sql = "Select * from provinces";
		return super.findAll(sql);
	}
	
	public Province findById(int id) throws SQLException {
		String sql = "Select * from provinces where id = ?";
		return super.findById(sql, id);
	}
	
	public boolean add(Province province) throws SQLException {
		connect();
		String sql = "Insert into provinces(name) values(?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, province.getName());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean update(Province province) throws SQLException {
		connect();
		String sql = "Update provinces set name = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, province.getName());
		statement.setInt(2, province.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean remove(int id) throws SQLException {
		String sql = "Delete from provinces where id = ?";
		return super.remove(sql, id);
	}
	
	@Override
	protected Province parse(ResultSet resultSet) throws SQLException {
		Province province = null;
		if(resultSet.next()) {
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			province = new Province(id,name);
		}
		return province;
	}
}
