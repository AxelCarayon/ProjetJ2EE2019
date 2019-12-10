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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.DAOcategorie;
import javax.sql.DataSource;
import modele.dao.DataSourceFactory;
import modele.entity.CategorieEntity;

/**
 *
 * @author marie
 */
@WebServlet(name = "CategorieServlet", urlPatterns = {"/CategorieServlet"})
public class CategorieServlet extends HttpServlet {

    private DataSource dataSource;
    private DAOcategorie dao;
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
        dao = new DAOcategorie(dataSource);
        List<CategorieEntity> cat;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String gsonData; 
        if(actionIs(request, "libelle")){
            try{
                int code = Integer.parseInt(request.getParameter("code"));
                String libelle=dao.afficherLibelle(code);
                envoyerResultat(response,gson.toJson(libelle));
            }
            catch(SQLException e){
                throw new SQLException(e);
            }
        }else{
            try{
                cat = dao.toutesLesCategories();
                envoyerResultat(response,gson.toJson(cat));
            }
            catch(SQLException e){
                throw new SQLException(e);
            }
        }
        
    }
    private boolean actionIs(HttpServletRequest request, String action) {
        return action.equals(request.getParameter("action"));
    }
    private void envoyerResultat(HttpServletResponse response, String gsonData) throws IOException{
        try ( PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            out.println(gsonData);
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
            Logger.getLogger(CategorieServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CategorieServlet.class.getName()).log(Level.SEVERE, null, ex);
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
