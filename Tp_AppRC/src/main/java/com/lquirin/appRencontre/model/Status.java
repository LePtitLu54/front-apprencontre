package com.lquirin.appRencontre.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "status")
@EntityListeners(AuditingEntityListener.class)
public class Status {

    @ManyToMany
    @JoinTable(
            name = "utilisateur_status",
            joinColumns = @JoinColumn(name = "id_utilisateur"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    Set<StyleDeJeux> listeStyleDeJeux;

    @OneToMany(mappedBy="status")
    Set<Utilisateur> listeUtilisateur;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String denomination;

}