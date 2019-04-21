package com.test.security;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/hello")
@ServletSecurity(@HttpConstraint(rolesAllowed = {"admin", "member"}, transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL))
public class Hello extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("Hello, 2019!");
    }
}
