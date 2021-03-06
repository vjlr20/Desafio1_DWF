<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<title>Inicio de Sesion</title>
</head>
<body>
	<%@ include file="navbarCliente.jsp"%>
	<div class="container">
		<c:if test="${not empty listaEventos}">
			<div class="alert alert-dark">
				<ul>
					<c:forEach var="eventos" items="${requestScope.listaEventos}">
						<li>${eventos}</li>
					</c:forEach>
				</ul>
			</div>
		</c:if>
		<div class="row">
			<div class="col-12 mt-5">
				<div class="d-flex justify-content-center align-items-center">
					<form class="w-25 shadow-lg p-3 mb-5 bg-white rounded p-5"
						method="POST"
						action="${pageContext.request.contextPath}/clientes.do">
						<input hidden="hidden" value="updatePassword" name="op"> <input
							value="${sessionScope.usser}" name="clienteID" hidden>
						<div class="form-group mt-3">
							<span>Ingrese su contraseña actual</span> <input type="password"
								class="form-control" required name="actualPassword"
								placeholder="contraseña actual">
						</div>
						<div class="form-group mt-3">
							<span>Contraseña nueva</span> <input type="password"
								class="form-control" name="newPassword" required
								placeholder="nueva contraseña">
						</div>
						<div class="text-center mt-5">
							<button type="submit" class="btn btn-primary">Actualizar</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>