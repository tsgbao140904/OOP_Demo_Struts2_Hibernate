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

			// Cập nhật danh sách bài viết trong session
			updateSessionPosts();

			/* return SUCCESS; */

			// Chuyển hướng đến home.jsp
			HttpServletResponse response = ServletActionContext.getResponse();
			response.sendRedirect("home.jsp");

			return NONE; // Tránh xử lý Struts tiếp tục
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	private void updateSessionPosts() {
        List<Post> posts = new ArrayList<>();
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

            // Cập nhật danh sách bài viết vào session
            session.put("posts", posts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public List<Post> getPosts() {
		return posts;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
