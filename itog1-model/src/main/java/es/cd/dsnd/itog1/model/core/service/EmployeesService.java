package es.cd.dsnd.itog1.model.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.cd.dsnd.itog1.api.core.service.IEmployeesService;
import es.cd.dsnd.itog1.model.core.dao.EmployeesDao;
import es.cd.dsnd.itog1.model.core.dao.EmployeesProjectsDao;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;



@Lazy
@Service("EmployeesService")
public class EmployeesService implements IEmployeesService {

	@Autowired
	private EmployeesDao employeesDao;

	@Autowired
	private EmployeesProjectsDao employeesProjectsDao;

	@Autowired
	private DefaultOntimizeDaoHelper daoHelper;
	
	
	@Override
	public EntityResult employeeQuery(Map<String, Object> keyMap, List<String> attrList)
			throws OntimizeJEERuntimeException {
		return this.daoHelper.query(this.employeesDao, keyMap, attrList);
	}

	@Override
	public EntityResult employeeInsert(Map<String, Object> attrMap) throws OntimizeJEERuntimeException {
		  return this.daoHelper.insert(this.employeesDao, attrMap);
	}

	@Override
	public EntityResult employeeUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap)
			throws OntimizeJEERuntimeException {
		  return this.daoHelper.update(this.employeesDao, attrMap, keyMap);
	}

	@Override
	public EntityResult employeeDelete(Map<String, Object> keyMap) throws OntimizeJEERuntimeException {
		  return this.daoHelper.delete(this.employeesDao, keyMap);
		  
	}
	@Override
	public EntityResult employeeProjectQuery(Map<String, Object> keyMap, List<String> attrList)
			throws OntimizeJEERuntimeException {
		return this.daoHelper.query(this.employeesProjectsDao, keyMap, attrList);
	}

//	@Override
//	public EntityResult employeeProjectInsert(Map<String, Object> attrMap) 
//			throws OntimizeJEERuntimeException {
//	return this.daoHelper.insert(this.employeesProjectsDao, attrMap);
//	}
	@Override
	public EntityResult employeeProjectInsert(Map<String, Object> attrMap) throws OntimizeJEERuntimeException {
			Map<String, Object> employee_id = new HashMap<String, Object>();
			employee_id.put(EmployeesProjectsDao.ATTR_ID_EMPLOYEE, attrMap.get(EmployeesProjectsDao.ATTR_ID_EMPLOYEE));
			List<String> projectList = new ArrayList<String>();
			projectList.add(EmployeesProjectsDao.ATTR_ID_PROJECT);
			projectList.add(EmployeesProjectsDao.ATTR_EP);
			
		  EntityResult employeeProjectQuery = this.employeeProjectQuery(employee_id, projectList);
		  	  
		  
		  if (employeeProjectQuery.getCode() != EntityResult.OPERATION_WRONG && employeeProjectQuery.calculateRecordNumber() > 0) {
			List<Integer> object = (List<Integer>) employeeProjectQuery.get(EmployeesProjectsDao.ATTR_EP);
			int suma = 0;
			for(int actual : object) {
				suma += actual;
			}
			int value = (int) attrMap.get(EmployeesProjectsDao.ATTR_EP);
			suma = suma + value;
			if(suma <= 100) {
				return this.daoHelper.insert(this.employeesProjectsDao, attrMap);
			} else {
				EntityResult toret = new EntityResultMapImpl();
				toret.setCode(EntityResult.OPERATION_WRONG);
				toret.setMessage("La suma del % de implicación de un empleado no puede superar el 100%");
				return toret;
			}
			
		  } else {
			  if(employeeProjectQuery.getCode() == EntityResult.OPERATION_WRONG) {
				  return employeeProjectQuery;  
			  }else {
					return this.daoHelper.insert(this.employeesProjectsDao, attrMap);
			  }
		  }  
	}

	@Override
	public EntityResult employeeProjectUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap)
			throws OntimizeJEERuntimeException {
		  return this.daoHelper.update(this.employeesProjectsDao, attrMap, keyMap);
	}

	@Override
	public EntityResult employeeProjectDelete(Map<String, Object> keyMap) throws OntimizeJEERuntimeException {
		  return this.daoHelper.delete(this.employeesProjectsDao, keyMap);
	}

	 @Override
	 public EntityResult employeeProjectDetailsQuery(Map<String, Object> keyMap, List<String> attrList)
	   throws OntimizeJEERuntimeException {
	  return this.daoHelper.query(this.employeesProjectsDao, keyMap, attrList, EmployeesProjectsDao.QUERY_DETAILS);
	 }
}
