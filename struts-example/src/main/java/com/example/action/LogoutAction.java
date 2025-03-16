package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.ServletActionContext;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 1L;
    private Map<String, Object> session;

    public String execute() {
        // Xóa session
        if (session != null) {
            session.clear();
        }
        
        // Chuyển hướng bằng sendRedirect
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.sendRedirect("login.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return NONE; // Trả về NONE vì đã xử lý điều hướng
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}