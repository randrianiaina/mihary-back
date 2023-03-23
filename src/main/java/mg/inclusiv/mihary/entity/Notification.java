
package mg.inclusiv.mihary.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
    @Entity
    @Table(name = "notification")
    public class Notification {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "idNotification")
        private Long idNotification;

        @ManyToOne()
        @JoinColumn(name = "utilisateur_idUtilisateur")
        private Utilisateur utilisateur;

        @Column(length = 200,name = "messageNotification")
        private String messageNotification;

        @Column(name = "lu")
        private Boolean lu;

        @Column(name = "dateNotification")
        private LocalDate dateNotification;

        // Getters et setters
    }
