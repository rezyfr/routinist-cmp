package id.rezyfr.routinist.presentation.util

fun Number.noDecimal() : String {
    return this.toString().split(".")[0]
}