<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Page d'accueil</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	</head>
	
	<body>
		<%@ include file="shared/header.jsp" %>
		
		<div class="container mt-2">
			<div class="bg-secondary p-2 mb-3 rounded">
				<h2>Bibliothèques de la ville</h2>
				
				<p class="mb-0">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
	    				 Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
	    				  Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
	    				 Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
			</div>
			
			<div class="row">
				<div class="col">
					<div class="bg-secondary p-2 rounded">
						<h3 class="m-0">Derniers livres ajoutés</h3>
						
						<c:forEach var="book" items="${books}">
							<div class="bg-dark text-white p-3 my-2 rounded">
								<p>Titre : <b>${book.title} - </b>Auteur : <b>${book.author}</b></p>
								<p>Publié le <b>${book.releaseDate}</b></p>
								<p>Il reste <b>${book.availableCopies}</b> copie(s) disponible(s) sur <b>${book.totalCopies}</b></p>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	</body>
</html>