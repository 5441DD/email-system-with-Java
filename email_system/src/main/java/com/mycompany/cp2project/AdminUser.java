/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cp2project;

/**
 *
 * @author ahmet
 */
public class AdminUser extends User {
    //Seperates users as a their role

    public AdminUser(int id, String name, String surname, String birth_date, int gender, String mail, String password) {
        super(id, name, surname, birth_date, 0, mail, password, "admin");
    }
    @Override
    public String ReturnValues(){
        return "Admin user";
    }
}
