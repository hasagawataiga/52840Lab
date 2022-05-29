package com.hoangvoletuan.controller.api;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoangvoletuan.model.BookModel;
import com.hoangvoletuan.service.ILibraryService;
import com.hoangvoletuan.util.HttpUtil;

@WebServlet(urlPatterns = {"/api-home"})
public class BookAPI extends HttpServlet {

	@Inject
	private ILibraryService libraryService;
	private static final long serialVersionUID = 6890982504150843646L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("JSON");
		BookModel bookModel = HttpUtil.of(request.getReader()).toModel(BookModel.class);
		bookModel = libraryService.save(bookModel);
		System.out.println(bookModel);
		mapper.writeValue(response.getOutputStream(), bookModel);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("JSON");
		BookModel updatedBook =  HttpUtil.of(request.getReader()).toModel(BookModel.class);
		updatedBook = libraryService.update(updatedBook);
		mapper.writeValue(response.getOutputStream(), updatedBook);
	}

	/*protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}*/
	
}
