package com.cienet.np.service.dto

data class NumberDTO(var operator: String, var number: String, var state: Int) {
    companion object {
        fun fromModel(operator: String, number: String, state: Int): NumberDTO {
            return NumberDTO(
                operator = operator,
                number = number,
                state = state
            )
        }
    }
}
