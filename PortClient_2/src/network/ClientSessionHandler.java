package network;

import de.lessvoid.nifty.controls.Console;
import info.Info;
import info.InfoManager;
import info.Message;
import info.VesselInfo;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ClientSessionHandler extends IoHandlerAdapter {

    private boolean finished;

    public boolean isFinished() {
        return finished;
    }

    @Override
    public void sessionOpened(IoSession session) {
        // send summation requests
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        Message msg = (Message) message;
        switch (msg.getId()) {
            case ERROR:
                System.out.println("ERROR");
                break;
            case INFO_ALL:
//                System.out.println("INFO_ALL");
                Info ofn = (Info) msg.getContent();
                InfoManager.setVesselList(ofn.getVesselList());
                // TODO: getVesselList XYZ setzen
                break;
            case LOGIN:
                System.out.println("LOGIN");
                break;
            case PLAYER_POSITION:
                System.out.println("PLAYER_POSITION");
                break;
            case TEXT_MESSAGE:
                Console con = InfoManager.getConsole();
                if (con != null) {
                    String chat = (String) msg.getContent();
                    if (!chat.substring(0, chat.indexOf(":")).equals(InfoManager.getPlayerName())) {
                        con.output(chat);
                    }
                }
                break;
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        cause.printStackTrace();
        session.close(true);
    }
}