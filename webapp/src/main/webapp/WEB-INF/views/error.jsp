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
			<div class="alert alert-danger text-center">
				Error ${error_number} : ${error_message}
				<br/>
				<!-- stacktrace -->
			</div>
		</div>
	</section>
	
<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/resources/js/dashboard.js'/>"></script>

</body>
</html>