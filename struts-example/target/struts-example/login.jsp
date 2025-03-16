<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<title>Login</title>
</head>
<body>
	<h2>Login</h2>
	<s:form action="login" method="post">
		<s:textfield name="username" label="Username" required="true" />
		<s:password name="password" label="Password" required="true" />
		<s:submit value="Login" />
	</s:form>

</body>
</html>
