<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="bg-dark py-2">
	<div class="container">
		<nav class="navbar navbar-expand-lg navbar-light">
			<button class="navbar-toggler bg-white" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
	          	<span class="navbar-toggler-icon"></span>
	        </button>
	        
	        <div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item px-2">
						<a class="nav-link text-white" href="/">Accueil</a>
					</li>
					<li class="nav-item px-2">
						<a class="nav-link text-white" href="/search">Recherche</a>
					</li>
				</ul>
				
				<c:choose>
					<c:when test="${empty sessionScope.user}">
						<ul class="navbar-nav ms-auto">
							<li class="nav-item px-2">
								<a class="nav-link text-white" href="/login">Connexion</a>
							</li>
							<li class="nav-item px-2">
								<a class="nav-link text-white" href="/signup">Inscription</a>
							</li>
						</ul>
					</c:when>

					<c:otherwise>
						<ul class="navbar-nav ms-auto">
							<li class="nav-item px-2">
								<a class="nav-link text-white px-3" href="/profile">${sessionScope.user.firstName}</a>
							</li>
							<li class="nav-item px-2">
								<a class="nav-link text-white px-3" href="/logout">Déconnexion</a>
							</li>
						</ul>
					</c:otherwise>
				</c:choose>
	        </div>
		</nav>
	</div>
</header>