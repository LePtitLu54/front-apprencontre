package com.lquirin.appRencontre.model;


import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "competenceJeux")
@IdClass(Jeux.CleJeux.class)
@EntityListeners(AuditingEntityListener.class)
public class Jeux {

   @Id
   private Integer utilisateurId;
   @Id
   private Integer titreJeuxId;
   @Id
   private int niveau;


   @ManyToOne
   @MapsId("utilisateurId")
   @JoinColumn(name = "utilisateurId")
   private Utilisateur utilisateur;

   @ManyToOne
   @MapsId("titreJeuxId")
   @JoinColumn(name = "titreJeuxId")
   private TitreJeux titreJeux;

   @Embeddable
   class CleJeux implements Serializable {
      Integer utilisateurId;
      Integer titreJeuxId;
      Integer niveau;
   }

   @OneToMany(mappedBy = "jeux")
   private List<StyleDeJeux> styleDeJeux;
}