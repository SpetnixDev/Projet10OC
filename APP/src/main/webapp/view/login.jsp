<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Connexion</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	</head>
	
	<body>
		<%@ include file="shared/header.jsp" %>
		
		<div class="container mt-2">
			<h2 class="text-center my-4">Connexion</h2>
		
			<form:form id="form" modelAttribute="userLoginDTO" class="needs-validation signup-form mx-3" method="post" action="login">
				<div class="row">
					<div class="col-md-6 mb-3">
						<form:label path="email" class="mb-2" for="email">Email</form:label>
						<form:input type="email" path="email" class="form-control" id="email" placeholder="Email" />
					
						<div class="invalid-feedback">Veuillez entrer un email valide</div>
					</div>
					
					<div class="col-md-6 mb-3">
						<form:label path="password" class="mb-2" for="password">Mot de passe</form:label>
						<form:input type="password" path="password" class="form-control" id="password" placeholder="Mot de passe" />
					</div>
				</div>
		
				<c:if test="${not empty error}">
					<p class="text-danger text-center">${error}</p>
				</c:if>
				
				<div class="text-center my-2">
					<button class="btn btn-primary" type="submit">Se connecter</button>
				</div>
			</form:form>		
		</div>
		
		<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
		<script src="js/script.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	</body>
</html>