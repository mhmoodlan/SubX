
package subtitlesystemviews;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import subtitlesystemcontrollers.ClientController;

/**
 *
 * @author Mahmoud Aslan
 */

public class ClientViewer extends JFrame {

    private final Container cont;
    public static JLabel label;
    private static String host;
    private static int port;
    private static Dimension dim;
    private static int boundX, boundY, dimX, dimY, isExtend;
    
    public ClientViewer() {
        cont = getContentPane();
        label = new JLabel();
        dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        /*GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        GraphicsConfiguration[] gf = gs[0].getConfigurations();
        Rectangle r = gf[0].getBounds();*/
        boundX = dim.width;
        boundY = dim.height-100;
        dimX = dim.width;
        dimY = 100;
        isExtend = dim.width;
    }
    
    public static void setLabelText(String text) {
        label.setText("<html>" + text + "</html>");
    }
    
    public static void setLabelFontSize(boolean type) {
        Font f = ClientViewer.label.getFont();
        int x = (int)f.getSize();
        
        if(type) { // increase
            if(x<100)
                label.setFont(new Font("Helvetica Neue", 1, x+2));
        } else { // decrease
            if(x>2)
                label.setFont(new Font("Helvetica Neue", 1, x-2));
        }
    }
    
    public void updateDimensions(int axis, boolean type) {
        //System.out.println("Before update : " + dimX + "   " + dimY);
        if(axis == 1) { // x axis
            if(type && dimX < dim.width) {
                dimX += 20;
                if(boundX != 0) {
                    boundX -= 20;   
                }
            }else if(!type && dimX > 100) {
                dimX -= 20;
            }
        } else if(axis == 2) { //y axis 
            if(type && dimY < dim.height-150) {
                dimY += 10;
                if(boundY != 0) {
                    boundY -= 10;   
                }
            } else if(!type && dimY > 35) {
                dimY -= 10;
                if(boundY != 0) {
                    boundY += 10;
                }
            }
        }
        setBounds(boundX, boundY, dimX, dimY);
        
        label.setBounds(15, 0, dimX-30, dimY);
        label.setMaximumSize(new Dimension(dimX-30, dimY));
        //System.out.println("After update : " + dimX + "   " + dimY);
    }
    
    public void updatePosition(int axis, String pos) {
        refreshDim();
        if(axis == 1) {
            if(pos.equals("left")) {
                boundX = 0 + isExtend;
            } else if(pos.equals("right")) {
                boundX = dim.width-dimX + isExtend;
            } else if(pos.equals("center")) {
                boundX = (dim.width-dimX)/2 + isExtend;
            }
        } else {
            if(pos.equals("top")) {
                boundY = 0;
            } else if(pos.equals("bottom")) {
                boundY = dim.height-dimY;
            }
        }
        setBounds(boundX, boundY, dimX, dimY);
    }
    
    public void setProjectionMode(String mode) {
        refreshDim();
        if(mode.equals("dup")) {
            isExtend = 0;
            updatePosition(1, "left");
        } else if(mode.equals("ext")) {
            isExtend = dim.width;
            updatePosition(1, "left");
        }
    }
    
    private void refreshDim() {
        //System.out.println(dimX);
        dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        if(dimX > dim.width) dimX = dim.width;
        label.setBounds(15, 0, dimX-30, dimY);
        label.setMaximumSize(new Dimension(dimX-30, dimY));
        if(isExtend != 0) {
            isExtend = dim.width;
        }
        //System.out.println(dimX);
    }
    
    private void resetBounds(int position) {
        boundX = 0;
        boundY = dim.height-100;
        setBounds(boundX, boundY, dimX, dimY);
    }
    
    private void create() {
        cont.setLayout(null);
        dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(boundX, boundY, dimX, dimY);
        setUndecorated(true);
        label.setBounds(15, 0, dimX-30, 100);
        label.setMaximumSize(new Dimension(dimX-30, 100));
        label.setText("***START***");
        label.setForeground(Color.white);
        label.setFont(new Font("Helvetica Neue", 1, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        cont.add(label);
        cont.setBackground(Color.black);
        setTitle("TedxSubtitle");
        setVisible(true);
        setAlwaysOnTop(true);
    }
    
    public static void main(String[] args) {
        
       FileReader fr;
        try {
            fr = new FileReader("./SubXClient.ini");
            BufferedReader br = new BufferedReader(fr);
            String configurationLine;
            try {
                while((configurationLine = br.readLine()) != null) {
                    String[] values = configurationLine.split("=");
                    for(int i = 0 ; i < values.length ; i+=2) {
                        String key = values[i].trim();
                        String value = values[i+1].trim();
                        switch(key) {
                            case "port" :
                                port = Integer.parseInt(value);
                                break;
                            case "host" :
                                host = value;
                                break;
                            default:
                                break;
                        }
                    }
                }
            } catch(IOException | NumberFormatException e ) {
            }
        } catch (FileNotFoundException ex) {
            //Set label text to not found!
            System.out.println("FILE NOT FOUND");
        }
       
       
       ClientViewer tedx = new ClientViewer();
       tedx.create();
       tedx.setDefaultCloseOperation(EXIT_ON_CLOSE);
       ClientController client = new ClientController(tedx, host, port);
       new Thread() {
           @Override
           public void run() {
               client.create();
           }
       }.start();
    }
}
