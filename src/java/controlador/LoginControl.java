/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import modelo.dao.UserDao;
import modelo.entidad.User;
import org.mindrot.jbcrypt.BCrypt;

@ManagedBean
@SessionScoped
public class LoginControl implements Serializable {

    private User usuario;

    public LoginControl() {
        this.usuario = new User();
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    // Método para manejar el inicio de sesión
    public String login() {
        FacesMessage mensaje = null;
        UserDao ud = new UserDao();
        
        // Obtener la contraseña sin cifrar del formulario
        String passwordFromForm = usuario.getClave();

        // Obtener la información del usuario desde la base de datos
        usuario = ud.login(usuario);

        if (usuario != null) {
            // Obtener el hash almacenado en la base de datos
            String hashedPasswordFromDB = usuario.getClave();

            // Verificar la contraseña utilizando BCrypt
            if (BCrypt.checkpw(passwordFromForm, hashedPasswordFromDB)) {
                // Almacenar el usuario en la sesión actual
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ ", this.usuario.getNombre());
                FacesContext.getCurrentInstance().addMessage(null, mensaje);
                return "/menu/inicio.xhtml"; // Redirigir a la página de inicio
            } else {
                mensaje = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de Acceso", "Usuario o Clave incorrecta");
                FacesContext.getCurrentInstance().addMessage(null, mensaje);
                usuario = new User();
                return ""; // Permanecer en la misma página
            }
        } else {
            mensaje = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de Acceso", "Usuario no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            usuario = new User();
            return ""; // Permanecer en la misma página
        }
    }

    // Método para cerrar la sesión
    public String cerrarSession() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        httpSession.invalidate(); // Invalidar la sesión actual
        return "/index"; // Redirigir a la página de inicio de sesión
    }
}
