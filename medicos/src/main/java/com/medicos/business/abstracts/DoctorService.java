package com.medicos.business.abstracts;

import java.sql.SQLException;

import com.medicos.core.result.DataResult;
import com.medicos.entity.Doctor;

public interface DoctorService extends BaseService<Doctor>{
	public DataResult<Doctor> getByEmail(String email) throws SQLException;
}
