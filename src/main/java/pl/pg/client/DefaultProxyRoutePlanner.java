package pl.pg.client;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.protocol.HttpContext;

public class DefaultProxyRoutePlanner extends DefaultRoutePlanner {

    private final String scheme;
    private final String host;
    private final String[] nonProxyHosts;
    private final int port;

    public DefaultProxyRoutePlanner(SchemePortResolver schemePortResolver, String scheme, String host, String[] nonProxyHosts, int port) {
        super(schemePortResolver);
        this.scheme = scheme;
        this.host = host;
        this.nonProxyHosts = nonProxyHosts;
        this.port = port;
    }

    private boolean doesTargetMatchNonProxy(HttpHost target) {
        if(nonProxyHosts == null || nonProxyHosts.length < 1) {
            return false;
        }
        String uriHost = target.getHostName();
        for(String nonProxyHost: nonProxyHosts) {
            if(uriHost.matches(nonProxyHost)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {
        if(doesTargetMatchNonProxy(target)) {
            return null;
        }
        return new HttpHost(host, port, scheme);
    }
}
