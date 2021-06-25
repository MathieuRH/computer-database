<!DOCTYPE html>
<html>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><fmt:message key="label.login.title" /></h1>
                    <form action="login" method="POST" name="f">
                        <fieldset>
                        	<%-- Error message --%>
                        	<c:if test="${ error }">
                        	<div class="form-group alert alert-danger">
                        		<p><fmt:message key="label.login.error" /></p>
                                ${ errorMessage }
                            </div>
                        	</c:if>
                            <div class="form-group">
                                <label for="username">
                                	<fmt:message key="label.login.field.username" />
                                </label>
                                <input name="username" type="text" class="form-control" required />
                            </div>
                            <div class="form-group">
                                <label for="password">
                                	<fmt:message key="label.login.field.password" />
								</label>
                                <input name="password" value="" type="password" class="form-control" required />
                            </div>               
                        </fieldset>
                        <div class="actions pull-right">
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input type="submit" value='<fmt:message key="label.login.button" />' class="btn btn-primary">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
	
<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/resources/js/dashboard.js'/>"></script>

</body>
</html>