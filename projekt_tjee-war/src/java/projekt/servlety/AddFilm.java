/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.servlety;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.ws.WebServiceRef;
import ws_clients.DataAccessSOAP_Service;

/**
 *
 * @author student
 */
@MultipartConfig(fileSizeThreshold=1024*1024*10, // 10 MB
                 maxFileSize=1024*1024*20,      // 20MB
                 maxRequestSize=1024*1024*50)   // 50MB
@WebServlet(name = "AddFilm", urlPatterns = {"/AddFilm"})
public class AddFilm extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/DataAccessSOAP/DataAccessSOAP.wsdl")
    private DataAccessSOAP_Service service;

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
        Part part = request.getPart("file");
        String fileName = part.getSubmittedFileName();
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        if(".mp4".equals(extension)){
            byte[] bytes = new byte[(int)part.getSize()];
            part.getInputStream().read(bytes);
            addFilm(fileName, bytes);
            getServletContext().getRequestDispatcher("/homePage.jsp").forward(
                    request, response);
        }
        else {
            getServletContext().getRequestDispatcher("/error.jsp").forward(
                    request, response);
        }
        
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

    private boolean addFilm(java.lang.String name, byte[] data) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws_clients.DataAccessSOAP port = service.getDataAccessSOAPPort();
        return port.addFilm(name, data);
    }

}
