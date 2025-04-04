package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.example.model.Post;
import com.example.model.User;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.example.util.HibernateUtil;

import java.sql.Timestamp;
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

            Session hibernateSession = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = hibernateSession.beginTransaction();

            try {
                // Tìm user theo username
                User user = (User) hibernateSession.createQuery("FROM User WHERE username = :username")
                        .setParameter("username", username)
                        .uniqueResult();

                if (user == null) {
                    tx.rollback();
                    addActionError("Người dùng không tồn tại!");
                    return ERROR;
                }

                // Tạo bài viết mới
                Post post = new Post();
                post.setTitle(title);
                post.setBody(body);
                post.setStatus("published");
                post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                post.setUser(user);

                hibernateSession.save(post);
                tx.commit();

                ServletActionContext.getResponse().sendRedirect("home.action");
                return NONE;
            } catch (Exception e) {
                tx.rollback();
                e.printStackTrace();
                addActionError("Lỗi khi đăng bài!");
                return ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    // Getters & Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}