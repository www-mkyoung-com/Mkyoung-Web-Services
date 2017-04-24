package com.mkyong.client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPBinding;
import com.mkyong.ws.ImageServer;
import com.mkyong.ws.ImageServerImplService;

public class ImageClient{
	
	public static void main(String[] args) throws Exception {
	   
		ImageServerImplService imgService = new ImageServerImplService();
		ImageServer imgServer = imgService.getImageServerImplPort();
	
        //enable MTOM in client
        BindingProvider bp = (BindingProvider) imgServer;
        SOAPBinding binding = (SOAPBinding) bp.getBinding();
        binding.setMTOMEnabled(true);
        
        //convert to byte array
        BufferedImage originalImage = ImageIO.read(new File("c:\\images\\rss.png"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( originalImage, "png", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        
		imgServer.uploadImage(imageInByte);
		
		
		//test download
		/*byte[] bDownload = imgServer.downloadImage("rss.png");
		InputStream in = new ByteArrayInputStream(bDownload);
		BufferedImage biImage = ImageIO.read(in);

		JFrame frame = new JFrame();
        frame.setSize(300, 300);
        JLabel label = new JLabel(new ImageIcon(biImage));
        frame.add(label);
        frame.setVisible(true);*/
    }

}
