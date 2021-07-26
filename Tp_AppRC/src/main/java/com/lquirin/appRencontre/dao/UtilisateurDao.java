package com.lquirin.appRencontre.dao;

import com.lquirin.appRencontre.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Integer> {
    @Query("FROM Utilisateur u JOIN FETCH u.listeRoles WHERE email = :email")
    Optional<Utilisateur> trouverParPseudoAvecRoles(@Param("email") String email);

}