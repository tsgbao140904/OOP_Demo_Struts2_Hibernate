<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Share Post New</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 500px;
            background: white;
            padding: 20px;
            margin: 50px auto;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #333;
        }
        label {
            font-weight: bold;
            display: block;
            margin: 10px 0 5px;
            text-align: left;
        }
        input, textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 15px;
        }
        input:focus, textarea:focus {
            border-color: #007BFF;
            outline: none;
        }
        .btn {
            background: #007BFF;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
        }
        .btn:hover {
            background: #0056b3;
        }
        .error {
            color: red;
            margin-bottom: 10px;
        }
        .back-link {
            display: block;
            margin-top: 20px;
            color: #007BFF;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Share Post New</h2>
        
        <!-- Hiển thị lỗi nếu có -->
        <s:if test="hasActionErrors()">
            <div class="error">
                <s:actionerror />
            </div>
        </s:if>

        <!-- Form đăng bài HTML thuần -->
        <form action="createPost" method="post">
            <label for="title">Title:</label>
            <input type="text" name="title" id="title" required>
            
            <label for="body">Content:</label>
            <textarea name="body" id="body" required></textarea>
            
            <button type="submit" class="btn">Share</button>
        </form>
        
        <a href="home.jsp" class="back-link">Quay lại trang chủ</a>
    </div>
</body>
</html>
