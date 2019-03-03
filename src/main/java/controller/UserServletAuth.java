package controller;

import exceptions.UserException;
import model.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.MD5Hasher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet({"/login", "/"})
public class UserServletAuth extends HttpServlet {
    private UserService service;

    @Override
    public void init() {
        this.service = UserServiceImpl.getInstance();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        RequestDispatcher dispatcher=req.getRequestDispatcher("/views/login.jsp");
        dispatcher.forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RequestDispatcher dispatcher=null;
        User user=null;
        String login=req.getParameter("login");
        String password=req.getParameter("password");
        HttpSession session=req.getSession(false);
        try{
            user=service.getByLogin(login);
        } catch (UserException e){
            dispatcher=req.getRequestDispatcher("/views/login.jsp");
            req.setAttribute("message", e.getMessage());
            dispatcher.forward(req, res);
        }
        if(!password.equals(user.getPassword())){
            dispatcher=req.getRequestDispatcher("/views/login.jsp");
            req.setAttribute("message", "Wrong password! Be more careful...");
            dispatcher.forward(req, res);
        } else {
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(100);


            String[] ref=req.getHeaders("Referer").nextElement().split(":8080");
            if (ref.length==1|ref[1].equals("/")) {
                res.sendRedirect("/admin ");
            } else {
                res.sendRedirect(ref[1]);
            }
        }
    }
}
