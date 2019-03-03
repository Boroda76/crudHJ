package controller.filter;

import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(urlPatterns = {"/admin/*", "/user", "/", "/login"})
public class UserFilterRole implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest) request;
        HttpServletResponse res=(HttpServletResponse) response;
        try {
            User user = (User) req.getSession().getAttribute("user");
            String role = user.getRole();
            if (role.equals("admin")) chain.doFilter(request, response);
            else {
                String[] ref=req.getHeaders("Referer").nextElement().split(":8080");
                if (ref.length==1|!ref[1].equals("/user?id=" + user.getId())) {
                    RequestDispatcher dispatcher=req.getRequestDispatcher("/views/user.jsp");
                    dispatcher.forward(req, res);
                } else {
                    chain.doFilter(request, response);
                }
            }
        }catch (NullPointerException npe){
            req.setAttribute("message", "You should login first!");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/");
            dispatcher.forward(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
