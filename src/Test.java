import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.humatic.dsj.DSCapture;
import de.humatic.dsj.DSEnvironment;
import de.humatic.dsj.DSFilterInfo;

public class Test {

	public static void main(String[] args) throws IOException,
			InterruptedException, AWTException {

		JFrame frame = new JFrame();

		DSEnvironment.unlockDLL("lundvall@telia.com", -513080L, 1833569L, 0L);

		DSFilterInfo[][] dsi = DSCapture.queryDevices();

		DSFilterInfo info = dsi[0][0];

		System.out.println(info.getName());
		System.out.println(info.getPath());
		final DSCapture capture = new DSCapture(0, info, true,
				DSFilterInfo.doNotRender(),   null);

		// frame.add(capture);

		JPanel p = new JPanel();
		JButton buttonCapture = new JButton("capture");
		p.add(buttonCapture);

		p.add(capture);
		frame.add(p);

		frame.pack();
		frame.setVisible(true);

		buttonCapture.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				
				//photo
				
				File file = new File("screencapture.jpg");
				
				BufferedImage image = capture.getImage();
				;
				capture.getActiveVideoDevice().showPropertiesDialog();
				try {
					ImageIO.write(image, "jpg", file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				
				//video
				capture.setCaptureFile("captureTest.asf", DSFilterInfo.doNotRender(), DSFilterInfo.doNotRender(), true);
				capture.record();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				capture.stop();
				
				
				// stockage
				try {
					Connection connection = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/testblob", "root", "");

					PreparedStatement st = connection
							.prepareStatement("INSERT INTO t_file VALUES(?,?,?)");

					InputStream is = new FileInputStream("screencapture.jpg");

					st.setBinaryStream(2, is, (int) file.length());
					st.setString(3, "test.jpg");
					st.setInt(1, 1);

					 st.execute();
					System.out.println("OK");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

	}

}
