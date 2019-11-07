package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseModel {

    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("code")
    @Expose
    private Integer code;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("details")
    @Expose
    private List<String> details;




}
