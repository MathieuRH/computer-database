<%@ page pageEncoding="UTF-8" %>
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
			  	<li> 
			  		<a class="btn" id="dashboard" href="dashboard"><fmt:message key="label.home"/></a>
		        </li>
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
        <div class="container">
            <h1 id="homeTitle">
                ${nbUsers} <fmt:message key="label.foundUsers"/>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-right">
                    <a class="btn btn-success" id="addUser" href="addUser"><fmt:message key="label.addUser"/></a>
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><fmt:message key="label.edit"/></a>
		        </div>
            </div> 
        </div>

        <form id="deleteForm" action="adminPage" method="POST">
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
                        <th><fmt:message key="label.username"/>
                        </th>
                        <th><fmt:message key="label.email"/>
	                    </th>
                        <th><fmt:message key="label.role"/>
                        </th>
                    </tr>
                </thead>

                <tbody id="results">
               <c:forEach items="${listUsersDTO}" var="userDTO">
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="${userDTO.id}">
                        </td>
                        <td>
                            <a href="<c:url value="editUser"> <c:param name="userId" value="${userDTO.id}"/></c:url>" id="useless" onclick="">${userDTO.username}</a>
                        </td>
                        <td>${userDTO.email}</td>
                        <td>${userDTO.role}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </section>
    
<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/resources/js/dashboard.js'/>"></script>

</body>
</html>