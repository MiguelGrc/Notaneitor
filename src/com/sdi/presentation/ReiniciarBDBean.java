package com.sdi.presentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import alb.util.date.DateUtil;
import alb.util.log.Log;

import com.sdi.business.AdminService;
import com.sdi.business.Services;
import com.sdi.business.TaskService;
import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.dto.User;
import com.sdi.persistence.impl.UserDaoJdbcImpl;

@ManagedBean(name = "reiniciar")
@ApplicationScoped
public class ReiniciarBDBean {

	public void reiniciar() {
		AdminService aServ;
		TaskService tServ;

		try {
			aServ = Services.getAdminService();
			tServ = Services.getTaskService();

			UserDaoJdbcImpl dUser = new UserDaoJdbcImpl();

			List<User> usuariosActuales = aServ.findAllUsers();
			for (User u : usuariosActuales) {
				if (!u.getIsAdmin())
					aServ.deepDeleteUser(u.getId());
			}
			Log.debug("Usuarios borrados de la BD");

			List<User> usuariosNuevos = new ArrayList<User>();
			for (int i = 1; i <= 3; i++) {
				User usuario = new User();
				dUser.save(usuario.setEmail("user" + i + "@gmail.com")
						.setId(new Long(i)).setLogin("user" + i)
						.setPassword("user" + i));
				usuariosNuevos.add(usuario);
			}
			Log.debug("Usuarios cargados en la BD");

			List<Category> categNuevas = new ArrayList<Category>();
			usuariosNuevos = aServ.findAllUsers();

			int j = 1;
			for (User u : usuariosNuevos) {
				if (!u.getIsAdmin()) {
					for (int i = 1; i <= 3; i++) {
						Category categ = new Category();
						tServ.createCategory(categ.setId(new Long(j))
								.setUserId(u.getId())
								.setName("Category" + j));
						categNuevas.add(categ);
						j++;
					}
				}
			}
			Log.debug("Categorías cargadas en la BD");
			
			List<Task> nuevasTareas = new ArrayList<Task>();
			j = 1;
			for (User u : usuariosNuevos) {
				if (!u.getIsAdmin()) {				
					List<Category> categoriasUsuario = tServ
							.findCategoriesByUserId(u.getId());
					for (int i = 1; i <= 10; i++) {
						Task tarea = new Task();
						nuevasTareas.add(tarea
								.setPlanned(
										DateUtil.addDays(DateUtil.today(), 6))
								.setId(new Long(j)).setTitle("Tarea" + j)
								.setUserId(u.getId()));
						j++;
					}
					for (int i = 1; i <= 10; i++) {
						Task tarea = new Task();
						nuevasTareas.add(tarea.setPlanned(DateUtil.today())
								.setId(new Long(j)).setTitle("Tarea" + j)
								.setUserId(u.getId()));
						j++;
					}
					for (int i = 1; i <= 10; i++) {
						Task tarea = new Task();
						Category categ = null;
						if (i <= 3) {
							categ = categoriasUsuario.get(0);
						} else {
							if (i <= 6) {
								categ = categoriasUsuario.get(1);
							} else {
								if (i <= 10) {
									categ = categoriasUsuario.get(2);
								}
							}

						}

						nuevasTareas.add(tarea
								.setPlanned(DateUtil.addDays(DateUtil.today(), -j))
								.setId(new Long(j)).setTitle("Tarea" + j)
								.setUserId(u.getId())
								.setCategoryId(categ.getId()));
						j++;
					}
				}
			}
			Log.debug("Tareas cargadas en la BD");
			

			//Las mezclamos para poder ordenadarlas luego:
			Collections.shuffle(nuevasTareas);
			
			//Las introducimos mezcladas
			for(Task t: nuevasTareas)
				tServ.createTask(t);

		} catch (Exception ex) {
			Log.error(ex);
		}
	}

}
