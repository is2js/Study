package mail;

public class Proxy implements IProxy {
	
	Server mailServer;

	@Override
	public void sender(Mail mail) {
		if(mailServer == null)
			mailServer = new Server();
		
		mailServer.sender(mail);
	}
}
