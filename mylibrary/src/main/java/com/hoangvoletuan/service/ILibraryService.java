package com.hoangvoletuan.service;

import java.util.List;

import com.hoangvoletuan.model.BookModel;

public interface ILibraryService {
	List<BookModel> findAll();
	BookModel save(BookModel bookModel);
	BookModel update(BookModel updatedBook);
}
