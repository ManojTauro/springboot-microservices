package com.manoj.accounts.dto;

//@Schema(
//        name = "Response",
//        description = "Schema to hold successful response information"
//)
public record ResponseDTO(
//        @Schema(
//                description = "Status code in the response"
//        )
        String statusCode,

//                @Schema(
//                description = "Status message in the response"
//)
        String statusMsg
) {

}
