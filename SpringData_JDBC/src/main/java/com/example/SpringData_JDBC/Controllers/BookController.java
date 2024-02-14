package com.example.SpringData_JDBC.Controllers;

import com.example.SpringData_JDBC.Entities.Book;
import com.example.SpringData_JDBC.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/addBook")
    public Book addBook(@RequestBody  Book book){
        return bookService.addBook(book);
    }

    @GetMapping("/getBooks")
    public Iterable<Book>getBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/getBook/{id}")
    public Book getBook(@PathVariable Long id){
        return bookService.getBook(id);
    }

    @PutMapping("/update/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book){
        return bookService.updateBook(id,book);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook (@PathVariable Long id){
         bookService.deleteBook(id);
    }

}
