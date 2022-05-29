package com.hoangvoletuan.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import com.hoangvoletuan.dao.ILibraryDao;
import com.hoangvoletuan.model.BookModel;
import com.hoangvoletuan.service.ILibraryService;

public class LibraryService implements ILibraryService{
	
	@Inject
	private ILibraryDao libraryDao;
	@Override
	public List<BookModel> findAll() {
		// TODO Auto-generated method stub
		return libraryDao.findAll();
	}
	@Override
	public BookModel save(BookModel bookModel) {
		Long bookId = libraryDao.save(bookModel);
		return libraryDao.findOne(bookId);
	}
	@Override
	public BookModel update(BookModel updatedBook) {
		BookModel oldBook = libraryDao.findOne(updatedBook.getId());
		updatedBook.setCreatedBy(oldBook.getCreatedBy());
		updatedBook.setCreatedDate(oldBook.getCreatedDate());
		//updatedBook.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		libraryDao.update(updatedBook);
		return libraryDao.findOne(updatedBook.getId());
	}
	
}
