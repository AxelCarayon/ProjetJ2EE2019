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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import modele.dao.DAOclient;
import modele.dao.DataSourceFactory;
import modele.entity.ClientEntity;

/**
 *
 * @author marie
 */
@WebServlet(name = "ModifierClientServlet", urlPatterns = {"/ModifierClientServlet"})
public class ModifierClientServlet extends HttpServlet {
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
            throws ServletException, IOException, Exception {
          
        dataSource = DataSourceFactory.getDataSource();
        dao = new DAOclient(dataSource);
        
        String client="";
        try{
            client = request.getSession().getAttribute("id").toString();
            if (client == ""){throw new Exception() ;}
        }catch(Exception e){
            throw new Exception("Client non connecté.");
        }
        
        String action = request.getParameter("action");
        String data = request.getParameter("data");
        
        switch (action){
            case "email": dao.modifierContact(client, data);
                break;
            case "adresse": dao.modifierAdresse(client, data);
                break;
            case "ville": dao.modifierVille(client, data);
                break;    
            case "region": dao.modifierRegion(client, data);
                break;  
            case "cp":dao.modifierCodePostal(client, data);
                break;
            case "pays": dao.modifierPays(client, data);
                break;
            case "tel": dao.modifierTelephone(client, data);
                break;
            case "fax": dao.modifierFax(client, data);
                break;
            case "societe": dao.modifierSociete(client, data);
                break;
            case "fonction": dao.modifierFonction(client, data);
                break;    
        }
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            ClientEntity clientEntity = dao.afficherClient(client);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String gsonData = gson.toJson(clientEntity);
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
        } catch (Exception ex) {
            Logger.getLogger(ModifierClientServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {
            Logger.getLogger(ModifierClientServlet.class.getName()).log(Level.SEVERE, null, ex);
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
