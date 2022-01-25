<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>View Books</title>
    </head>
    <body>
    <div>
    <h3> Choose File to Upload in Server </h3>
       <form action="upload" method="post" enctype="multipart/form-data">
           <input type="file" name="file" />
           <input type="submit" value="upload" />
       </form>
    </div>

    </body>
</html>




