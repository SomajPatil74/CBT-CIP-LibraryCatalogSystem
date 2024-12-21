package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Book;
import dto.BookingDetails;

public class UserDao {
    public boolean getUserByRegno(Connection conn, String regNo) throws SQLException {
        String query = "select * from user where reg_num = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, regNo);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int getUserByRegno_(Connection conn, String regNo) throws SQLException {
        String query = "select * from user where reg_num = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, regNo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public void saveUser(Connection conn, String userName, String regNo) throws SQLException {
        String query = "insert into user (user_name, reg_num) values (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, userName);
            ps.setString(2, regNo);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("User added successfully.");
            } else {
                System.out.println("Failed to add user!!!");
            }
        }
    }

    public void getAllUsers(Connection conn) throws SQLException {
        String query = "select * from user";

        System.out.println("+------------+--------------------+---------------+");
        System.out.println("| Id         | User Name          | Reg No        |");
        System.out.println("+------------+--------------------+---------------+");

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("| %-10s | %-18s | %-13s | \n", rs.getInt(1), rs.getString(2), rs.getString(3));
                    System.out.println("+------------+--------------------+---------------+");
                }
            }
        }
    }

    public void updateBookQty(Connection conn, Book book) throws SQLException {
        String query = "update books set qty = ? where sr_no = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, book.getBookQty());
            ps.setInt(2, book.getSrNo());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("Failed to update book!!!");
            }
        }
    }

    public void saveBookingDetails(Connection conn, int userId, int bookId, int qty) throws SQLException {
        String query = "insert into booking_details(user_id, book_id, qty) values(?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);
            preparedStatement.setInt(3, qty);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Booking details Added Successfully!!");
            } else {
                System.out.println("Failed to add Booking details!!!");
            }
        }
    }

    public List<BookingDetails> getBookDetailsId(Connection conn, int userId) throws SQLException {
        String query = "select * from booking_details bd "
                + "inner join books b on b.id = bd.book_id "
                + "where bd.user_id = ?";

        List<BookingDetails> bookingDetails = new ArrayList<>();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BookingDetails bookingDetail = new BookingDetails();
                bookingDetail.setAuthorName(resultSet.getString("author_name"));
                bookingDetail.setBookId(resultSet.getInt("book_id"));
                bookingDetail.setBookName(resultSet.getString("name"));
                bookingDetail.setQty(resultSet.getInt("qty"));
                bookingDetail.setStdId(resultSet.getInt("userId"));
                bookingDetail.setSrNo(resultSet.getInt("sr_no"));
                bookingDetail.setId(resultSet.getInt("id"));

                bookingDetails.add(bookingDetail);
            }
        }
        return bookingDetails;
    }

    public void deleteBookingDetails(Connection conn, int id) throws SQLException {
        String query = "delete from booking_details where id = ? ";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}