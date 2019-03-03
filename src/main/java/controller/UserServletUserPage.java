package controller;

import exceptions.UserException;
import model.User;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/user"})
public class UserServletUserPage extends HttpServlet {
    private UserService service;

    @Override
    public void init() {
        this.service = UserServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String id = req.getParameter("id");
        RequestDispatcher dispatcher = req.getRequestDispatcher("views/user.jsp");
        User user = null;
        if(id!=null) {
            try {
                user = service.getById(Long.parseLong(id));
            } catch (UserException e) {
                req.setAttribute("message", e.getMessage());
                dispatcher = req.getRequestDispatcher("views/error.jsp");
            }
        }
        req.setAttribute("user", user);
        dispatcher.include(req, res);
    }
}
