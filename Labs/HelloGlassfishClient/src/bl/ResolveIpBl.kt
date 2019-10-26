package bl

import com.cdyne.ws.IP2Geo

public class ResolveIpBl {
    private val ip2Geo = IP2Geo()
    fun getCity(ip: String): String {
        return ip2Geo.iP2GeoSoap.resolveIP(ip,"").city
    }
}