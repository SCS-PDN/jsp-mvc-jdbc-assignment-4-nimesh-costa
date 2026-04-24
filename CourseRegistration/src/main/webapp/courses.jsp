<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Courses</title>
</head>
<body>

<h2>Available Courses</h2>

<table border="1" cellpadding="10">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Instructor</th>
        <th>Credits</th>
        <th>Action</th>
    </tr>

    <c:forEach var="course" items="${courses}">
        <tr>
            <td>${course.course_id}</td>
            <td>${course.name}</td>
            <td>${course.instructor}</td>
            <td>${course.credits}</td>
            <td>
                <!-- POST FORM (IMPORTANT) -->
                <form action="register/${course.course_id}" method="post">
                    <button type="submit">Register</button>
                </form>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>