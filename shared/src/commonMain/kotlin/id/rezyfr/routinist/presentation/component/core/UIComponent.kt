package id.rezyfr.routinist.presentation.component.core


sealed class UIComponent {

    data class Toast(
        val alert: Pair<String, String>
    ): UIComponent()

    data class Dialog(
        val alert: Pair<String, String>
    ): UIComponent()


    data class ToastSimple(
        val title:String,
    ): UIComponent()

    data class DialogSimple(
        val title:String,
        val description:String
    ): UIComponent()


    data class None(
        val message:String,
    ): UIComponent()



}