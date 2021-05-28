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
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${nbComputers} Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a>
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
		        </div>
            </div> 
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <th>
                            Discontinued date
                        </th>
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
               <c:forEach items="${listComputersDTO}" var="computerDTO"> 
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="${computerDTO.id}">
                        </td>
                        <td>
                            <a href="editComputer.jsp" onclick="">${computerDTO.name}</a>
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
            <ul class="pagination">
              <li>
               	<a href="<c:url value="dashboard"> <c:param name="page_request" value="${page>1 ? page-1 : page}"/></c:url>" aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
               	</a>
              </li>
              <li><a href="<c:url value="dashboard"> <c:param name="page_request" value="1"/></c:url>">1</a></li>
              <c:if test="${page>4}"><li><a href=#>...</a></li></c:if>
    
              <c:forEach var="i" begin="${page>3 ? page-2 : page>2 ? page-1 :  page>1 ? page : page+1}" 
              	end="${page<page_max-2 ? page+2 : page<page_max-1 ? page+1 : page<page_max ? page : page-1}" step="1">
	              <li><a href="<c:url value="dashboard"> <c:param name="page_request" value="${i}"/></c:url>">${i}</a></li>
              </c:forEach>         
              
              <c:if test="${page<page_max-3}"><li><a href=#>...</a></li></c:if>
              <li><a href="<c:url value="dashboard"> <c:param name="page_request" value="${page_max}"/></c:url>">${page_max}</a></li>
              <li>
                <a href="<c:url value="dashboard"> <c:param name="page_request" value="${page<page_max ? page+1 : page}"/></c:url>" aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                </a>
           	  </li>
	        </ul>
		            
	        <div class="btn-group btn-group-sm pull-right" role="group" >
	        	<form action=dashboard method=POST>
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