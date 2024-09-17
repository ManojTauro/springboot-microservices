package com.manoj.accounts.dto;

@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
public record ResponseDTO() {
    @Schema(
            description = "Status code in the response"
    )
    private String statusCode;

    @Schema(
            description = "Status message in the response"
    )
    private String statusMsg;
}
