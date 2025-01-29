/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cp2project;

/**
 *
 * @author ahmet
 */
public class RegularUser extends User {
        //Seperates users as a their role. Inheritance using in here.
    
    public RegularUser(int id, String name, String surname, String birth_date, int gender, String mail, String password) {
        super(id, name, surname, birth_date, gender, mail, password, "regular");
    }
    @Override
    public String ReturnValues(){
    return "Regular user";
    }
    
}
