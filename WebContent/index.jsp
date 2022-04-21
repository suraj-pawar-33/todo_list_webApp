<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>login</title>
</head>
<style>
* {
	margin: 0px;
	padding: 0px;
}

.wrapper {
	width: 70%;
	height: auto;
	background-color: #cc6600;
	margin: 0px auto;
	padding: 5px;
	display: flex;
}

#message {
	width: 100%;
	margin-top: 20px;
	height: 25px;
	font-size: 10px;
	font-weight: normal;
	color: red;
}

.field {
	width: 100%;
	margin-top: 20px;
	height: 25px;
	font-size: 20px;
}

.btn {
	padding: 10px;
	background-color: #cc6600;
	font-weight: bold;
	font-size: 20px;
	color: white;
	margin-top: 20px;
	border: 0px;
	outline: none;
}

.btn:hover {
	background-color: #b35900;
	cursor: pointer;
}

.small_btn {
	padding: 5px 8px 5px 8px;
	margin: 5px;
	background-color: #cc6600;
	font-size: 13px;
	color: white;
	margin-left: 20px;
	border: 0px;
	border-radius: 8px;
	outline: none;
	border-bottom: 1px solid gray;
	border-left: 1px solid gray;
}

.inner {
	padding: 3%;
}

.list_div {
	overflow-y: scroll;
	height: 500px;
	width: 94%;
}

.left {
	flex: 2;
	background-color: white;
	display: inline-block;
	font-weight: bold;
	font-size: 20px;
}

.right {
	flex: 4;
	background-color: #ffc266;
}

@media ( max-width :800px) {
	.wrapper {
		flex-direction: column;
	}
}
</style>

<body>

	<div class="wrapper">
		<div class="inner left"></div>
		<div class="right">
			<form action="index" method="post">
				<p>${message}</p>
				<button name="btn" value="signin">Sign in</button>
			</form>
		</div>
	</div>
</body>
</html>