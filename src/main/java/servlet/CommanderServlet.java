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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import modele.dao.DAOclient;
import modele.dao.DAOcommande;
import modele.dao.DAOligne;
import modele.dao.DataSourceFactory;
import modele.entity.ClientEntity;

/**
 *
 * @author marie
 */
@WebServlet(name = "CommanderServlet", urlPatterns = {"/CommanderServlet"})
public class CommanderServlet extends HttpServlet {

    private DAOcommande daoC;
    private DAOligne daoL;
    private DAOclient dAOclient;
    private DataSource dataSource;

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
            throws ServletException, IOException, SQLException, Exception {
        dataSource = DataSourceFactory.getDataSource();
        daoC = new DAOcommande(dataSource);
        daoL = new DAOligne(dataSource);
        dAOclient = new DAOclient(dataSource);
        
        if(actionIs(request, "commande")){
            String code_client= request.getSession().getAttribute("id").toString();
            Date date = new Date();
            Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String s = formatter.format(date);
            ClientEntity client;
            int code;
            
            try{
                client = dAOclient.afficherClient(code_client);
                code = daoC.ajouterCommande(code_client, s, s, 0, client.getContact(), client.getAdresse(), client.getVille(), client.getRegion(), client.getCode_postal(), client.getPays(), 0);

            }catch(SQLException e){
                throw new SQLException(e);
            }
            try (PrintWriter out = response.getWriter()) {
                    response.setContentType("application/json;charset=UTF-8");
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String gsonData = gson.toJson(code);
                    out.println(gsonData);
                }
        }else{
            int commande = Integer.parseInt(request.getParameter("code"));
            int prod = Integer.parseInt(request.getParameter("prod"));
            int qte = Integer.parseInt(request.getParameter("qte"));
            try{
                daoL.ajouterLigne(commande, prod, qte);
            }catch(SQLException e){
                throw new SQLException(e);
            }
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
            Logger.getLogger(CommanderServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CommanderServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CommanderServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CommanderServlet.class.getName()).log(Level.SEVERE, null, ex);
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
