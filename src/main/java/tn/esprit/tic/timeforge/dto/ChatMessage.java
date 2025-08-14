package tn.esprit.tic.timeforge.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessage {

    private String message;
    private String user;
    private byte[] fileData;
    private String fileName;
    private String fileType;
    private boolean isVoiceMessage;
    private boolean isFileMessage;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }



    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public boolean isVoiceMessage() {
        return isVoiceMessage;
    }

    public void setVoiceMessage(boolean voiceMessage) {
        isVoiceMessage = voiceMessage;
    }

}