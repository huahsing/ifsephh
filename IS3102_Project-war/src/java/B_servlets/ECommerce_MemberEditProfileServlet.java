/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B_servlets;

import HelperClasses.Member;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class ECommerce_MemberEditProfileServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            
            String email = request.getParameter("email");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            Integer securityQn = Integer.parseInt(request.getParameter("securityQuestion"));
            String securityAns = request.getParameter("securityAnswer");
            Integer age = Integer.parseInt(request.getParameter("age"));
            Integer income = Integer.parseInt(request.getParameter("income"));
            String password = request.getParameter("password");

            updateMemberRESTful(email, name, phone, address, securityQn, securityAns, age, income, password);
            
            session.setAttribute("memberEmail", email);
            response.sendRedirect("ECommerce_GetMember");
        } catch (Exception ex) {
            out.println("\n\n " + ex.getMessage());
        }
    }
    
    public void updateMemberRESTful(String email, String name, String phone, String address, Integer securityQn, String securityAns, Integer age, Integer income, String password) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client
                .target("http://localhost:8080/IS3102_WebService-Student/webresources/entity.memberentity")
                .path("setMember")
                .queryParam("email", email)
                .queryParam("name", name)
                .queryParam("phone", phone)
                .queryParam("address", address)
                .queryParam("securityQn", securityQn)
                .queryParam("securityAns", securityAns)
                .queryParam("age", age)
                .queryParam("income", income)
                .queryParam("password", password);
        Invocation.Builder invocationBuilder = target.request();
        
        Response response = invocationBuilder.put(Entity.entity("", "application/json"));
        System.out.println("status: " + response.getStatus());
        
        if(response.getStatus() == Response.Status.OK.getStatusCode()) {
            System.out.println("success");
        } else {
            System.out.println("failed");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
