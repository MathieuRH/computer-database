<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>" media="screen"/> 
<link rel="stylesheet" href="<c:url value='/resources/css/font-awesome.css'/>" media="screen"/> 
<link rel="stylesheet" href="<c:url value='/resources/css/main.css'/>" media="screen"/> 
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>
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
                                <label for="computerName">Computer name</label>
                                <form:input path="name" type="text" class="form-control" id="computerName" name="computerName" 
                                	placeholder="Computer name" value="${computerDTO.name}"></form:input>
                            </div>
                            
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <form:input path="introduced" type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" 
                                value='${computerDTO.introduced!=null?computerDTO.introduced:""}'/>
                           </div>
                            
                            
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <form:input path="discontinued" type="date" class="form-control" id="discontinued" name="discontinued" 
                                placeholder="Discontinued date" 
                                value='${computerDTO.discontinued!=null?computerDTO.discontinued:""}'/>
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
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
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
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