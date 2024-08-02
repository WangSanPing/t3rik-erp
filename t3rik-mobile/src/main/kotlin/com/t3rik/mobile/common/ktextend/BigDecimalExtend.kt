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