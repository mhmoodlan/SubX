package subtitlesystemcontrollers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import subtitlesystemviews.ServerViewer;

/**
 *
 * @author Mahmoud Aslan
 */
public class ServerController {

    public static String fileName;
    private ServerSocket server;
    private final int PORT;
    private static ArrayList<String> lines;
    private static int currentSubtitle;
    private static ArrayList<PrintStream> ps;
    private static boolean clientConnected;

    public ServerController(int port, String fileName) {
        this.PORT = port;
        ServerController.fileName = fileName;
        ServerController.lines = new ArrayList<String>();
        ServerController.currentSubtitle = 0;
        ServerController.clientConnected = false;
        ServerController.ps = new ArrayList<PrintStream>();
        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void readFile(String fileName) throws FileNotFoundException, IOException {
        lines.clear();
        currentSubtitle = 0;
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        lines = new ArrayList<String>();
        String currentLine;
        while ((currentLine = br.readLine()) != null) {
            lines.add(currentLine);
        }
        ps.stream().forEach((thrps) -> {
            thrps.print(lines.get(currentSubtitle) + "\n");
        });
        ServerViewer.setCurrentSubtitleLabel(lines.get(currentSubtitle));
        if (currentSubtitle < lines.size() - 1) {
            ServerViewer.setNextSubtitleLabel(lines.get(currentSubtitle + 1));
        }

    }

    public static void nextSubtitle() {
        if (currentSubtitle < lines.size() - 1) {
            currentSubtitle += 1;
            ps.stream().forEach((thrps) -> {
                thrps.print(lines.get(currentSubtitle) + "\n");
            });
            ServerViewer.setCurrentSubtitleLabel(lines.get(currentSubtitle));
            if (currentSubtitle < lines.size() - 1) {
                ServerViewer.setNextSubtitleLabel(lines.get(currentSubtitle + 1));
            }
        }

    }

    public static void preSubtitle() {
        if (currentSubtitle > 0) {
            ServerViewer.setNextSubtitleLabel(lines.get(currentSubtitle));
            currentSubtitle -= 1;
            ps.stream().forEach((thrps) -> {
                thrps.print(lines.get(currentSubtitle) + "\n");
            });
            ServerViewer.setCurrentSubtitleLabel(lines.get(currentSubtitle));
        }
    }
    
    public static void sendCommandToClient(String command, String value) {
        ps.stream().forEach((thrps) -> {
            thrps.print("$ServerController: cmd=" + command + ", value=" + value +"\n");
        });
    }

    public void create() {
        try {
            (new Thread() {
                @Override
                public void run() {
                    try {
                        Socket client = server.accept();
                        System.out.println("Client Connected");
                        
                        ps.add(new PrintStream(client.getOutputStream()));
                        readFile(fileName);
                        ps.get(ps.size()-1).print(lines.get(currentSubtitle) + "\n");
                        clientConnected = true;
                        ServerViewer.setControlsState(true);
                        ps.get(ps.size()-1).print("$ServerController: cmd=" + "proj" + ", value=" + ServerViewer.getProjMode() +"\n");
                        while(true) {
                            client = server.accept();
                            System.out.println("Client Connected");
                            ps.add(new PrintStream(client.getOutputStream()));
                            ps.get(ps.size()-1).print(lines.get(currentSubtitle) + "\n");
                            ps.get(ps.size()-1).print("$ServerController: cmd=" + "proj" + ", value=" + ServerViewer.getProjMode() +"\n");
                        }
                    } catch (IOException ex) {
                    }
                }
            }).start();

        } catch (Exception e) {
        }
    }
}
