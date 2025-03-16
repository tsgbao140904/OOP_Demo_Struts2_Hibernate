<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng Nhập</title>
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
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);
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
            margin-bottom: 15px;
            display: flex;
            align-items: center;
        }
        label {
            font-weight: bold;
            width: 120px;
            margin-right: 10px;
        }
        .form-control {
            flex: 1;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .submit-btn {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            border: none;
            color: white;
            font-size: 16px;
            cursor: pointer;
            border-radius: 4px;
            margin-top: 10px;
        }
        .submit-btn:hover {
            background-color: #0056b3;
        }
        .register-link {
            text-align: center;
            margin-top: 10px;
        }
        .register-link a {
            text-decoration: none;
            color: #007bff;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Đăng Nhập</h2>

        <!-- Hiển thị lỗi nếu có -->
        <s:if test="hasActionErrors()">
            <div class="error">
                <s:actionerror />
            </div>
        </s:if>

        <!-- Form đăng nhập HTML thuần -->
        <form action="login" method="post">
            <div class="input-group">
                <label for="username">Tên đăng nhập:</label>
                <input type="text" name="username" id="username" class="form-control" required>
            </div>

            <div class="input-group">
                <label for="password">Mật khẩu:</label>
                <input type="password" name="password" id="password" class="form-control" required>
            </div>

            <button type="submit" class="submit-btn">Đăng Nhập</button>
        </form>

        <div class="register-link">
            <p>Chưa có tài khoản? <a href="register.jsp">Đăng ký</a></p>
        </div>
    </div>
</body>
</html>
