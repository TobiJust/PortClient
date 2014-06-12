/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.net.InetSocketAddress;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import com.jme3.system.AppSettings;

/**
 * Network client that connects to the server with the MINA libraries.
 * @author Georg Labbé
 */
public class NetworkClient implements Runnable{

    public final static String CONECTION_TIMEOUT = "ConectionTimeout";
    public final static String HOST_NAME = "Hostname";
    public final static String PORT = "Port";
    
    AppSettings settings = null;
            
    public NetworkClient(AppSettings settings) {
        this.settings = settings;
    }
    
    public void run() {
        // --- NETWORK STUFF ---
        NioSocketConnector connector = new NioSocketConnector();
        // Connection timeout 5000 aus Konfigurationsdatei laden
        connector.setConnectTimeoutMillis(settings.getInteger(CONECTION_TIMEOUT));

        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

        connector.getFilterChain().addLast("logger", new LoggingFilter());
        
        connector.setHandler(new ClientSessionHandler());
        IoSession session;
        for (;;) {
            try {
                // Hostname = "localhost" und Port = 1234 sollte evtl. irgendwo eingetragen werden
                ConnectFuture future = connector.connect(new InetSocketAddress(settings.getString(HOST_NAME), settings.getInteger(PORT)));
                future.awaitUninterruptibly();
                session = future.getSession();
                break;
            } catch (RuntimeIoException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    //...
                }
            }
        }
        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }
    
    
}
