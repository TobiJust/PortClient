package network;
import info.Message;
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
		Message msg = (Message)message;
		switch(msg.getId()){
		case ERROR:
			System.out.println("ERROR");
			break;
		case INFO_ALL:
			System.out.println("INFO_ALL");
			break;
		case LOGIN:
			System.out.println("LOGIN");
			break;
		case PLAYER_POSITION:
			System.out.println("PLAYER_POSITION");
			break;
		case TEXT_MESSAGE:
			System.out.println((String)msg.getContent());
			break;
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		session.close(true);
	}
}