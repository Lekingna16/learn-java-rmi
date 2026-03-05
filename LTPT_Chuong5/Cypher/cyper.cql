//UNIQUE CONSTRAINT

CREATE CONSTRAINT student_email_uq IF NOT EXISTS
FOR (n:Student)
REQUIRE n.email IS NODE UNIQUE;

//KEY CONSTRAINT
CREATE CONSTRAINT student_pk IF NOT EXISTS
FOR (n:Student)
REQUIRE n.student_id IS NODE KEY;

CREATE CONSTRAINT course_pk IF NOT EXISTS
FOR (n:Course)
REQUIRE n.course_id IS NODE KEY;

CREATE CONSTRAINT dept_pk IF NOT EXISTS
FOR (n:Department)
REQUIRE n.dept_id IS NODE KEY;

SHOW CONSTRAINTS;

CREATE CONSTRAINT student_gpa IF NOT EXISTS
FOR (n:Student)
REQUIRE n.gpa IS :: FLOAT;


//LOAD FROM CSV FILES
LOAD CSV WITH HEADERS
FROM 'file:///courses/courses.csv' AS row
CREATE (c:Course{
course_id:row.course_id,
name:row.name,
hours: toInteger(row.hours)
});

LOAD CSV WITH HEADERS
FROM 'file:///courses/students.csv' AS row
CREATE (s:Student{
student_id:row.student_id,
name: row.name,
gpa:toFloat(row.gpa)
});

LOAD CSV WITH HEADERS
FROM 'file:///courses/departments.csv' AS row
CREATE (d:Department{
dept_id:row.dept_id,
name:row.name,
dean:row.dean,
building:row.building,
room:row.room
});

//Tao relationships

LOAD CSV WITH HEADERS
FROM 'file:///courses/courses.csv' AS row
MATCH (c:Course{course_id:row.course_id})
MATCH (d:Department{dept_id:row.dept_id})
MERGE (c) - [:BELONGS_TO] -> (d);

LOAD CSV WITH HEADERS
FROM 'file:///courses/enrollments.csv' AS row
MATCH (c:Course{course_id:row.course_id})
MATCH (s:Student{student_id:row.student_id})
MERGE (s) - [:ENROLLED_IN {semester:row.semester, grade:toInteger(row.grade)}] -> (c);