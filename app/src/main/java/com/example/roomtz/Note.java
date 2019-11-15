package com.example.roomtz;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private Boolean bool;

  //  private String description;

  //  private int priority;

    public Note(String title, Boolean bool/*, String description, int priority*/) {
        this.title = title;
        this.bool = bool;

       // this.description = description;
        //this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public Boolean getBool() {
        return bool;
    }



  /*  public String getDescription() {
        return description;
    }*/

    /*public int getPriority() {
        return priority;
    }*/
}
