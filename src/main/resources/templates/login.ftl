<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8"></meta>
<meta name="unauthorized" content="true"></meta>
<title>Byteflair Authorization Server</title>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
</head>
<body>
	<div class="container">
		<div id="loginContentsDiv">
			<legend><center>Access your resources</center></legend>
			<form method="post" action="login">
				<label class="hidden-label" for="username">Username</label>
				<input id="username" value="" name="username" placeholder="username" ></input>
				<label class="hidden-label" for="Passwd">Password</label>
				<input id="password" type="password" name="password" placeholder="password" ></input>
				<input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<input id="login" class="rc-button rc-button-submit" type="submit" value="Log in" name="login"></input>
				<label class="rememberMe">
				    <input id="rememberMe" type="checkbox" checked="checked" value="yes" name="rememberMe"></input>
    				<span>Remember me in this computer</span>
    			</label>
    			<#if RequestParameters['error']??>
                	<div class="alert alert-danger">
                		There was a problem logging in. Please try again.
                	</div>
                </#if>
			</form>
		</div>
	</div>
</body>
</html>