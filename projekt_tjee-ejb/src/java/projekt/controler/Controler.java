/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.controler;


import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.Base64;
import javax.ejb.Stateless;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

/**
 *
 * @author student
 */
@Stateless
public class Controler{

    CodecRegistry kodek = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));;
    private final String localConnection = "mongodb://localhost:27017/";
    private final MongoClient  mongoClient = MongoClients.create(localConnection);
    private final MongoCollection<Film> collection = mongoClient.getDatabase("studenci_2020").getCollection("projekt-tjee", Film.class).withCodecRegistry(kodek);
    

   private ArrayList<Film> getFilmList(){
        ArrayList<Film> filmList = new ArrayList();
        FindIterable<Film> results = collection.find(eq("deleteFlag", false));
        for(Film temp : results){
            filmList.add(temp);
        }
        return filmList;
    }
    
    
    public String getFilmListHTML(){
        ArrayList<Film> filmList = getFilmList();
        String row="";
        
        for(Film film : filmList){
            row = row.concat("<tr>");
            row = row.concat("<td>" + film.getName() + "</td>");
            row = row.concat("<td>" + film.getDate() + "</td>");
            row = row.concat("<td text-center>" + 
                "<div class=\"grid margin\">"
                   +"<div class=\"s-12 l-3\">"
                            + "<form class=\"customform\" action=\"DeleteFilm\" method=\"post\">"
                                + "<button type=\"submit\"> Delete </button>"
                                + "<input type=\"hidden\" value=" + film.getId() +" name=\"id\"/>"
                            + "</form></div>" 
                   + "<div class=\"s-12 l-3 \">"
                            + "<form class=\"customform\" action=\"PlayFilm\" method=\"post\">"
                                + "<button type=\"submit\"> Play </button>"
                                + "<input type=\"hidden\" value=" + film.getId() +" name=\"id\"/>"
                            + "</form></div>"
                   + "<div class=\"s-12 l-3\">"
                            + "<form class=\"customform\" action=\"DownloadFilm\" method=\"get\">"
                                + "<button type=\"submit\"> Download </button>"
                                + "<input type=\"hidden\" value=" + film.getId() +" name=\"id\"/>"
                            + "</form></div>"
                + "</div>");
        }
        return row;
    }
    
    public boolean addFilm(String name, byte[] data){
        String dataStr = Base64.getEncoder().encodeToString(data);
        String extension = name.substring(name.lastIndexOf('.'));
        Film film = new Film(name, extension, dataStr);
        collection.insertOne(film);
        return true;
    }
    
    public boolean deleteFilm(String filmId){
        boolean delete;
        collection.updateOne(eq("_id", new ObjectId(filmId)), Updates.combine(Updates.set("deleteFlag", true)));
        collection.deleteOne(eq("_id", new ObjectId(filmId)));
        delete = true;
        return delete;
    }
    
    public String getFilmData(String filmId){
        Film film = collection.find(eq("_id", new ObjectId(filmId))).first();
        if(film == null || film.getData().isEmpty())
            return null;
        else{
            return film.getData();
        }
    }
    
    public String getFilmName(String filmId){
        Film film = collection.find(eq("_id", new ObjectId(filmId))).first();
        if(film == null || film.getData().isEmpty())
            return null;
        else{
            return film.getName();
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
