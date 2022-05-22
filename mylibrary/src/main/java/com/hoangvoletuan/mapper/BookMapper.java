package com.hoangvoletuan.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hoangvoletuan.model.BookModel;

public class BookMapper implements RowMapper<BookModel>{

	@Override
	public BookModel mapRow(ResultSet resultSet) {
		try {
			BookModel book = new BookModel();
			book.setId(resultSet.getLong("id"));
			book.setTitle(resultSet.getString("title"));
			book.setAuthor(resultSet.getString("author"));
			return book;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
