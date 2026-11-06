/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package covercraft.model;

/**
 *
 * @author max2
 */
public class University {
    private String name;
    private String department;
    private String logoPath;
    public University(String name, String department, String logoPath){
        this.name= name;
        this.department=department;
        this.logoPath=logoPath;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department){
        this.department=department;
    }
     public String getLogoPath() {
        return logoPath;
    }
    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
    public boolean hasLogo(){
        return logoPath!=null && !logoPath.isEmpty();
    }
}
