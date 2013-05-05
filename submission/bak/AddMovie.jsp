<html>
<head><title>INFO613 Project</title></head>
<body>
<h1>INFO613 Project</h1>
<h2>By Dustin Overmiller and Justin Kambic</h2>
<h3>Add a Movie to the Database</h3>
<p>This page will allow you to select a movie xml file and add it to the Oracle Database
<p>Select a movie xml file to upload: <br />
<form action="AddMovie.jsp" method="post"
                        enctype="multipart/form-data">
<input type="file" name="file" size="50" />
</br>
</br>
<input type="submit" value="Upload File" />
</form>

<%@ page import="java.io.*" %>
<%
try
{
	String contentType = request.getContentType();
	if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
	{
		/* get the data size */
		int formDataLength = request.getContentLength();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		/* initialize the byte array */
		byte dataBytes[] = new byte[formDataLength];
		
		/* keep reading until there are no more bytes left */
		int byteRead = 0;	
		while ((byteRead = request.getInputStream().read(dataBytes, 0, dataBytes.length)) != -1) {
			buffer.write(dataBytes, 0, byteRead);
		}
		buffer.flush();
		
		/* convert this back to a byte array */
		dataBytes = buffer.toByteArray();

		String file = new String(dataBytes);
		String saveFile = file.substring(file.indexOf("filename=\"") + 10);
		if (saveFile.indexOf(".xml") != -1)
		{
			saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
			saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
			saveFile = request.getRealPath("/") + "movies/" + saveFile;
		
			int lastIndex = contentType.lastIndexOf("=");
			String boundary = contentType.substring(lastIndex + 1,contentType.length());
			int pos;
			pos = file.indexOf("filename=\"");
			
			pos = file.indexOf("\n", pos) + 1;
			pos = file.indexOf("\n", pos) + 1;
			pos = file.indexOf("\n", pos) + 1;
			
			int boundaryLocation = file.indexOf(boundary, pos) - 4;
			int startPos = ((file.substring(0, pos)).getBytes()).length;
			int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
			
			FileOutputStream fileOut = new FileOutputStream(saveFile);
			
			fileOut.write(dataBytes, startPos, (endPos - startPos));
			fileOut.flush();
			fileOut.close();
			
			String result = project.AddMovie.addMovie(saveFile);
						
			if (result.indexOf("Success") == -1)
			{
				/* remove the file */
				File delFile = new File(saveFile);
				delFile.delete();
				out.print("</br><b>" + result + "</b></br>");
			}
			else
			{
				out.print("</br><b>" + result + "<font color=\"green\"> and movies subdirectory</font></b></br>");
			}
		}
		else
		{
			out.print("</br><b><font color=\"red\"> Error: XML file needs to be specified</font></b></br>");
		}
	}
}
catch (Exception e)
{
	out.print("</br><font color=\"red\"><b>Upload failed</b></font></br>");
}

%>

</body>
</html>