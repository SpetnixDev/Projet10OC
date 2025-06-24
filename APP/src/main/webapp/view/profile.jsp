<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Profil</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	</head>
	
	<body>
		<%@ include file="shared/header.jsp" %>
		
		<div class="container mt-2">
			<h2 class="text-center my-4">Profil</h2>
		
			<div class="border border-dark border-1 rounded mb-3 p-2">
				<h2>Informations</h2>
			
				<p class="my-0"><b>Email : </b></b><c:out value="${sessionScope.user.email}" /></p>
				<p class="my-0"><b>Prénom : </b></b><c:out value="${sessionScope.user.firstName}" /></p>
				<p class="my-0"><b>Nom : </b><c:out value="${sessionScope.user.lastName}" /></p>
			</div>
			
			<c:choose>
				<c:when test="${not empty sessionScope.user.loans}">
					<h3 class="text-primary text-center">Emprunts en cours</h3>
					
					<c:forEach var="loan" items="${sessionScope.user.loans}">
						<div class="border border-dark border-1 rounded mt-2 p-3">
							<p class="my-1">Titre : <b>${loan.bookTitle}</b> - Auteur : <b>${loan.bookAuthor}</b></p>
							<p class="my-1">A retourner le : <b>${loan.returnDate}</b></p>
							
							<c:choose>
								<c:when test="${loan.extended == false}">
									<button type="submit" class="btn btn-primary my-1" onclick="extendLoan(${loan.id})">Prolonger le prêt</button>
								</c:when>
							
								<c:otherwise>
									<p class="text-primary my-1">Vous avez déjà prolongé ce prêt</p>
								</c:otherwise>
							</c:choose>
						</div>
					</c:forEach>
				</c:when>
				
				<c:otherwise>
					<h3 class="text-danger text-center">Vous n'avez aucun emprunt en cours</h3>
				</c:otherwise>
			</c:choose>
		</div>
		
		<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
		<script src="js/script.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	</body>
</html>