package com.example.thcources.core.model

import java.math.BigDecimal
import java.math.RoundingMode

public fun Rate(
    value: String,
): Rate = Rate(
    value.toBigDecimal().setScale(1, RoundingMode.HALF_UP),
)

public fun Rate(
    value: Double,
): Rate = Rate(
    value.toBigDecimal().setScale(1, RoundingMode.HALF_UP),
)

@JvmInline
public value class Rate internal constructor(
    public val value: BigDecimal,
) {
    init {
        check(
            value > 0.toBigDecimal() &&
                    value <= 5.toBigDecimal() &&
                    value.scale() == 1,
        )
    }
}
