package org.example;

import java.util.Date;

public class Project {
    private int id;
    private String name;
    private String start_date;
    private String end_date;
    private String description;
    private int organization_id;
    private String organization_name;
    private int manager_id;
    private String manager_name;
    private int habitat_id;
    private String habitat_name;

    public Project(int id, String name, String start_date,String end_date, String description, int manager_id, int organization_id, int habitat_id) {
        this.id = id;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.organization_id = organization_id;
        this.manager_id = manager_id;
        this.habitat_id = habitat_id;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getStart_date() {return start_date;}

    public void setStart_date(String start_date) {this.start_date = start_date;}

    public String getEnd_date() {return end_date;}

    public void setEnd_date(String end_date) {this.end_date = end_date;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public int getOrganization_id() {return organization_id;}

    public void setOrganization_id(int organization_id) {this.organization_id = organization_id;}

    public int getManager_id() {return manager_id;}

    public void setManager_id(int manager_id) {this.manager_id = manager_id;}

    public int getHabitat_id() {return habitat_id;}

    public void setHabitat_id(int habitat_id) {this.habitat_id = habitat_id;}

    public String getHabitat_name() {return habitat_name;}

    public void setHabitat_name(String habitat_name) {this.habitat_name = habitat_name;}

    public String getManager_name() {return manager_name;}

    public void setManager_name(String manager_name) {this.manager_name = manager_name;}

    public String getOrganization_name() {return organization_name;}

    public void setOrganization_name(String organization_name) {this.organization_name = organization_name;}
}
