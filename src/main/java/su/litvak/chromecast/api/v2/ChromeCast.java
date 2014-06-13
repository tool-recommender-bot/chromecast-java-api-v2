package su.litvak.chromecast.api.v2;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class ChromeCast {
    public final static String SERVICE_TYPE = "_googlecast._tcp.local.";

    private final JmDNS mDNS;
    private final String name;
    private Channel channel;

    public ChromeCast(JmDNS mDNS, String name) {
        this.mDNS = mDNS;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return getServiceInfo().getInet4Addresses()[0].getHostAddress();
    }

    public int getPort() {
        return getServiceInfo().getPort();
    }

    public String getAppsUrl() {
        return getServiceInfo().getURLs()[0];
    }

    public String getApplication() {
        return getServiceInfo().getApplication();
    }

    private ServiceInfo getServiceInfo() {
        return mDNS.getServiceInfo(SERVICE_TYPE, name);
    }

    public synchronized void connect() throws IOException, GeneralSecurityException {
        if (channel == null) {
            channel = new Channel(getIpAddress(), getPort());
        }
    }

    public synchronized void disconnect() throws IOException {
        if (channel == null) {
            return;
        }

        channel.close();
        channel = null;
    }
}
