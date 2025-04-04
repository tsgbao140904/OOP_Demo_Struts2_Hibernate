package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.example.model.User;
import org.apache.struts2.ServletActionContext;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RegisterAction extends ActionSupport {
    private String username;
    private String password;
    private String confirmPassword;

    public String execute() {
        // Kiểm tra xác nhận mật khẩu
        if (!password.equals(confirmPassword)) {
            addActionError("Mật khẩu xác nhận không khớp!");
            return INPUT;
        }

        Session hibernateSession = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = hibernateSession.beginTransaction();

        try {
            // Kiểm tra username đã tồn tại
            Long count = (Long) hibernateSession.createQuery("SELECT COUNT(*) FROM User WHERE username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            if (count > 0) {
                tx.rollback();
                addActionError("Tên người dùng đã tồn tại!");
                return INPUT;
            }

            // Tạo user mới
            User user = new User(username, password, "user");
            hibernateSession.save(user);
            tx.commit();

            ServletActionContext.getResponse().sendRedirect("login.jsp");
            return NONE;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            addActionError("Đăng ký thất bại, vui lòng thử lại!");
            return INPUT;
        }
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}