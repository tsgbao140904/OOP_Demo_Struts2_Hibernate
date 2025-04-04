package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.example.model.Post;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 1L;
    private List<Post> posts;
    private Map<String, Object> session;
    private int id; // ID bài viết cần xóa

    public String execute() {
        posts = new ArrayList<>();
        Session hibernateSession = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = hibernateSession.beginTransaction();

        try {
            posts = hibernateSession.createQuery("FROM Post p ORDER BY p.createdAt DESC", Post.class).getResultList();
            session.put("posts", posts);
            tx.commit();

            HttpServletResponse response = ServletActionContext.getResponse();
            response.sendRedirect("home.jsp");
            return NONE;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return ERROR;
        }
    }

    public String deletePost() {
        System.out.println("ID cần xóa: " + id);

        Session hibernateSession = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = hibernateSession.beginTransaction();

        try {
            Post post = hibernateSession.get(Post.class, id);
            if (post != null) {
                hibernateSession.delete(post);
                session.put("message", "Xóa bài viết thành công!");
            } else {
                session.put("error", "Không tìm thấy bài viết để xóa!");
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            session.put("error", "Lỗi khi xóa bài viết!");
        }

        // Tải lại danh sách bài viết
        loadPosts();

        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.sendRedirect("home.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return NONE;
    }

    private void loadPosts() {
        posts = new ArrayList<>();
        Session hibernateSession = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = hibernateSession.beginTransaction();

        try {
            posts = hibernateSession.createQuery("FROM Post p ORDER BY p.createdAt DESC", Post.class).getResultList();
            session.put("posts", posts);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    // Getters & Setters
    public List<Post> getPosts() { return posts; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}