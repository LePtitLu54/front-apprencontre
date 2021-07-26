package com.lquirin.apprc_android.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Utilisateur implements Serializable {

    private String pseudo;
    private int id;
    private String password;
    private int age;
    private String email;
    private String pays;
    private String lang;
    private String jeux;

    private List<Role> listeRole = new ArrayList<>();

    private List<Utilisateur> ListeUtilisateur = new ArrayList<>();

    public Utilisateur(int id, String pseudo) {
        this.id = id;
        this.pseudo = pseudo;
    }

    public static Utilisateur fromJson(JSONObject jsonUtilisateur) throws JSONException {
        System.out.println(jsonUtilisateur);

        Utilisateur utilisateur = new Utilisateur(
                jsonUtilisateur.getInt("id"),
                jsonUtilisateur.getString("pseudo"));


        JSONArray jsonListeRole = jsonUtilisateur.getJSONArray("listeRole");
        for(int j = 0; j < jsonListeRole.length(); j++) {
            JSONObject jsonRole = jsonListeRole.getJSONObject(j);
            Role role = Role.fromJson(jsonRole);
            utilisateur.getListeRole().add(role);
        }


        return utilisateur;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Role> getListeRole() { return listeRole; }
    public void setListeRole(List<Role> listeRole) { this.listeRole = listeRole; }

    public List<Utilisateur> getListeUtilisateur() { return ListeUtilisateur; }
    public void setListeUtilisateur(List<Utilisateur> listeUtilisateur) { ListeUtilisateur = listeUtilisateur; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }

    public String getLang() { return lang; }
    public void setLang(String lang) { this.lang = lang; }

    public String getJeux() { return jeux; }
    public void setJeux(String jeux) { this.jeux = jeux; }
}
