<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*,java.util.*"%>
<%
String title=request.getParameter("title");
String author=request.getParameter("author");
LibraryDao libraryDao = new LibraryDao();
BookModel bookModel = new BookModel();
bookModel.setTitle(title);
bookModel.setAuthor(author);
Long id = libraryDao.save();
out.print("id: " + id);
 %>