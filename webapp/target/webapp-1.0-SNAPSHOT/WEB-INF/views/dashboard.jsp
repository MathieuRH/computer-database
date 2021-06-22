<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>" media="screen"/> 
<link rel="stylesheet" href="<c:url value='/resources/css/font-awesome.css'/>" media="screen"/> 
<link rel="stylesheet" href="<c:url value='/resources/css/main.css'/>" media="screen"/> 
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top flex">
        <div class="container">
              <a class="navbar-brand" href="dashboard"><fmt:message key="label.homeRef"/></a>
			  <ul class="nav navbar-nav navbar-right">
		        <li class="dropdown">
		        	<a class="dropdown-toggle" data-toggle="dropdown" href="#">
		        			<fmt:message key="label.changeLang" /><span class="caret"></span></a>
		        	<ul class="dropdown-menu">
			        	<li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
				        <li><a href="?lang=fr"><fmt:message key="label.lang.fr" /></a></li>
			        </ul>
		        </li> 
			    <li><a class="pull-right" href="<c:url value="/logout" />"><span class="glyphicon glyphicon-log-in"></span> 
						<fmt:message key="label.logout" /></a></li>
	    	</ul>
        </div>
    </header>

    <section id="main">
	    <sec:authorize access='hasRole("ADMIN")'>
			  <a class="btn btn-success" id="adminPage" href="adminPage"><fmt:message key="label.adminPage"/></a>
		</sec:authorize>
    
        <div class="container">
            <h1 id="homeTitle">
                ${nbComputers} <fmt:message key="label.foundComputers"/>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action=dashboard method="GET" class="form-inline">
                        <input type="search" id="searchbox" name="computer_name_request" class="form-control" placeholder="<fmt:message key="label.nameSearch"/>" />
                        <input type="submit" id="searchsubmit" value="<fmt:message key="label.nameFilter"/>" class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer"><fmt:message key="label.addComputer"/></a>
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><fmt:message key="label.edit"/></a>
		        </div>
            </div> 
        </div>

        <form id="deleteForm" action="dashboard" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th><fmt:message key="label.name"/>
                        	<a class="align-middle pull-right" id="orderByName" href="<c:url value="dashboard"> <c:param name="request_session" value="orderByName"/></c:url>">
                        	 <i class="fa fa-sort"></i></a>
                        </th>
                        <th><fmt:message key="label.introduced"/>
	                        <a class="align-middle pull-right" id="orderByIntroduced" href="<c:url value="dashboard"> <c:param name="request_session" value="orderByIntroduced"/></c:url>">
	                        <i class="fa fa-sort"></i></a>
	                    </th>
                        <th><fmt:message key="label.discontinued"/>
                        	<a class="align-middle pull-right" id="orderByDiscontinued" href="<c:url value="dashboard"> <c:param name="request_session" value="orderByDiscontinued"/></c:url>">
                        	<i class="fa fa-sort"></i></a>
                        </th>
                        <th><fmt:message key="label.company"/>
                        	<a class="align-middle pull-right" id="orderByCompany" href="<c:url value="dashboard"> <c:param name="request_session" value="orderByCompany"/></c:url>">
                        	<i class="fa fa-sort"></i></a>
                        </th>
                    </tr>
                </thead>

                <tbody id="results">
               <c:forEach items="${listComputersDTO}" var="computerDTO">
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="${computerDTO.id}">
                        </td>
                        <td>
                            <a href="<c:url value="editComputer"> <c:param name="computerId" value="${computerDTO.id}"/></c:url>" id="useless" onclick="">${computerDTO.name}</a>
                        </td>
                        <td>${computerDTO.introduced}</td>
                        <td>${computerDTO.discontinued}</td>
                        <td>${computerDTO.companyName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </section>


    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
	     	<div class="btn-group btn-group-sm pull-left">
	        	<a class="btn btn-default" id="orderById" href="<c:url value="dashboard"> 
	        		<c:param name="request_session" value="orderById"/></c:url>"><fmt:message key="label.home"/></a>
	        </div>
            <ul class="pagination" ${page=page_session.page } ${page_max=page_session.nbPages }>
              <li>
               	<a href="<c:url value="dashboard"> <c:param name="page_request" value="${page-5>1 ? page-5 : 1}"/></c:url>" aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
               	</a>
              </li>
              <li><a href="<c:url value="dashboard"> <c:param name="page_request" value="1"/></c:url>">1</a></li>
              <c:if test="${page_max>1}">
	              <c:if test="${page>4}"><li><a href=#>...</a></li></c:if>
	    
	              <c:forEach var="i" begin="${page>3 ? page-2 : page>2 ? page-1 :  page>1 ? page : page+1}" 
	              	end="${page<page_max-2 ? page+2 : page<page_max-1 ? page+1 : page<page_max ? page : page-1}" step="1">
		              <li><a href="<c:url value="dashboard"> <c:param name="page_request" value="${i}"/></c:url>">${i}</a></li>
	              </c:forEach>         
	              
	              <c:if test="${page<page_max-3}"><li><a href=#>...</a></li></c:if>
	              <li><a href="<c:url value="dashboard"> <c:param name="page_request" value="${page_max}"/></c:url>">${page_max}</a></li>
              </c:if>
              <li>
                <a href="<c:url value="dashboard"> <c:param name="page_request" value="${page+5<page_max ? page+5 : page_max}"/></c:url>" aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                </a>
           	  </li>
	        </ul>
		            
	        <div class="btn-group btn-group-sm pull-right" role="group" >
	        	<form action=dashboard method=GET>
		            <button type="submit" name="page_nb_comp" value=1 class="btn btn-default">1</button>
		            <button type="submit" name="page_nb_comp" value=10 class="btn btn-default" >10</button>
		            <button type="submit" name="page_nb_comp" value=100 class="btn btn-default">100</button>
	            </form>
	        </div>
		</div>
    </footer>
<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/resources/js/dashboard.js'/>"></script>

</body>
</html>