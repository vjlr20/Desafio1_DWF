<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en" dir="ltr">
    <head>
        <meta charset="UTF-8">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>Empresas - Administrador</title>
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
                        <h3 class="text-center">Empresas</h3>
                    </div>
                    <div class="row">
                        <div class="col-12">
							<div class="container-fluid">
								<a class="btn btn-primary" href="${pageContext.request.contextPath}/empresas.do?op=nuevaEmpresa">Registrar nueva empresa</a>
							</div>
                            <br>
                            <table class="table table-striped table-hover" id="myTable">
                                <thead class="table-dark text-center">
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Codigo</th>
                                        <th scope="col">Empresa</th>
                                        <th scope="col">Contacto</th>
                                        <th scope="col">Telefono</th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody class="text-center">
                                	<c:forEach items="${requestScope.empresas}" var="empresa">
                                		<tr>
	                                        <th scope="row">${empresa.empresa_id}</th>
	                                        <td>${empresa.codigo_empresa}</td>
	                                        <td>${empresa.nombreEmpresa}</td>
	                                        <td>${empresa.contactoEmpresa}</td>
	                                        <td>${empresa.telefonoEmpresa}</td>
	                                        <td>
	                                            <div class="row">
	                                                <div class="col-12">
	                                                    <a href="${pageContext.request.contextPath}/admin.do?op=ver-empresa&codigo=${empresa.empresa_id}" class="btn btn-info">Ver</a>
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
