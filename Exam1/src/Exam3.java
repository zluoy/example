import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Exam3 {
	public static void main(String args[]) {
		try {
			Socket soc = new Socket("127.0.0.1", 5050);
			
			InputStream is = soc.getInputStream();
			DataInputStream ds = new DataInputStream(is);
			
			String str = ds.readUTF();
			
			System.out.println("num : " + str);
			
			ds.close();
			is.close();
			soc.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
