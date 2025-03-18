package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.example.model.Post;
import com.example.util.DBConnection;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public class HomeAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 1L;
    private List<Post> posts;
    private Map<String, Object> session;
    private int id; // ID bài viết cần xóa

    public String execute() {
        posts = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT p.id, p.title, p.body, u.username, p.created_at FROM posts p JOIN users u ON p.user_id = u.id ORDER BY p.created_at DESC");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setBody(rs.getString("body"));
                post.setUsername(rs.getString("username"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                posts.add(post);
            }

            session.put("posts", posts);

            HttpServletResponse response = ServletActionContext.getResponse();
            response.sendRedirect("home.jsp");
            return NONE;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public String deletePost() {
        System.out.println("ID cần xóa: " + id); // Debug ID

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM posts WHERE id = ?")) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                session.put("message", "Xóa bài viết thành công!");
            } else {
                session.put("error", "Không tìm thấy bài viết để xóa!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.put("error", "Lỗi khi xóa bài viết!");
        }

        // Tải lại danh sách bài viết để cập nhật session
        loadPosts();

        // Chuyển hướng về home.jsp
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.sendRedirect("home.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return NONE; 
    }

    // Hàm load lại danh sách bài viết
    private void loadPosts() {
        List<Post> updatedPosts = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT p.id, p.title, p.body, u.username, p.created_at FROM posts p JOIN users u ON p.user_id = u.id ORDER BY p.created_at DESC");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setBody(rs.getString("body"));
                post.setUsername(rs.getString("username"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                updatedPosts.add(post);
            }

            session.put("posts", updatedPosts); // Cập nhật danh sách bài viết trong session
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public List<Post> getPosts() {
        return posts;
    }
    
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
