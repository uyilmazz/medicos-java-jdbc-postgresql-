package com.medicos.business.concretes;

import java.sql.SQLException;
import java.util.List;

import com.medicos.business.abstracts.AppointmentService;
import com.medicos.business.messages.ResultMessages;
import com.medicos.core.result.DataResult;
import com.medicos.core.result.ErrorDataResult;
import com.medicos.core.result.ErrorResult;
import com.medicos.core.result.Result;
import com.medicos.core.result.SuccessDataResult;
import com.medicos.core.result.SuccessResult;
import com.medicos.entity.Appointment;
import com.medicos.repository.AppointmentRepository;

public class AppointmentManager implements AppointmentService{

	private final String entityName = "Appointment";
	private AppointmentRepository appointmentRepository;
	
	public AppointmentManager(AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	@Override
	public DataResult<List<Appointment>> getAll() throws SQLException {
		return new ErrorDataResult<List<Appointment>>(appointmentRepository.findAll());
	}

	@Override
	public DataResult<Appointment> getById(long id) throws SQLException {
		Appointment appointment = appointmentRepository.findById(id);
		if(appointment == null) {
			return new ErrorDataResult<Appointment>(ResultMessages.notAddedMessage(entityName));
		}
		return new SuccessDataResult<Appointment>(appointment);
	}
	
	@Override
	public Result add(Appointment entity) throws SQLException {
		Appointment appointment = appointmentRepository.findByDateAndDoctorId(entity.getAppointmentDate(), entity.getDoctorId());
		if(appointment != null) {
			return new ErrorResult(ResultMessages.alreadyExistMessage(entityName));
		}
		boolean addedResult = appointmentRepository.add(entity);
		return addedResult ? new SuccessResult(ResultMessages.addedMessage(entityName)) : new ErrorResult(ResultMessages.notAddedMessage(entityName));
	}

	@Override
	public Result update(Appointment entity) throws SQLException {
		DataResult<Appointment> appointmentResult = getById(entity.getId());
		if(!appointmentResult.isSuccess()) {
			return new ErrorResult(appointmentResult.getMessage());
		}
		boolean updatedResult = appointmentRepository.update(entity);
		return updatedResult ? new SuccessResult(ResultMessages.updatedMessage(entityName)) : new ErrorResult(ResultMessages.notUpdatedMessage(entityName));
	}

	@Override
	public Result delete(long id) throws SQLException {
		DataResult<Appointment> appointmentResult = getById(id);
		if(!appointmentResult.isSuccess()) {
			return new ErrorResult(appointmentResult.getMessage());
		}
		boolean deletedResult = appointmentRepository.remove(id);
		return deletedResult ? new SuccessResult(ResultMessages.deletedMessage(entityName)) : new ErrorResult(ResultMessages.notDeletedMessage(entityName));
	}

}
