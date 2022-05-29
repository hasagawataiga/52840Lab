<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.*,com.hoangvoletuan.dao.impl.*,com.hoangvoletuan.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!--Load main stylesheet-->
<link rel="stylesheet" href="css/style.css">
<!--Load font-->
<script src="https://kit.fontawesome.com/ab27088153.js"
	crossorigin="anonymous"></script>
<!--Load google font-->
<link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,700"
	rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Library</title>
</head>
<body>
	<div class="wrapper flex">

		<div class="sidebar">

			<!--Avatar-->
			<div class="avatar">
				<img src="img/avatar2.jpg" width="200" height="200" alt="Tuan Hoang" />
				<h1 class="name">Tuan Vo Le Hoang</h1>
				<h4 class="job">Fresher Developer</h4>
			</div>

			<!--Aboutme-->
			<div class="module">
				<h3 class="module_title">
					<i class="fa fa-user circle circle--medium"></i> <span>About
						me</span>
				</h3>
				<div class="moduleContent">
					<p></p>
				</div>
			</div>

			<!--Contact me-->
			<div class="module">
				<h3 class="module_title">
					<i class="fa fa-address-book-o circle circle--medium"></i> <span>Contact
						me</span>
				</h3>
				<div class="module_content">
					<ul>
						<li class="flex"><i
							class="fa fa-map-marker circle circle--small circle--white"></i>
							<div>Warszawa, Poland</div></li>
						<li class="flex"><i
							class="fa fa-globe circle circle--small circle--white"></i>
							<div>hoangvoletuan.com</div></li>
						<li class="flex"><i
							class="fa fa-envelope circle circle--small circle--white"></i>
							<div>hoangvoletuan1603@gmail.com</div></li>
						<li class="flex"><i
							class="fa fa-mobile circle circle--small circle--white"></i>
							<div>+48 663 448 013</div></li>
					</ul>
				</div>
			</div>

			<!--Programming languages-->
			<div class="module progress">
				<h3 class="module_title">
					<span>Programming languages</span>
				</h3>
				<div class="module_content">
					<ul>
						<li class="flex"><i
							class="fa fa-brands fa-java circle circle--small circle--white"></i>
							<div class="flex_1">
								<span>Java</span> <span class="progress_bar"><span
									style="width: 85%"></span>
							</div></li>
						<li class="flex"><i
							class="fa fa-html5 circle circle--small circle--white"></i>
							<div class="flex_1">
								<span>HTML/CSS/JS</span> <span class="progress_bar"><span
									style="width: 55%"></span>
							</div></li>
						<li class="flex"><i
							class="fa fa-brands fa-windows circle circle--small circle--white"></i>
							<div class="flex_1">
								<span>C#</span> <span class="progress_bar"><span
									style="width: 75%"></span>
							</div></li>

					</ul>
				</div>
			</div>

			<!--My skills-->
			<div class="module progress">
				<h3 class="module_title">
					<span>Technique skills</span>
				</h3>
				<div class="module_content">
					<ul>
						<li class="flex"><i
							class="fa fa-brands fa-unity circle circle--small circle--white"></i>
							<div class="flex_1">
								<span>Unity development</span> <span class="progress_bar"><span
									style="width: 60%"></span>
							</div></li>
						<li class="flex"><i
							class="fa fa-bullseye circle circle--small circle--white"></i>
							<div class="flex_1">
								<span>Object-Oriented Programming</span> <span
									class="progress_bar"><span style="width: 90%"></span>
							</div></li>
						<li class="flex"><i
							class="fa fa-brands fa-github circle circle--small circle--white"></i>
							<div class="flex_1">
								<span>Github</span> <span class="progress_bar"><span
									style="width: 70%"></span>
							</div></li>
						<li class="flex"><i
							class="fa-brands fa-linux circle circle--small circle--white"></i>
							<div class="flex_1">
								<span>Command Line Interface (CLI)</span> <span
									class="progress_bar"><span style="width: 55%"></span>
							</div></li>
					</ul>
				</div>
			</div>

			<!--Soft skills-->
			<div class="module">
				<h3 class="module_title">
					<i class="fa fa-paperclip circle circle--medium"></i> <span>Soft
						skills</span>
				</h3>
				<div class="module_content">
					<ul>

						<li class="flex"><i
							class="fa fa-share circle circle--small circle--white"></i> <span>Time-management</span>
						</li>
						<li class="flex"><i
							class="fa fa-share circle circle--small circle--white"></i> <span>Critical
								thinking</span></li>
						<li class="flex"><i
							class="fa fa-share circle circle--small circle--white"></i> <span>Adaptability</span>
						</li>
						<li class="flex"><i
							class="fa fa-share circle circle--small circle--white"></i> <span>Self-learning</span>
						</li>

						<li class="flex"><i
							class="fa fa-share circle circle--small circle--white"></i> <span>Responsibility</span>
						</li>
						<li class="flex"><i
							class="fa fa-share circle circle--small circle--white"></i> <span>Problem
								solving</span></li>

					</ul>
				</div>
			</div>
		</div>

		<div class="content">
			<div class="section">
				<h5 class="section_title">
					<span><a href="addBook.jsp">Add new book</a></span>
				</h5>
			</div>
			<div class="library">
				<h3>Library</h3>
				<%
				LibraryDao libraryDao = new LibraryDao();
				List<BookModel> books = new ArrayList<BookModel>();
				books = libraryDao.findAll();
				for (int i = 0; i < books.size(); i++) {
					out.print("<p>" + (i + 1) + ". ");
					out.print(books.get(i).getTitle());
					out.print("-" + books.get(i).getAuthor() + "\n");
				}
				%>
			</div>

		</div>
	</div>
</body>
</html>