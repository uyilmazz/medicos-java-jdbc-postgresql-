package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.medicos.entity.Address;
import com.medicos.entity.Province;

public class AddressRepository extends BaseRepository<Address>{
	
	public List<Address> findAll() throws SQLException{
		String sql = "Select * from addresses";
		return super.findAll(sql);
	}
	
	public Address findById(long id) throws SQLException {
		String sql = "Select * from addresses where id = ?";
		return super.findById(sql, id);
	}
	
	public boolean add(Address address) throws SQLException {
		connect();
		String sql = "Insert into addresses(address_line_1,address_line_2,customer_id,province_id) values(?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, address.getAddressLine1());
		statement.setString(2, address.getAddressLine2());
		statement.setLong(3, address.getCustomerId());
		statement.setInt(4, address.getProvince().getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean update(Address address) throws SQLException {
		connect();
		String sql = "Update addresses set address_line_1 = ? , address_line_2 = ? ,customer_id = ?,province_id = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, address.getAddressLine1());
		statement.setString(2, address.getAddressLine2());
		statement.setLong(3, address.getCustomerId());
		statement.setInt(4, address.getProvince().getId());
		statement.setLong(5, address.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean remove(long id) throws SQLException {
		String sql = "Delete from addresses where id = ?";
		return super.remove(sql, id);
	}
	
	@Override
	protected Address parse(ResultSet resultSet) throws SQLException {
		long addressId = resultSet.getLong("id");
		String addressLine1 = resultSet.getString("address_line_1");
		String addressLine2 = resultSet.getString("address_line_2");
		long userId = resultSet.getLong("user_id");
		int provinceId = resultSet.getInt("province_id");
		String provinceName = resultSet.getString("province_name");
		Province province = new Province(provinceId,provinceName);
		Address address = new Address(addressId,addressLine1,addressLine2,userId);
		address.setProvince(province);
		return address;
	}

}
