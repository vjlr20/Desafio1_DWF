<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en" dir="ltr">
    <head>
        <meta charset="UTF-8">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>Inicio - Administrador</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
		<link href="//cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css" rel="stylesheet" type=text/css>
		<link href="${pageContext.request.contextPath}/public/css/estiloadmin.css" rel="stylesheet" type=text/css>
    </head>
    <body>
        <%@ include file="menu.jsp" %>
        <main>
            <section class="py-5">
                <div class="container">
                    <div class="pb-3">
                        <h3 class="text-center">Ofertas</h3>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <nav class="nav nav-pills nav-justified">
                                <a class="nav-link active" aria-current="page" href="admin.do?op=home">Todas las ofertas</a>
                                <a class="nav-link" href="admin.do?op=standby">Ofertas pendientes de aprobación</a>
                            </nav>
                            <br>
                            <table class="table table-striped table-hover" id="myTable">
                                <thead class="table-dark text-center">
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Oferta</th>
                                        <th scope="col">Empresa</th>
                                        <th scope="col">Fecha de publicación</th>
                                        <th scope="col">Estado</th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody class="text-center">
                                	<c:forEach items="${requestScope.ofertas}" var="oferta">
                                		<tr>
	                                        <th scope="row">${oferta.idOferta}</th>
	                                        <td>${oferta.tituloOferta}</td>
	                                        <td>${oferta.nombreEmpresa}</td>
	                                        <td>${oferta.fechaRegistroOferta}</td>
	                                        <td>
	                                        	<c:if test="${oferta.estadoOferta == 1}">
	                                        		Aprobada
	                                        	</c:if>
	                                        	<c:if test="${oferta.estadoOferta == 2}">
	                                        		Rechazada
	                                        	</c:if>
	                                        	<c:if test="${oferta.estadoOferta == 3}">
	                                        		Pendiente de aprobación
	                                        	</c:if>
	                                        </td>
	                                        <td>
	                                            <div class="row">
	                                                <div class="col-12">
	                                                    <a href="${pageContext.request.contextPath}/admin.do?op=details&codigo=${oferta.idOferta}" class="btn btn-info">Ver</a>
	                                                </div>
	                                            </div>
	                                        </td>
	                                    </tr>
                                	</c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </section>
        </main>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="//cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>
        <script src="https://use.fontawesome.com/7e8731e53c.js"></script>
        <script src="${pageContext.request.contextPath}/public/js/dataTable.js"></script>
    </body>
</html>