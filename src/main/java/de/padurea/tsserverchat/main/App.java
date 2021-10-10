package de.padurea.tsserverchat.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.github.manevolent.ts3j.api.TextMessageTargetMode;
import com.github.manevolent.ts3j.event.TS3Listener;
import com.github.manevolent.ts3j.event.TextMessageEvent;
import com.github.manevolent.ts3j.identity.LocalIdentity;
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket;

import de.padurea.tsserverchat.utils.FileManager;
import de.padurea.tsserverchat.utils.MainWindow;

public class App {
	
	protected static LocalTeamspeakClientSocket socket;
	
	public static void main(String[] args) throws InterruptedException {
		FileManager fm = new FileManager(System.getProperty("user.dir") + "//config.txt");
		
		//check config
		if (!fm.fileExist() || !fm.getFile().isFile()) {
			try {
				fm.getFile().mkdirs();
				fm.getFile().delete();
				fm.getFile().createNewFile();
				fm.write("username", "ServerChatViewer");
				fm.write("hostname", "127.0.0.1");
				System.err.println("Please edit your configuation.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			MainWindow.main(args);
			socket = new LocalTeamspeakClientSocket();
			
			try {
				try {
					socket.setIdentity(LocalIdentity.read(new File(System.getProperty("user.dir") + "//identity.ini")));
				} catch (FileNotFoundException ex) {
					System.err.println("Please add a identity file. (filename: identity.ini)");
					return;
				}
				socket.setNickname(fm.getValue("username"));
				socket.addListener(getEventListeners());
				
				socket.connect(fm.getValue("hostname"), 15000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
		}
	}

	private static TS3Listener getEventListeners() {
		return new TS3Listener() {
			public void onTextMessage(TextMessageEvent e) {
				if (e.getTargetMode() == TextMessageTargetMode.SERVER) {
					System.out.println("ServerChatMSG by " + e.getInvokerName() + "(ID" + e.getInvokerId() + "): " + e.getMessage());
				}
			}
			
			
		};
	}
	
	public static LocalTeamspeakClientSocket getSocket() {
		return socket;
	}
}
