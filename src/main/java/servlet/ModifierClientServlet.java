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
        
        String client=request.getSession().getAttribute("id").toString();
        String action = request.getParameter("action");
        String data = request.getParameter("data");
        ClientEntity clientEntity;
        
        switch (action){
            case "email": 
                try{
                    dao.modifierContact(client, data);
                }catch(Exception e){
                    throw new Exception(e);
                }
            break;
            case "adresse": 
                try{
                    dao.modifierAdresse(client, data);
                }catch(Exception e){
                    throw new Exception(e);
                }
            break;
            case "ville": 
                try{
                    dao.modifierVille(client, data);
                }catch(Exception e){
                    throw new Exception(e);
                }
            break;    
            case "region": 
                try{
                    dao.modifierRegion(client, data);
                }catch(Exception e){
                    throw new Exception(e);
                }
            break;  
            case "cp":
                try{
                    dao.modifierCodePostal(client, data);
                }catch(Exception e){
                    throw new Exception(e);
                }
            break;
            case "pays": 
                try{
                    dao.modifierPays(client, data);
                }catch(Exception e){
                    throw new Exception(e);
                }
            break;
            case "tel": 
                try{
                    dao.modifierTelephone(client, data);
                }catch(Exception e){
                    throw new Exception(e);
                }
                break;
            case "fax": 
                try{
                    dao.modifierFax(client, data);
                }catch(Exception e){
                    throw new Exception(e);
                }
            break;
            case "societe": 
                try{
                    dao.modifierSociete(client, data);
                }catch(Exception e){
                    throw new Exception(e);
                }
            break;
            case "fonction": 
                try{
                    dao.modifierFonction(client, data);
                }catch(Exception e){
                    throw new Exception(e);
                }
            break;    
        }
        try{
            clientEntity = dao.afficherClient(client);
        }catch(Exception e){
            throw new Exception(e);
        }                

        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
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
