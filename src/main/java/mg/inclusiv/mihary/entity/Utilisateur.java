package mg.inclusiv.mihary.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

    @Entity
    @org.hibernate.annotations.Proxy(lazy=false)
    @Data

    @AllArgsConstructor
    @NoArgsConstructor
    @Inheritance(strategy=InheritanceType.SINGLE_TABLE)

    @Table(name = "utilisateur")
    public class Utilisateur implements Serializable {
        @Id
        @Column(name="idUtilisateur", nullable=false, length=10)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(length = 50,nullable=false,unique = true)
        private String login;

        @Column(length = 100,nullable=false)
        private String mdpUtilisateur;

        @Column(length = 50)
        private String nomUtilisateur;

        @Column(length = 50)
        private String prenomUtilisateur;

        @Column(length = 50)
        private String adresseUtilisateur;

        @Column(length = 50,nullable=false,unique = true)
        private String email;

        @Column()
        private Double soldeUtilisateur;

        @Lob
        @Column()
        private byte[] photoUtilisateur;

        @Column(length = 10)
        private Long nifCoop;

        @Column(length = 17)
        private Long statCoop;

        @Column(length = 20)
        private String telephoneUtilisateur;

        @Column(length = 50)
        private String responsableCoop;

        @Column(length = 50)
        private String statutCompteCoop;

        @Column(length = 12)
        private Long cinAgriculteur;

        @ManyToOne(fetch = FetchType.LAZY)
        @org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)
        @JoinColumn(name = "cooperative_id")
        private Utilisateur cooperative;

        @JsonIgnore
        @OneToMany(mappedBy = "cooperative", fetch = FetchType.LAZY)
        @org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)
        private List<Utilisateur> agriculteurs;

        @JsonIgnore
        @OneToMany(mappedBy = "utilisateur")
        @org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)
        private List<Commande> commandes;

        public Utilisateur(String username, String encodedPassword, String fullname,String email) {
            this.login=username;
            this.mdpUtilisateur=encodedPassword;
            this.nomUtilisateur=fullname;
            this.email=email;
        }

        public enum TypeUtilisateur {
            COOPERATIVE,
            AGRICULTEUR,
            CLIENT,ADMIN
        }

        @Enumerated(EnumType.STRING)
        private TypeUtilisateur typeUtilisateur;

        public void setTypeUtilisateur(String typeUtilisateur) {
            this.typeUtilisateur = TypeUtilisateur.valueOf(typeUtilisateur);
        }
    }
