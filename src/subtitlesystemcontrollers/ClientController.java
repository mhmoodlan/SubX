package subtitlesystemcontrollers;

import subtitlesystemviews.ClientViewer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Mahmoud Aslan
 */
public class ClientController {
    
    private final String HOST;
    private final int PORT;
    private Socket client;
    private BufferedReader is;
    private final ClientViewer clientViewer;
    
    public ClientController(ClientViewer clientViewer, String host, int port) {
        this.HOST = host;
        this.PORT = port;
        this.clientViewer = clientViewer;
    }
    
    public void create() {
        try {
            client = new Socket(HOST, PORT);
            System.out.println("Connected");
            startListening();
        } catch(IOException e) {
            try {
                Thread.sleep(1000);
                create();
            } catch (InterruptedException ex) {
                create();
            }
        }
            
    }
    
    public void startListening() {
        try {
            is = new BufferedReader(new InputStreamReader (client.getInputStream()));
            while(true) {
                try {
                    String line = is.readLine();
                    if(line != null) {
                        if(line.startsWith("$ServerController")) {
                            String command = line.substring(line.indexOf("cmd")+4, line.indexOf("cmd")+8);
                            String value = line.substring(line.indexOf("value")+6, line.length());
                            //System.out.println(command);
                            switch(command) {
                                case "font" :
                                    ClientViewer.setLabelFontSize(Boolean.parseBoolean(value));
                                    break;
                                case "dimx" :
                                    clientViewer.updateDimensions(1, Boolean.parseBoolean(value));
                                    break;
                                case "dimy" :
                                    clientViewer.updateDimensions(2, Boolean.parseBoolean(value));
                                    break;
                                case "posx" :
                                    clientViewer.updatePosition(1, value);
                                case "posy" :
                                    clientViewer.updatePosition(2, value);
                                    break;
                                case "proj" :
                                    clientViewer.setProjectionMode(value);
                                    break;
                                default :
                                    break;
                            }
                        } else {
                            ClientViewer.setLabelText(line);   
                        }
                    }
                    else
                        System.out.println("NULL was recieved!");
                } catch (IOException ex) {
                    //System.out.println("failed listeneing");
                    create();
                }
            }
        } catch(IOException e) {
            create();
        }
    }
}
