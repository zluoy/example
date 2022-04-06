import java.util.Random;
import java.sql.*;

public class Exam {
	public static void main(String args[]) {
		Thread select_Thread = new Exam2();
		
		
		Thread insert_Thread = new Thread() {
			public void run() {
				int num;	// 랜덤숫자 
				boolean result = false; // 쿼리 insert 성공 여부
				
				Random random = new Random();

				System.out.println( Thread.currentThread().getName());
				
				try {
					Class.forName("org.h2.Driver");
					
					String url = "jdbc:h2:tcp://localhost/~/test";
					String user = "test";
					String pw = "test";
					
					Connection con = null;
					
					Statement stmt = null;
					
					String sql = "";
					
					while(true) {
						
						num = random.nextInt();	// 랜덤으로 숫자생성
					
						// DB 연결
						con = DriverManager.getConnection(url, user, pw);
						stmt = con.createStatement();	// sql 쿼리실행위한 상태 생성
						
						sql = "INSERT INTO TEST_DB VALUES ("+ num+ ", SYSDATE)";	// insert 쿼리
						
						//System.out.println("sql :: " + sql);
						
						//result = stmt.execute(sql);		// sql 쿼리실행
						
						//System.out.println("result :: " + result);
						
						// 쿼리 실행 이후, 자원 반납 ( 안하면 Connection full 에러날 수 있음 ) 
						if(stmt != null) stmt.close();	// DB연결 해
						
						if(con != null) con.close();	//DB연결 해제 
						
						try {
							// 0.1초에 한번
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}	
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
		
		insert_Thread.setName("insert thread ::");
		select_Thread.setName("select thread ::");
		
		insert_Thread.start();
		select_Thread.start();
	}	
	
}
