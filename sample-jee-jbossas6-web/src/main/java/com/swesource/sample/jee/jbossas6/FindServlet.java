package com.swesource.sample.jee.jbossas6;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 */
//@WebServlet(name = "findServlet", urlPatterns = "/")
@WebServlet(value = "/findServlet")
public class FindServlet extends HttpServlet {

    private DataRemote dataBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        execute(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        execute(request, response);
    }

    private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(dataBean == null) {
            System.out.println("findServlet: Generating new ctx!");
            try {
                Context context = new InitialContext();
                Object o = context.lookup("sample-jee-jbossas6/DataBean/remote");
                dataBean = (DataRemote)o;
                Data d = new Data();
                d.setData("tjoho");
                dataBean.store(d);
            }
            catch(NamingException e) {
                e.printStackTrace();
            }
        }

        Data data = dataBean.find(1L);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("Data: " + data.getData() + "<br/>");
        out.println("</body></html>");
        out.flush();
        out.close();
    }
}
