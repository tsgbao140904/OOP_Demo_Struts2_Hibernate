package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.example.model.User;
import org.apache.struts2.ServletActionContext;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.http.HttpSession;

public class LoginAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;

    public String execute() {
        Session hibernateSession = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = hibernateSession.beginTransaction();

        try {
            User user = (User) hibernateSession.createQuery("FROM User WHERE username = :username AND password = :password")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();

            if (user != null) {
                HttpSession session = ServletActionContext.getRequest().getSession();
                session.setAttribute("username", username);
                tx.commit();
                ServletActionContext.getResponse().sendRedirect("home.jsp");
                return NONE;
            } else {
                tx.commit();
                ServletActionContext.getResponse().sendRedirect("error.jsp");
                return NONE;
            }
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return ERROR;
        }
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}