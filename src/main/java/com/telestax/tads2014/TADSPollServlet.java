
package com.telestax.tads2014;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * A simple servlet taking advantage of features added in 3.0.
 * </p>
 * 
 * <p>
 * The servlet is registered and mapped to /TADSPoll using the {@linkplain WebServlet
 * @HttpServlet}. The {@link TADSPollService} is injected by CDI.
 * </p>
 * 
 * @author Jean Deruelle
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/TADSPoll")
public class TADSPollServlet extends HttpServlet {

    static String PAGE_HEADER = "<html><head><title>TADS Poll Service</title></head><body>";

    static String PAGE_FOOTER = "</body></html>";

    @Inject
    TADSPollService tadsPollService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String fromNumber = req.getParameter("from");
	String toNumber = req.getParameter("to");
	String drink = req.getParameter("drink");

	String birthDate = tadsPollService.getBirthDate(toNumber);
	String location = tadsPollService.getLocation(fromNumber);
	String favDrink = tadsPollService.getDrink(drink);

	System.out.println("from " + fromNumber + ", to " + toNumber + ", drink " + drink);
	System.out.println("birthDate " + birthDate + ", location " + location + ", favDrink " + favDrink);

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println(PAGE_HEADER);
        writer.println("<h1>Poll Results</h1>");
	writer.println("<p>Birth Date" + birthDate + "</p>");
	writer.println("<p>Location " + location + "</p>");
	writer.println("<p>Favorite Drink " + favDrink + "</p>");
        writer.println(PAGE_FOOTER);
        writer.close();
    }

}