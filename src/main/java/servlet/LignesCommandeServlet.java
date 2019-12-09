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
import modele.dao.DAOligne;
import modele.dao.DataSourceFactory;
import modele.entity.LigneCommandeEntity;

/**
 *
 * @author Axel
 */
@WebServlet(name = "LignesCommandeServlet", urlPatterns = {"/LignesCommandeServlet"})
public class LignesCommandeServlet extends HttpServlet {
    
    private DataSource dataSource;
    private DAOligne dao;

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
        dao = new DAOligne(dataSource);
        List<LigneCommandeEntity> data;
        int commande = Integer.parseInt(request.getParameter("commande"));

        if ( actionIs(request, "majQte")){
            int produit = Integer.parseInt(request.getParameter("produit"));
            int qte = Integer.parseInt(request.getParameter("quantite"));
            try{
                dao.modifierQuantiteLigne(commande, produit, qte);
            }catch(SQLException e){
                throw new SQLException(e);
            }
        }
        if ( actionIs(request, "trashLigne")){
            int produit = Integer.parseInt(request.getParameter("produit"));
            try{
                dao.supprimerLigne(commande, produit);
            }catch(SQLException e){
                throw new SQLException(e);
            }
        }
        
        try{
            data = dao.afficherCommande(commande);
        }catch(SQLException e){
            throw new SQLException(e);
        }
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String gsonData = gson.toJson(data);
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
            Logger.getLogger(LignesCommandeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LignesCommandeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
