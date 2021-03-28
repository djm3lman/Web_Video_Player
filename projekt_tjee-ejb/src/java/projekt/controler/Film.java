/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.controler;

import java.util.Date;
import org.bson.types.ObjectId;

/**
 *
 * @author student
 */
public class Film {
    
    private ObjectId id;
    private String name;
    private String extension;
    private Date date;
    private String data;
    private boolean deleteFlag;
    
    public Film(){
    }
    
    public Film(String name, String extension, String data){
        this.name = name;
        this.extension= extension;
        this.date= new Date();
        this.data=data;
        this.deleteFlag= false;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getExtension() {
        return extension;
    }

    public String getData() {
        return data;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
