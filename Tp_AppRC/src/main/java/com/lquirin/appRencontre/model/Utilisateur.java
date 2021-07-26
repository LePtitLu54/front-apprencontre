package com.lquirin.appRencontre.model;


import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "utilisateur")
@EntityListeners(AuditingEntityListener.class)
public class Utilisateur {

    private String pseudo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String password;
    private int age;
    private String email;
    private String pays;
    private String lang;
    private String jeux;

    @ManyToMany
    @JoinTable(name = "role_utilisateur",
            joinColumns = @JoinColumn(name = "id_utilisateur" ),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    private List<Role> listeRoles = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "utilisateur_jeux",
            joinColumns = @JoinColumn(name = "id_utilisateur"),
            inverseJoinColumns = @JoinColumn(name = "id_jeux"))
    private Set<TitreJeux> listeTitreJeux = new HashSet<>();

    @ManyToOne
    private Status status;
}


