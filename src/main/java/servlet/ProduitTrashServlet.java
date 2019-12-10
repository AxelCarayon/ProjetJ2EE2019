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
import javax.sql.DataSource;
import modele.dao.DAOproduit;
import modele.dao.DataSourceFactory;
import modele.entity.LigneCommandeEntity;

/**
 *
 * @author marie
 */
@WebServlet(name = "ProduitTrashServlet", urlPatterns = {"/ProduitTrashServlet"})
public class ProduitTrashServlet extends HttpServlet {

    private DataSource dataSource;
    private DAOproduit dao;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("application/json;charset=UTF-8");
        dataSource = DataSourceFactory.getDataSource();
        dao = new DAOproduit(dataSource);
        
        try{
            if(request.getSession().getAttribute("etat")== "admin"){
                if(actionIs(request, "add")){
                    String nom = request.getParameter("nom");
                    int fournisseur= Integer.parseInt(request.getParameter("fournisseur"));
                    int categorie= Integer.parseInt(request.getParameter("categorie"));
                    String quantite_par_unite= request.getParameter("quantite_par_unite");
                    Double prix_unitaire= Double.parseDouble(request.getParameter("prix_unitaire"));
                    int unite_en_stock= Integer.parseInt(request.getParameter("unite_en_stock"));     
                    dao.ajouterProduit(nom, fournisseur, categorie, quantite_par_unite, prix_unitaire, unite_en_stock, 0, 0, false);
                }else{
                    int code = Integer.parseInt(request.getParameter("code"));
                    dao.supprimerProduit(code);
                }
            }
        }catch(SQLException e){
            throw new SQLException(e);
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
            Logger.getLogger(ProduitTrashServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProduitTrashServlet.class.getName()).log(Level.SEVERE, null, ex);
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
