package com.hoangvoletuan.dao.impl;

import java.util.List;

import com.hoangvoletuan.dao.ILibraryDao;
import com.hoangvoletuan.mapper.BookMapper;
import com.hoangvoletuan.model.BookModel;

public class LibraryDao extends AbstractDao<BookModel> implements ILibraryDao{	
	@Override
	public List<BookModel> findAll() {
		String sql = "SELECT * FROM new_library.book";
		return query(sql, new BookMapper());
	}

	@Override
	public Long save(BookModel bookModel) {
		String sql = "INSERT INTO book (title, author) VALUE(?, ?)";
		return insert(sql, bookModel.getTitle(), bookModel.getAuthor());
	}

	@Override
	public BookModel findOne(Long id) {
		String sql = "SELECT * FROM book WHERE id = ?";
		List<BookModel> books = query(sql, new BookMapper(), id);
		return books.isEmpty() ? null : books.get(0);
	}

	@Override
	public void update(BookModel updatedBook) {
		String sql = "UPDATE book SET title = ?, author = ?, createddate = ?, createdby = ?, modifieddate = ?, modifiedby = ? WHERE id = ?";
		update(sql, updatedBook.getTitle(), updatedBook.getAuthor(),
				updatedBook.getCreatedDate(), updatedBook.getCreatedBy(),
				updatedBook.getModifiedDate(), updatedBook.getModifiedBy(), updatedBook.getId());
	}

}
