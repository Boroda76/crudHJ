package controller;

import exceptions.UserException;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/delete")
public class UserServletDelete extends HttpServlet {
    private UserService service;

    @Override
    public void init() {
        this.service = UserServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String sid = req.getParameter("id");
        if (sid != null) {
            try {
                Long id = Long.parseLong(sid);
                service.delete(id);
            } catch (UserException e) {
                req.setAttribute("message", e.getMessage());
                RequestDispatcher dispatcher = req.getRequestDispatcher("views/error.jsp");
                dispatcher.forward(req, res);
            }
        } else {
            req.setAttribute("message", "Can't delete user without ID");
            RequestDispatcher dispatcher = req.getRequestDispatcher("views/error.jsp");
            dispatcher.forward(req, res);
        }

        res.sendRedirect("/admin");

    }
}
