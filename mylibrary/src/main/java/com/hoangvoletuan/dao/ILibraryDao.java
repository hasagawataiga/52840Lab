package com.hoangvoletuan.dao;

import java.util.List;

import com.hoangvoletuan.model.BookModel;

public interface ILibraryDao extends GenericDao<BookModel>{
	List<BookModel> findAll();
	Long save(BookModel bookModel);
	BookModel findOne (Long id);
	void update(BookModel updatedBook);
}
