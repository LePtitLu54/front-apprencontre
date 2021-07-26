package com.lquirin.appRencontre.model;


import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Data
@Entity
@Table(name = "style_de_jeux")
@EntityListeners(AuditingEntityListener.class)
public class StyleDeJeux {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String nom;

    @ManyToOne
    private Jeux jeux;
}
