package service;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.BookDao;
import dao.UserDao;
import dto.Book;
public class UserService {
    Scanner sc = new Scanner(System.in);

    public void searchBySrNo(Connection conn) throws SQLException {
        System.out.print("Enter Serial No of Book : ");
        int srNo = sc.nextInt();
        BookDao dao = new BookDao();
        Book book = dao.getBooksBySrno(conn, srNo);

        if (book != null) {
            System.out.println("----- Book Details -----");
            System.out.println("Sr No : " + book.getSrNo() + " Book Name: " + book.getBookName() + " Author Name: "
                    + book.getAuthorName());
        } else {
            System.out.println("No Book for Serial No " + srNo + " Found!!!");
        }
    }

    public void addUser(Connection conn) throws SQLException {
        System.out.print("Enter User Name : ");
        String userName = sc.nextLine();

        System.out.print("Enter Registration No : ");
        String regNo = sc.nextLine();
        UserDao dao = new UserDao();
        boolean isStdExist = dao.getUserByRegno(conn, regNo);

        if (isStdExist) {
            System.out.println("User details exist into our system.");
            return;
        }
        dao.saveUser(conn, userName, regNo);
    }

    public void getAllUsers(Connection conn) throws SQLException {
        UserDao dao = new UserDao();
        dao.getAllUsers(conn);
    }
}