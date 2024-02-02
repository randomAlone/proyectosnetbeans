//creado por Random
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
package controlador;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.dao.UserDao;
import modelo.entidad.User;
import org.mindrot.jbcrypt.BCrypt;

@ManagedBean
@ViewScoped
public class UserControl {
    private List<User> listaUsers; // Lista de usuarios
    private User user; // Objeto User para operaciones CRUD
    
    /**
     * Creates a new instance of UserControl
     */
    public UserControl() {
    }

    // Método para obtener la lista de usuarios
    public List<User> getListaUsers() {
        UserDao ed = new UserDao();
        listaUsers = ed.listarUsers(); // Obtener la lista de usuarios desde la base de datos
        return listaUsers;
    }
    
    // Método para establecer la lista de usuarios
    public void setListaUser(List<User> listaUser) {
        this.listaUsers = listaUser;
    }
    
    // Getter y setter para el objeto User
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    // Método para limpiar el objeto User (para operaciones de creación o edición)
    public void limpiarUser() {
        user = new User();
    }
}
