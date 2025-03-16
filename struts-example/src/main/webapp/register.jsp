<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng Ký</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            width: 400px;
        }
        h2 {
            text-align: center;
        }
        .error {
            color: red;
            font-size: 14px;
            text-align: center;
        }
        .input-group {
            margin-bottom: 10px;
            display: flex;
            flex-direction: column;
        }
        label {
            font-weight: bold;
            margin-bottom: 5px;
        }
        .form-control {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .submit-btn {
            width: 100%;
            padding: 10px;
            background-color: #28a745;
            border: none;
            color: white;
            font-size: 16px;
            cursor: pointer;
            border-radius: 4px;
            margin-top: 10px;
        }
        .submit-btn:hover {
            background-color: #218838;
        }
        .login-link {
            text-align: center;
            margin-top: 10px;
        }
        .login-link a {
            text-decoration: none;
            color: #007bff;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Đăng Ký</h2>

        <!-- Hiển thị lỗi nếu có -->
        <% 
            if (request.getAttribute("errors") != null) { 
        %>
            <div class="error">
                <%= request.getAttribute("errors") %>
            </div>
        <% } %>

        <!-- Form đăng ký HTML thuần -->
        <form action="register" method="post">
            <div class="input-group">
                <label for="username">Tên đăng nhập:</label>
                <input type="text" name="username" id="username" class="form-control" required>
            </div>

            <div class="input-group">
                <label for="password">Mật khẩu:</label>
                <input type="password" name="password" id="password" class="form-control" required>
            </div>

            <div class="input-group">
                <label for="confirmPassword">Xác nhận mật khẩu:</label>
                <input type="password" name="confirmPassword" id="confirmPassword" class="form-control" required>
            </div>

            <button type="submit" class="submit-btn">Đăng Ký</button>
        </form>

        <div class="login-link">
            <p>Đã có tài khoản? <a href="login.jsp">Đăng nhập</a></p>
        </div>
    </div>
</body>
</html>
