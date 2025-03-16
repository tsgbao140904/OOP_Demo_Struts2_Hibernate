package com.example.action;

import com.example.model.User;
import com.example.util.DBConnection;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterAction extends ActionSupport {
    private String username;
    private String password;
    private String confirmPassword;

    public String execute() {
        // Kiểm tra username đã tồn tại chưa
        if (isUsernameExists(username)) {
            addActionError("Tên người dùng đã tồn tại!");
            return INPUT;
        }

        // Kiểm tra xác nhận mật khẩu
        if (!password.equals(confirmPassword)) {
            addActionError("Mật khẩu xác nhận không khớp!");
            return INPUT;
        }

        User user = new User(username, password, "user");

        // Lưu user vào database
        if (registerUser(user)) {
            try {
                ServletActionContext.getResponse().sendRedirect("login.jsp");
                return NONE; // Ngăn Struts xử lý tiếp
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            addActionError("Đăng ký thất bại, vui lòng thử lại!");
            return INPUT;
        }
        return INPUT;
    }

    // Kiểm tra username đã tồn tại chưa
    private boolean isUsernameExists(String username) {
        String query = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Trả về true nếu username đã tồn tại

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lưu user vào database
    private boolean registerUser(User user) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}