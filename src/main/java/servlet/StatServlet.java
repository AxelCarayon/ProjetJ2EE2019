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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import modele.dao.DAOstats;
import modele.dao.DataSourceFactory;

/**
 *
 * @author marie
 */
@WebServlet(name = "statServlet", urlPatterns = {"/statServlet"})
public class StatServlet extends HttpServlet {

    private DataSource dataSource;
    private DAOstats dAOstats;
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
        dataSource = DataSourceFactory.getDataSource();
        dAOstats = new DAOstats(dataSource);
        Map<String,Double> ca =new HashMap();
        Double caClient = -1.;
        try{
            String dateD = request.getParameter("dateD");
            String dateF = request.getParameter("dateF");
            if(actionIs(request, "cat")){
                ca = dAOstats.CAtoutesLesCategories(dateD, dateF);
                
            }
            if(actionIs(request, "pays")){
                ca = dAOstats.CAtousLesPays(dateD, dateF);
            }
            if(actionIs(request, "client")){
                String client = request.getParameter("client");
                caClient = dAOstats.CAparClient(client, dateD, dateF);
            }
              
            
        }catch(SQLException e){
            throw new SQLException(e);
        }
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String gsonData;
            if (caClient != -1.){
                gsonData = gson.toJson(caClient);
            }else{
                gsonData = gson.toJson(ca);
            }
            out.println(gsonData);
        }
    }
    private boolean actionIs(HttpServletRequest request, String action) {
            return action.equals(request.getParameter("action"));
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
            Logger.getLogger(StatServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(StatServlet.class.getName()).log(Level.SEVERE, null, ex);
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
