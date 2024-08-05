import java.math.BigDecimal


/**
 * BigDecimal扩展类
 * @author t3rik
 * @date 2024/8/1 19:29
 */


/**
 * 判断一个数是不是0
 */
fun BigDecimal.isZero(): Boolean {
    return this.compareTo(BigDecimal.ZERO) == 0
}

/**
 * 判断a是否等于b
 */
fun BigDecimal.isGreater(value: BigDecimal): Boolean {
    return this > value
}

/**
 * 判断a是否大于等于b
 */
fun BigDecimal.isGreaterOrEqual(value: BigDecimal): Boolean {
    return this >= value
}

/**
 * 判断数值是否为null，如果为null，返回0
 * 不为null 返回当前数值
 */
fun BigDecimal?.orZero(): BigDecimal {
    return this ?: BigDecimal.ZERO
}