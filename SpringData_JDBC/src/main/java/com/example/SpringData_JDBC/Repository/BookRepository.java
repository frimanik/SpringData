package com.example.SpringData_JDBC.Repository;

import com.example.SpringData_JDBC.Entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book,Long> {
}
