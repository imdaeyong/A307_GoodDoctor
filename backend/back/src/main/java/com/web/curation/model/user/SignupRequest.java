package com.web.curation.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;



@Data
@Valid
@ToString
public class SignupRequest {
    @ApiModelProperty(required = true)
    @NotNull
    private String email;
    @ApiModelProperty(required = true)
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$")
    private String password;
    @ApiModelProperty(required = true)
    @NotNull
    private String nickname;
}
