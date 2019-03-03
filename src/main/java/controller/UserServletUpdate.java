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

@WebServlet(urlPatterns = "/admin/update")
public class UserServletUpdate extends HttpServlet {
    private UserService service;

    @Override
    public void init() {
        this.service = UserServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/create.jsp");
        String id = req.getParameter("id");
        try {
            if (id != null) {
                req.setAttribute("userEdit", service.getById(Long.parseLong(id)));
            }
        } catch (UserException e) {
            req.setAttribute("message", e.getMessage());
            dispatcher = req.getRequestDispatcher("/views/error.jsp");
        }
        dispatcher.forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/create.jsp");
        String id = req.getParameter("id");
        if (id != null) {
            try {
                service.updateUser(new User(Long.parseLong(id), req.getParameter("login"),
                        req.getParameter("email"),
                        req.getParameter("password"),
                        Boolean.parseBoolean(req.getParameter("sex")),
                        Integer.parseInt(req.getParameter("age")),
                        Double.parseDouble(req.getParameter("weight")),
                        Double.parseDouble(req.getParameter("height")),
                        req.getParameter("role")));
//            res.getWriter().println("<body>User updated<br><a href=\"./\">Back to users list</a>");
                res.sendRedirect("/admin");
            } catch (UserException e){
                req.setAttribute("message", e.getMessage());
                dispatcher.forward(req, res);
            }
        } else {
            try {
                service.createUser(new User(0L, req.getParameter("login"),
                        req.getParameter("email"),
                        req.getParameter("password"),
                        Boolean.parseBoolean(req.getParameter("sex")),
                        Integer.parseInt(req.getParameter("age")),
                        Double.parseDouble(req.getParameter("weight")),
                        Double.parseDouble(req.getParameter("height")),
                        req.getParameter("role")));
                res.sendRedirect("/admin");
            } catch (UserException e){
                req.setAttribute("message", e.getMessage());
                dispatcher.forward(req, res);
            }
//            res.getWriter().println("<body>User created<br><a href=\"./\">Back to users list</a>");

        }
    }
}
