package com.mkyong.client;

import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.MTOMFeature;
import javax.xml.ws.soap.SOAPBinding;

import com.mkyong.ws.ImageServer;

public class ImageClient{
	
	public static void main(String[] args) throws Exception {
	   
		URL url = new URL("http://localhost:9999/ws/image?wsdl");
        QName qname = new QName("http://ws.mkyong.com/", "ImageServerImplService");

        Service service = Service.create(url, qname);
        ImageServer imageServer = service.getPort(ImageServer.class);

        /************  test upload ****************/
        Image imgUpload = ImageIO.read(new File("c:\\images\\rss.png"));
        
        //enable MTOM in client
        BindingProvider bp = (BindingProvider) imageServer;
        SOAPBinding binding = (SOAPBinding) bp.getBinding();
        binding.setMTOMEnabled(true);
   
        String status = imageServer.uploadImage(imgUpload);
        System.out.println("imageServer.uploadImage() : " + status);
        
        
        /************  test download  ***************/
       /* Image image = imageServer.downloadImage("rss.png");
        
        //display it in frame
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);

        System.out.println("imageServer.downloadImage() : Download Successful!");*/

    }

}