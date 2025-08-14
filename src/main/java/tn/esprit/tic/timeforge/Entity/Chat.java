package tn.esprit.tic.timeforge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le message ne peut pas être vide")
    @Size(max = 500, message = "Le message ne doit pas dépasser 500 caractères")
    private String message;

    @ManyToOne
    @JsonIgnore
    private User user; // ✅ Correction du nom

    @Lob
    private byte[] fileData;

    @Size(max = 255, message = "Le nom du fichier ne doit pas dépasser 255 caractères")
    private String fileName;

    @Size(max = 50, message = "Le type de fichier ne doit pas dépasser 50 caractères")
    private String fileType;

    private boolean isVoiceMessage;
    private boolean isFileMessage;

    @ManyToOne
    @JsonIgnore
    private User userr;
}
