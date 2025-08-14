package tn.esprit.tic.timeforge.Entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadReceiptRequest {

    private String channel;
    private String username;

    public ReadReceiptRequest() {
        super();
    }

}