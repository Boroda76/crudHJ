package controller.filter;

import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("all")
@WebFilter("/user")
public class UserFilterUserPage implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            User user = (User) req.getSession().getAttribute("user");
            String role = user.getRole();
            if ("admin".equals(role)) chain.doFilter(request, response);
            else {
                if (("/user?id=" + user.getId()).equals(req.getHeaders("Referer").nextElement().split(":8080")[1])) {
                    chain.doFilter(request, response);
                } else {
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You have no rights to see this page!");
                }
            }
        } catch (NullPointerException npe) {
            req.setAttribute("message", "You should login first!");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/");
            dispatcher.forward(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
