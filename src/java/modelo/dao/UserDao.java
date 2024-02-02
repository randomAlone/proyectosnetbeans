/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.util.List;
import modelo.entidad.User;
import modelo.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rolando
 */
public class UserDao {

    public List<User> listarUsers() {
        List<User> lista = null;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction t = sesion.beginTransaction();

        String hql = "FROM User";
        try {
            lista = sesion.createQuery(hql).list();
            t.commit();
            sesion.close();
        } catch (Exception e) {
            t.rollback();
        }
        return lista;
    }
    
   
    
    
    
    
    

public User login(User user) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String hql = "FROM User u WHERE u.nombre = :nombre";
    Query q = sesion.createQuery(hql);
    q.setParameter("nombre", user.getNombre()); // Asumo que getNombre() devuelve el nombre de usuario
    return (User) q.uniqueResult();
}


   
}
