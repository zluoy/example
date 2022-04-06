import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Exam2 extends Thread {
	@Override
	public void run() {

		int num;	// 랜덤숫자 
		boolean result = false;
		
		String rand_num = "";
		String rand_dt = "";
		
		System.out.println( Thread.currentThread().getName());
		
		// 소켓통신
		int port = 5050;
		ServerSocket server_Soc;
		
		try {
			Class.forName("org.h2.Driver");
			
			String url = "jdbc:h2:tcp://localhost/~/test";
			String user = "test";
			String pw = "test";
			
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			
			String sql = "";
			
			while(true) {
				System.out.println("1111");
				// DB 연결
				con = DriverManager.getConnection(url, user, pw);
				System.out.println("2222");
				
				stmt = con.createStatement();	// sql 쿼리실행위한 상태 생성
				System.out.println("3333");

				sql = "SELECT RAND_NUM, RAND_DT FROM TEST_DB WHERE ROWNUM = 1";	// SELECT 쿼리

				System.out.println("select sql :: " + sql);

				
				rs = stmt.executeQuery(sql);		// sql 쿼리실행
				
				if(rs.next()) {
					rand_num = rs.getString("RAND_NUM");
					rand_dt = rs.getString("RAND_DT");
					
					System.out.println("rand_num :: " + rand_num);
					System.out.println("rand_dt :: " + rand_dt);
				}
				
				
				try {
					server_Soc = new ServerSocket(port);
					
					Socket soc = server_Soc.accept();
					
					OutputStream os = null;
					DataOutputStream dos = null;
					
					// 데이터전송
					os = soc.getOutputStream();
					dos = new DataOutputStream(os);
				
					dos.writeUTF(rand_num);
					
					dos.close();
					soc.close();
					
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				sql = "DELETE FROM TEST_DB WHERE RAND_NUM = "+ rand_num +" AND TO_CHAR(RAND_DT, 'YYYY-MM-DD HH24:MI:SS') = '" + rand_dt + "'";
				
				System.out.println("sql :: " + sql);
				
				result = stmt.execute(sql);		// sql 쿼리실행
				
				// 쿼리 실행 이후, 자원 반납 ( 안하면 Connection full 에러날 수 있음 ) 
				if(stmt != null) stmt.close();	// DB연결 해
				
				if(con != null) con.close();	//DB연결 해제 
				
				
				try {
					// 1초에 한번
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("0 : " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("222 : " + e1.getMessage());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("333 : " + e1.getMessage());
			e1.printStackTrace();
		}
		
	}	
	
	// socket 
	public static void main(String args[]) throws IOException {
		
	}
}