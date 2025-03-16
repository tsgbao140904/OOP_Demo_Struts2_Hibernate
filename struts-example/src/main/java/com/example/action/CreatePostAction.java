package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.example.util.DBConnection;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

public class CreatePostAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 1L;
    private String title;
    private String body;
    private Map<String, Object> session;

    public String execute() {
        try {
            if (session == null || session.get("username") == null) {
                ServletActionContext.getResponse().sendRedirect("login.jsp");
                return NONE;
            }

            String username = (String) session.get("username");

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO posts (title, body, user_id, status) VALUES (?, ?, (SELECT id FROM users WHERE username=?), 'published')")) {

                stmt.setString(1, title);
                stmt.setString(2, body);
                stmt.setString(3, username);
                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    ServletActionContext.getResponse().sendRedirect("home.action");
                    return NONE;
                } else {
                    addActionError("Lỗi khi đăng bài!");
                    return INPUT;
                }

            } catch (Exception e) {
                e.printStackTrace();
                addActionError("Lỗi hệ thống!");
                return ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }


    // Getter & Setter
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
