package com.sdi.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import alb.util.date.DateUtil;

import com.sdi.business.AdminService;
import com.sdi.business.Services;
import com.sdi.business.TaskService;
import com.sdi.business.UserService;
import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.dto.User;
import com.sdi.persistence.impl.UserDaoJdbcImpl;

@ManagedBean(name = "reiniciar")
@ApplicationScoped
public class ReiniciarBD {

	public void reiniciar(ActionEvent e) {
		AdminService aServ;
		TaskService tServ;
		UserService uServ;

		// String[][] usuarios={{"user1","user1","1",
		// "user1@gmail.com"},{"user2","user2","2", "user2@gmail.com"},
		// {"user3","user3","3", "user3@gmail.com"}};
		// String[][] categorias={{"categoría1","1","1"}}

		try {
			aServ = Services.getAdminService();
			tServ = Services.getTaskService();
			uServ = Services.getUserService();

			UserDaoJdbcImpl dUser = new UserDaoJdbcImpl();

			List<User> usuariosActuales = aServ.findAllUsers();
			for (User u : usuariosActuales) {
				if (!u.getIsAdmin())
					aServ.deepDeleteUser(u.getId());
			}

			List<User> usuariosNuevos = new ArrayList<User>();
			for (int i = 1; i <= 3; i++) {
				User usuario = new User();
				dUser.save(usuario.setEmail("user" + i + "@gmail.com")
						.setId(new Long(i)).setLogin("user" + i)
						.setPassword("user" + i));
				usuariosNuevos.add(usuario);
			}

			// User usuario1 = new User();
			// usuario1.setEmail("user1@gmail.com").setId(new Long())
			// .setLogin("user1").setPassword("user1");
			// uServ.registerUser(usuario1);
			List<Category> categNuevas = new ArrayList<Category>();
			usuariosNuevos = aServ.findAllUsers();
			// for(User u: usuariosNuevos){
			// if(u.getIsAdmin()){
			// usuariosNuevos.remove(u);
			// }
			// }

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

			j = 1;
			for (User u : usuariosNuevos) {
				if (!u.getIsAdmin()) {
					Task tarea = new Task();
					List<Category> categoriasUsuario = tServ
							.findCategoriesByUserId(u.getId());
					for (int i = 1; i <= 10; i++) {
						tServ.createTask(tarea
								.setPlanned(
										DateUtil.addDays(DateUtil.today(), 6))
								.setId(new Long(j)).setTitle("Tarea" + j)
								.setUserId(u.getId()));
						j++;
					}
					for (int i = 1; i <= 10; i++) {
						tServ.createTask(tarea.setPlanned(DateUtil.today())
								.setId(new Long(j)).setTitle("Tarea" + j)
								.setUserId(u.getId()));
						j++;
					}
					for (int i = 1; i <= 10; i++) {
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

						tServ.createTask(tarea
								.setPlanned(
										DateUtil.addDays(DateUtil.today(), -j))
								.setId(new Long(j)).setTitle("Tarea" + j)
								.setUserId(u.getId())
								.setCategoryId(categ.getId()));
						j++;
					}
				}
			}

			// User usuario2 = new User();
			// usuario2.setEmail("user2@gmail.com").setId(new Long(2))
			// .setLogin("user2").setPassword("user2");

			// User usuario3= new User();
			// usuario3.setEmail("user3@gmail.com").setId(new Long(3))
			// .setLogin("user3").setPassword("user3");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
