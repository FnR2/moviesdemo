package mobile.app.moviesdemo

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import mobile.app.data.DefaultConnectionChecker
import javax.inject.Inject

data class ConnectionChecker @Inject constructor(
    private val context: Context
) : DefaultConnectionChecker {
    override fun isConnectionAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
