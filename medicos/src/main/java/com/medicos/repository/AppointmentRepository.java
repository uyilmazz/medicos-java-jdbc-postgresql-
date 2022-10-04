package com.medicos.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import com.medicos.entity.Appointment;

public class AppointmentRepository extends BaseRepository<Appointment>{

	public List<Appointment> findAll() throws SQLException{
		String sql = "Select * from appointments";
		return super.findAll(sql);
	}
	
	public Appointment findById(long id) throws SQLException {
		String sql = "Select * from appointments where id = ?";
		return super.findById(sql, id);
	}
	
	public boolean add(Appointment appointmen) throws SQLException {
		connect();
		String sql = "Insert into appointments(date,is_selected,customer_id,doctor_id) values(?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setTimestamp(1, appointmen.getAppointmentDate());
		statement.setBoolean(2, appointmen.isSelected());
		statement.setLong(3, appointmen.getCustomerId());
		statement.setLong(4, appointmen.getDoctorId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean update(Appointment appointmen) throws SQLException {
		connect();
		String sql = "Update appointments set date = ? , is_selected = ? ,customer_id = ?,doctor_id = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setTimestamp(1, appointmen.getAppointmentDate());
		statement.setBoolean(2, appointmen.isSelected());
		statement.setLong(3, appointmen.getCustomerId());
		statement.setLong(4, appointmen.getDoctorId());
		statement.setLong(5, appointmen.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean remove(long id) throws SQLException {
		String sql = "Delete from appointments where id = ?";
		return super.remove(sql, id);
	}
	
	
	@Override
	protected Appointment parse(ResultSet resultSet) throws SQLException {
		long appointmentId = resultSet.getLong("id");
		boolean isSelected = resultSet.getBoolean("is_selected");
		long doctorId = resultSet.getLong("doctor_id");
		long customerId = resultSet.getLong("customer_id");
		Timestamp appointmentDate = resultSet.getTimestamp("date");
		Appointment appointment = new Appointment(appointmentId,appointmentDate,isSelected,customerId,doctorId);
		return appointment;
	}

}
