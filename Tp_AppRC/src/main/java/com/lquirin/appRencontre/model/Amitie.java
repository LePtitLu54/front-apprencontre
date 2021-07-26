package com.lquirin.appRencontre.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "amitie")
@IdClass(Amitie.class)
public class Amitie implements Serializable {

    @Id
    private Integer utilisateur1Id;
    @Id
    private Integer utilisateur2Id;
    @ManyToOne
    @MapsId("utilisateur_1_id")
    @JoinColumn(name = "utilisateur_1_id")
    private Utilisateur utilisateur1;
    @ManyToOne
    @MapsId("utilisateur_2_id")
    @JoinColumn(name = "utilisateur_2_id")
    private Utilisateur utilisateur2;
    private boolean accepte;

    @Data
    @Embeddable
    class CleAmitie implements Serializable {
        @Column(name = "utilisateur_1_id")
        Integer utilisateur1Id;
        @Column(name = "utilisateur_2_id")
        Integer utilisateur2Id;
    }

}
