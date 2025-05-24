package id.rezyfr.routinist.presentation.component.core

sealed class NetworkState{

   data object Good: NetworkState()

   data object Failed: NetworkState()

}
