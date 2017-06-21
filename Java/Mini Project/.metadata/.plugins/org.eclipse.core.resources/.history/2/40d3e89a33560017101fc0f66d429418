package mail;

public class Proxy implements IProxy {
	
	Server mailServer;

	@Override
	public void sender(Mail mail) {
		if(mailServer == null)
			mailServer = new Server();
		
		mailServer.sender(mail);
	}

	@Override
	public Mail get() {
		// TODO Auto-generated method stub
		return null;
	}

}
