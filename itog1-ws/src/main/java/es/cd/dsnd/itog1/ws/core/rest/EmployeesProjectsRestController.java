package es.cd.dsnd.itog1.ws.core.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cd.dsnd.itog1.api.core.service.IEmployeesProjectsService;
import com.ontimize.jee.server.rest.ORestController;

@RestController
@RequestMapping("/employeesProjects")
public class EmployeesProjectsRestController extends ORestController<IEmployeesProjectsService> {

 @Autowired
 private IEmployeesProjectsService employeesProjectsService;

 @Override
 public IEmployeesProjectsService getService() {
  return this.employeesProjectsService;
 }

}