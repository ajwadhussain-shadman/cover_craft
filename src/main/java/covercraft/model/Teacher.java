/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package covercraft.model;

/**
 *
 * @author max2
 */
public class Teacher {
    private String name;
    private String designation;
    private String university;
    public Teacher(String name, String designation,String university) {
        this.name = name;
        this.designation = designation;
        this.university=university;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public String getUniversity(){
        return university;
    }
    public void setUniversity(String university){
        this.university=university;
               
    }
}