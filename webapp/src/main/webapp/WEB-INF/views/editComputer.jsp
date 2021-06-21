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
    <header class="navbar navbar-inverse navbar-fixed-top">
    	<div class="container">
              <a class="navbar-brand" href="dashboard"><fmt:message key="label.homeRef"/></a>
			  <ul class="nav navbar-nav navbar-right">
		        <li class="dropdown">
		        	<a class="dropdown-toggle" data-toggle="dropdown" href="#">
		        			<fmt:message key="label.changeLang" /><span class="caret"></span></a>
		        	<ul class="dropdown-menu">
				        <li><a href="?computerId=${computerDTO.id}&lang=en"><fmt:message key="label.lang.en" /></a></li>
				        <li><a href="?computerId=${computerDTO.id}&lang=fr"><fmt:message key="label.lang.fr" /></a></li>
			        </ul>
		        </li> 
			    <li><a class="pull-right" href="<c:url value="/logout" />"><span class="glyphicon glyphicon-log-in"></span> 
						<fmt:message key="label.logout" /></a></li>
	    	</ul>
        </div>
    </header>
    
    
<fmt:message key="label.name" var="nameLabel"/>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        ${computerDTO.id}
                    </div>
                    <h1>Edit Computer</h1>

                    <form:form modelAttribute="computerDTO" action="editComputer" id="editComputer" name="addComputerForm" method="POST">
                        <form:input path="id" type="hidden" value="${computerDTO.id}" id="id" name="id"/> 
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><fmt:message key="label.name"/></label>
                                <form:input path="name" type="text" class="form-control" id="computerName" name="computerName" 
                                	placeholder="${nameLabel}" value="${computerDTO.name}"></form:input>
                            </div>
                            
                            <div class="form-group">
                                <label for="introduced"><fmt:message key="label.introduced"/></label>
                                <form:input path="introduced" type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" 
                                value='${computerDTO.introduced!=null?computerDTO.introduced:""}'/>
                           </div>
                            
                            
                            <div class="form-group">
                                <label for="discontinued"><fmt:message key="label.discontinued"/></label>
                                <form:input path="discontinued" type="date" class="form-control" id="discontinued" name="discontinued" 
                                placeholder="Discontinued date" 
                                value='${computerDTO.discontinued!=null?computerDTO.discontinued:""}'/>
                            </div>
                            <div class="form-group">
                                <label for="companyId"><fmt:message key="label.company"/></label>
                                <form:select path="companyId" class="form-control" id="companyId" name="companyId" >
                                	<option value="">--</option>
                                	<c:forEach items="${listCompanyDTO}" var="companyDTO">
                                    	<option value="${companyDTO.id}" <c:if test="${companyDTO.id==computerDTO.companyId}">selected</c:if>>
                                    		${companyDTO.name}</option>
                                    </c:forEach>
                                </form:select>
                            </div>          
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<fmt:message key="label.save"/>" class="btn btn-primary">
                            <fmt:message key="label.or"/>
                            <a href="dashboard" class="btn btn-default"><fmt:message key="label.cancel"/></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/jquery.validate.min.js'/>"></script>
<script src="<c:url value='/resources/js/form-validation.js'/>"></script>

</body>
</html>