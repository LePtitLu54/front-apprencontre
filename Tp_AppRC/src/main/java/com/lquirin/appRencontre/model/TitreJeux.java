package com.lquirin.appRencontre.model;



import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Jeux")
@EntityListeners(AuditingEntityListener.class)
public class TitreJeux {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String nomJeux;


}