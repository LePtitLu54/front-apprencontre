package com.lquirin.appRencontre.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lquirin.appRencontre.dao.UtilisateurDao;
import com.lquirin.appRencontre.model.Role;
import com.lquirin.appRencontre.model.Utilisateur;
import com.lquirin.appRencontre.security.JwtUtil;
import com.lquirin.appRencontre.security.UserDetailsServiceCustom;
import com.lquirin.appRencontre.view.MyJsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class UtilisateurController {
    UtilisateurDao utilisateurDao;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private UserDetailsServiceCustom userDetailsServiceCustom;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UtilisateurController(
            UtilisateurDao utilisateurDao,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager,
            UserDetailsServiceCustom userDetailsServiceCustom,
            PasswordEncoder passwordEncoder

    ){
        this.utilisateurDao = utilisateurDao;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsServiceCustom = userDetailsServiceCustom;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/authentification")
    public ResponseEntity<String> authentification(@RequestBody Utilisateur utilisateur) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            utilisateur.getPseudo(), utilisateur.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Mauvais pseudo / mot de passe");
        }

        UserDetails userDetails = this.userDetailsServiceCustom.loadUserByUsername(utilisateur.getEmail());

        return ResponseEntity.ok(jwtUtil.generateToken(userDetails));
    }
    @JsonView(MyJsonView.VueUtilisateur.class)
    @PostMapping("/inscription")
    public ResponseEntity<String> inscription(@RequestBody Utilisateur utilisateur){

        Optional<Utilisateur> utilisateurDoublon = utilisateurDao.trouverParPseudoAvecRoles(utilisateur.getEmail());

        if(utilisateurDoublon.isPresent()) {
            return ResponseEntity.badRequest().body("Ce pseudo est déja utilisé");
        } else {

            utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));

         Role role_utilisateur = new Role();
            role_utilisateur.setId(1);

            utilisateur.getListeRoles().add(role_utilisateur);

            utilisateurDao.saveAndFlush(utilisateur);

            return ResponseEntity.ok(Integer.toString(utilisateur.getId()));
        }
    }

    @PostMapping("/admin/utilisateur")
    public ResponseEntity<String> updateUser(@RequestBody Utilisateur utilisateur){

        Optional<Utilisateur> utilisateurBddOptional = utilisateurDao.trouverParPseudoAvecRoles(utilisateur.getEmail());

        if(utilisateurBddOptional.isPresent()) {
            Utilisateur utilisateurBdd = utilisateurBddOptional.get();
            utilisateur.setPassword(utilisateurBdd.getPassword());
            utilisateurDao.save(utilisateur);
            return ResponseEntity.ok().body("Utilisateur mis à jour");
        }

        return ResponseEntity.notFound().build();
    }

    @JsonView(MyJsonView.VueUtilisateur.class)
    @GetMapping("/user/utilisateur-connecte")
    public ResponseEntity<Utilisateur> getInformationUtilisateurConnecte(
            @RequestHeader(value="Authorization") String authorization){
        //la valeur du champs authorization est extrait de l'entête de la requête

        //On supprime la partie "Bearer " de la valeur de l'authorization
        String token = authorization.substring(7);

        //on extrait l'information souhaitée du token
        String username = jwtUtil.getTokenBody(token).getSubject();

        Optional<Utilisateur> utilisateur = utilisateurDao.trouverParPseudoAvecRoles(username);

        if(utilisateur.isPresent()) {
            return ResponseEntity.ok().body(utilisateur.get());
        }

        return ResponseEntity.notFound().build();
    }

    @JsonView(MyJsonView.VueUtilisateur.class)
    @GetMapping("/admin/utilisateur/{id}")
    public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable int id) {

        Optional<Utilisateur> utilisateur = utilisateurDao.findById(id);

        if(utilisateur.isPresent()) {
            return ResponseEntity.ok(utilisateur.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @JsonView(MyJsonView.VueUtilisateur.class)
    @GetMapping("/admin/utilisateurs")
    public ResponseEntity<List<Utilisateur>> getUtilisateurs () {

        return ResponseEntity.ok(utilisateurDao.findAll());
    }

    @DeleteMapping("/admin/utilisateur/{id}")
    public ResponseEntity<Integer> deleteUtilisateur (@PathVariable int id) {

        if(utilisateurDao.existsById(id)) {
            utilisateurDao.deleteById(id);
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
    /*@ResponseBody
    @GetMapping(value = "/test/image-ressource")
    public ResponseEntity<byte[]> getImageAsRessource(){
        try{
            URL url = new URL("file:///C:/Users/stagiaire/Desktop/blocnote/image_storage/image.jpg");
            InputStream inputStream = url.openStream();
            HttpHeaders headers = new HttpHeaders();
            byte[] media = IOUtils.toByteArray(inputStream);
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(media, headers, HttpStatus.OK);

        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/test/image-upload")
    @ResponseBody
    public ResponseEntity<String> saveBase64(@RequestBody String base64Str) {
        StringBuffer fileName = new StringBuffer();
        fileName.append(UUID.randomUUID().toString().replaceAll("-", ""));
        if (base64Str.equals("")) {
            return ResponseEntity.badRequest().body("L'image est vide");
        } else if (base64Str.contains("data:image/png;")) {
            base64Str = base64Str.replace("data:image/png;base64,", "");
            fileName.append(".png");
        } else if (base64Str.contains("data:image/jpeg;")) {
            base64Str = base64Str.replace("data:image/jpeg;base64,", "");
            fileName.append(".jpeg");
        } else {
            return ResponseEntity.badRequest().body("L'image doit être au format jpg ou png");
        }

        File file = new File("", fileName.toString());
        byte[] fileBytes = Base64.getUrlDecoder().decode(base64Str);
        try {
            imageService.uploadToLocalFileSystem(file, fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Sauvegarde échouée");
        }


        return ResponseEntity.ok().body("Sauvegarde réussie");
    }*/

}


