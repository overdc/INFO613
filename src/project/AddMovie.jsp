<html>
<head><title>INFO613 Project</title></head>
<body>
<h1>INFO613 Project</h1>
<h2>By Dustin Overmiller and Justin Kambic</h2>
<h3>Add a Movie to the Database</h3>
<p>This page will allow you to select a movie xml file and add it to the Oracle Database
<p><%
	String fileName = "";
	String result = project.AddMovie.addMovie(fileName);
	out.print(result);
  %>
</body>
</html>