package com.example.SpringData_JDBC.Services;

import com.example.SpringData_JDBC.Entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Book addBook(Book book) {
        String sql = "INSERT INTO book (id, title, author) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, book.getId(), book.getTitle(), book.getAuthor());
        return book;
    }

    public Book getBook(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> getBooks() {
        String sql = "SELECT * FROM book";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class));
    }

    public Book updateBook(Long id, Book book) {
        String sql = "UPDATE book SET title = ?, author = ? WHERE id = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), id);
        return book;
    }

    public void deleteBook(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
