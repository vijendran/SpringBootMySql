<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<body>

	<h2>Table</h2>

	<p><b></b><p>

	<p><b>Iterated List:</b><p>


  <ul>
		<c:forEach var="user" items="${data}">
			<li style="list-style: none !important;">${user.id}  &nbsp; &nbsp;  ${user.name} &nbsp; &nbsp; ${user.dob} &nbsp; &nbsp; ${user.age} &nbsp; &nbsp; ${user.mobileNumber}</li>
		</c:forEach>
	</ul>

</body>
</html>