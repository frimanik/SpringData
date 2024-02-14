package com.example.SpringData_JDBC.Services;

import com.example.SpringData_JDBC.Entities.Book;
import com.example.SpringData_JDBC.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    public Book getBook(Long id){
        return bookRepository.findById(id).get();
    }

    public Iterable<Book> getBooks(){
        return bookRepository.findAll();
    }

    public Book updateBook(Long id,Book book){
        return bookRepository.save(book);
    }

    public void deleteBook(Long id){
         bookRepository.deleteById(id);
    }
}
