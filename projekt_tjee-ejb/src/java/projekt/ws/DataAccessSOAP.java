/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.ws;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import projekt.controler.Controler;

/**
 *
 * @author student
 */
@WebService(serviceName = "DataAccessSOAP")
@Stateless()
public class DataAccessSOAP {

    @EJB
    private Controler controler;
    
    @WebMethod(operationName = "showFilmList")
    public String showFilmList(){
        return controler.getFilmListHTML();
    }
    
    @WebMethod(operationName = "addFilm")
    public boolean addFilm(@WebParam(name = "name") String name, @WebParam(name = "data") byte[] data){
        return controler.addFilm(name, data);
    }
    
    @WebMethod(operationName = "deleteFilm")
    public boolean deleteFilm(@WebParam(name = "filmId") String filmId){
        return controler.deleteFilm(filmId);
    }
    
    @WebMethod(operationName = "getFilmData")
    public String getFilmData(@WebParam(name = "filmId") String filmId){
        return controler.getFilmData(filmId);
    }
    
    @WebMethod(operationName = "getFilmName")
    public String getFilmName(@WebParam(name = "filmId") String filmId){
        return controler.getFilmName(filmId);
    }
}
