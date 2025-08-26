<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Recherche de livres</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	</head>
	
	<body>
		<%@ include file="shared/header.jsp" %>
		
		<div class="container mt-2">
			<h2>Recherche de livres</h2>
			
			<form method="get" action="/search" class="d-flex mb-4">
				<div class="form-group flex-grow-1 me-2">
					<input type="text" name="keywords" class="form-control" placeholder="Entrez un mot-clé (titre, auteur...)" required>
				</div>
				<button type="submit" class="btn btn-primary">Rechercher</button>
			</form>
			
			<c:forEach var="book" items="${books}">
				<div class="bg-dark text-white p-3 my-2 rounded">
					<p>Titre : <b>${book.title} - </b>Auteur : <b>${book.author}</b></p>
					<p>Publié le <b>${book.releaseDate}</b></p>
					<p>Il reste <b>${book.availableCopies}</b> copie(s) disponible(s) sur <b>${book.totalCopies}</b></p>

                    <c:if test="${book.reservable}">
                        <hr />

                        <div class="d-flex align-items-center">
                            <c:if test="${not empty sessionScope.user && book.reservableByUser}">
                                <button type="submit" class="btn btn-primary my-1 me-2" onclick="reserveBook(${book.id})">Réserver</button>
                            </c:if>

                            <p class="mb-0 me-4"><b><c:out value="${book.reservations.size()}" /></b> en file d'attente.</p>
                            <p class="mb-0">Prochaine copie disponible : <b><c:out value="${book.nextAvailableCopy}" /></b></p>
                        </div>
                    </c:if>
				</div>
			</c:forEach>
		</div>

		<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
        <script src="js/script.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	</body>
</html>