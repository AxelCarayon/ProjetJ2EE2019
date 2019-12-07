/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import modele.dao.DAOclient;
import modele.dao.DataSourceFactory;
import modele.entity.ClientEntity;


/**
 *
 * @author marie
 */
@WebServlet(name = "SessionActiveServlet", urlPatterns = {"/SessionActiveServlet"})
public class SessionActiveServlet extends HttpServlet {

    private DataSource dataSource;
    private DAOclient dao; 
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
            throws ServletException, IOException, SQLException {
        
            String isconected = "isconected";            
            String isadmin = "isadmin";
            String getUser = "getuser";
            
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String gsonData ="";
            HttpSession session = request.getSession();
            if (session.getAttribute("etat") != null){
                // Admin
                if (isadmin.equals(request.getParameter("action"))){
                    if (session.getAttribute("etat").equals("admin")){
                      gsonData = gson.toJson(true);
                    }else{
                        gsonData = gson.toJson(false);} 
                    }
                //lambda
                if (isconected.equals(request.getParameter("action"))){
                    gsonData = gson.toJson(true);
                }
                //getuser
                if (getUser.equals(request.getParameter("action"))){
                    dataSource = DataSourceFactory.getDataSource();
                    dao = new DAOclient(dataSource);
                    String code = session.getAttribute("id").toString();
                    ClientEntity client = dao.afficherClient(code);
                    gsonData = gson.toJson(client);
                }
            }
            else{gsonData = gson.toJson(false);} 
            envoiInfo(gsonData,response);
    }
    
    public void envoiInfo (String data, HttpServletResponse response) throws ServletException, IOException{
        try ( PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            out.println(data);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SessionActiveServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SessionActiveServlet.class.getName()).log(Level.SEVERE, null, ex);
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

}
