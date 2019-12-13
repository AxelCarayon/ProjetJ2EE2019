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
import javax.sql.DataSource;
import modele.dao.DAOproduit;
import modele.dao.DataSourceFactory;
import modele.entity.ProduitEntity;

/**
 *
 * @author Axel
 */
@WebServlet(name = "ProduitServlet", urlPatterns = {"/ProduitServlet"})
public class ProduitServlet extends HttpServlet {

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
        dataSource = DataSourceFactory.getDataSource();
        dao = new DAOproduit(dataSource);
        int code = Integer.parseInt(request.getParameter("reference"));

        ProduitEntity produit;
        if (actionIs(request, "update")) {
            if (request.getSession().getAttribute("etat") == "admin") {
                try {
                    String data = request.getParameter("data");
                    String champ = request.getParameter("champ");
                    switch (champ) {
                        case "nom":
                            dao.modifierNomProduit(code, data);
                            break;
                        case "fournisseur":
                            dao.modifierFournisseur(code, Integer.parseInt(data));
                            break;
                        case "categorie":
                            dao.modifierCategorie(code, Integer.parseInt(data));
                        break;
                        case "quantite_par_unite":
                            dao.modifierQuantiteParUnite(code, data);
                            break;
                        case "prix_unitaire":
                            dao.modifierPrixUnitaire(code, Double.parseDouble(data));
                            break;
                        case "unites_commandes":
                            dao.modifierUnitesCommandees(code, Integer.parseInt(data));
                            break;
                        case "niveau_reaprovis":
                            dao.remettreEnStock(code, Integer.parseInt(data));
                            break;
                    }
                }catch(SQLException e){
                    throw new SQLException(e);
                }
            }
        }
        try {
            produit = dao.afficherProduit(code);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        try ( PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String gsonData = gson.toJson(produit);
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
            Logger.getLogger(ProduitServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProduitServlet.class.getName()).log(Level.SEVERE, null, ex);
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
