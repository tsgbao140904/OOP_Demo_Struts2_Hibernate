package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

public class LoginAction extends ActionSupport {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    public String execute() {
        if (authenticate(username, password)) {
            HttpSession session = ServletActionContext.getRequest().getSession();
            session.setAttribute("username", username);
			/* return SUCCESS; */
            try {
                HttpServletResponse response = ServletActionContext.getResponse();
                response.sendRedirect("home.jsp");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return NONE;
        } else {
			/*
			 * addActionError("Tên đăng nhập hoặc mật khẩu không đúng."); return INPUT;
			 */
        	try {
        	    HttpServletResponse response = ServletActionContext.getResponse();
        	    response.sendRedirect("error.jsp");
        	} catch (Exception e) {
        	    e.printStackTrace();
        	}
        	return NONE;

        }
    }

    private boolean authenticate(String username, String password) {
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Mật khẩu rỗng hoặc null");
            return false;
        }

        System.out.println("Đăng nhập với: " + username + " | Mật khẩu: " + password);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?")) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Đăng nhập thành công!");
                return true;
            } else {
                System.out.println("Sai tài khoản hoặc mật khẩu.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}