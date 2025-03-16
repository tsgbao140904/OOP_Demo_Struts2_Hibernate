<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>Share Post New</title>
</head>
<body>
    <h2>Share Post New</h2>

    <!-- Form đăng bài -->
    <s:form action="createPost" method="post">
        <label for="title">Title:</label>
        <s:textfield name="title" required="true"/><br><br>

        <label for="body">Content:</label>
        <s:textarea name="body" required="true"/><br><br>

        <s:submit value="Share"/>
    </s:form>

    <!-- Hiển thị lỗi nếu có -->
    <s:actionerror />

    <br>
    <a href="home.jsp">Quay lại trang chủ</a>
</body>
</html>
