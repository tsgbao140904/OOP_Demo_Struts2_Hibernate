<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #f4f4f4;
            padding: 10px;
        }
        .post {
            border-bottom: 1px solid #ddd;
            padding: 10px 0;
        }
        .logout-btn {
            background-color: red;
            color: white;
            padding: 5px 10px;
            text-decoration: none;
            border-radius: 5px;
        }
        .logout-btn:hover {
            background-color: darkred;
        }
    </style>
</head>
<body>

<!-- Thanh tiêu đề -->
<div class="header">
    <h2>Welcome, <s:property value="#session.username"/>!</h2>
    <a href="logout.action" class="logout-btn">Đăng xuất</a>
</div>

<!-- Nút tạo bài viết mới -->
<p><a href="createPost.jsp">📢 Share Post New</a></p>

<!-- Danh sách bài viết -->
<h3>📝 Latest Posts</h3>
<s:iterator value="posts">
    <div class="post">
        <h4><s:property value="title"/></h4>
        <p><s:property value="body"/></p>
        <p><small>Posted by <s:property value="username"/> at <s:property value="createdAt"/></small></p>
    </div>
</s:iterator>

</body>
</html>
