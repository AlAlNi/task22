package com.server.servlet;

import com.server.dao.UserDao;
import com.server.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/edituser")
public class EditUserServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = (UserDao) getServletContext().getAttribute("userDao");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        if (userId == null){
            throw new ServletException("Missing parameter 'id'");
        }
        User user = userDao.getUserById(Integer.valueOf(userId));
        if (user == null){
            resp.setStatus(404);
            req.getRequestDispatcher("/notfound.jsp").forward(req,resp);
            return;
        }
        req.setAttribute("user", user);
        req.setAttribute("PageTitle", "Edit User");
        req.setAttribute("PageBody", "editUserForm.jsp");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        userDao.updateUserById(new User(Integer.valueOf(id), name, Integer.valueOf(age), email, Long.valueOf(phone)));
        resp.sendRedirect(req.getContextPath() + "/allusers");
    }
}
