import login.LoginService;

import java.sql.SQLException;
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("Hello and welcome!");
        System.out.println("Thanks for Choosing Library Catalog System!!!");
        System.out.println("Please do login first for accessing menu.");
        LoginService loginService = new LoginService();
        loginService.doLogin();
    }
}