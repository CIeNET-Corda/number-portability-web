package com.cienet.np.service.dto

data class NumberDTO(var operator: String, var id: String, var state: Int) {
    companion object {
        fun fromModel(operator: String, id: String, state: Int): NumberDTO {
            return NumberDTO(
                operator = operator,
                id = id,
                state = state
            )
        }
    }
}
