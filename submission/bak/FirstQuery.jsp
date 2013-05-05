<html>
<head><title>INFO613 Project</title></head>
<body>
<h1>INFO613 Project</h1>
<h2>By Dustin Overmiller and Justin Kambic</h2>
<h3>Query to find all movies directed by Steven Spielburg</h3>
<p>This will query the database and display all movie titles and associated
genres for movies directed by Steven Spielburg
<p><%
	String result = project.FirstQuery.doQuery();
	out.print(result);
  %>
</body>
</html>