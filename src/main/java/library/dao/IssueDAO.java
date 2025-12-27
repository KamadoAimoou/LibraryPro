package library.dao;

import library.model.IssuedBook;

import java.util.List;

public interface IssueDAO {
    void issueBook(IssuedBook issuedBook);

    List<IssuedBook> findActive();

    void markReturned(int issueId);
}

