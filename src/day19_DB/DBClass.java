package day19_DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// DB에 관련된 코드만 처리하는 클래스
public class DBClass {
	private String url;
	private String id;
	private String pwd;
	private Connection con;
	// 여러종류의 Connection 중 sql에 있는 것으로 import 함
	
	public DBClass() {	// 생성자 생성
		// 오라클의 기능을 자바에서 사용하기 위해 무조건 처음 실행되게 하기 위해
		try {
		// 자바에서 오라클에 연결할 수 있게 도와주는 라이브러리를 등록하는 것
		Class.forName("oracle.jdbc.driver.OracleDriver");
		url = "jdbc:oracle:thin:@localhost:1521:xe";
		// 1521 = 오라클이 사용하는 포트번호
		// xe = 오라클 버전
		id = "dydgns2446";
		pwd = "evan9396";
		con = DriverManager.getConnection(url, id, pwd);
		System.out.println(con);
		// 위에 객체정보가 출력이 되면 오라클에 연결 성공
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	// 순서
	// 1. 드라이브 로드 (오라클 기능 사용)
	//	Class.forName("oracle.jdbc.driver.OracleDriver");
	//	url = "jdbc:oracle:thin:@localhost:1521:xe";
	// 										1521 = 오라클이 사용하는 포트번호
	// 											xe = 오라클 버전
	// 	id = "sql아이디";
	//	pwd = "sql비밀번호";
	
	// 2. 연결된 객체를 얻어오기
	//	Connection con = DriverManager.getConnection(url, id, pwd);
	
	// 3. 연결된 객체를 이용해서 명령어(쿼리문)을 전송할 수 있는 전송 객체를 얻어오기
	//	String sql = "쿼리 명령문 입력"
	//	PreparedStatement ps = con.prepareStatement(sql);
	
	// 4. 전송 객체를 이용해서 데이터베이스에 전송 후 결과를 얻어와서 int 또는 ResultSet으로 받는다
	//	ResultSet rs = ps.executeQuery();
	
	
	public ArrayList<StudentDTO> getUsers(){
		ArrayList<StudentDTO> list = new ArrayList<StudentDTO>();
		String sql = "select * from newst";
		try {			
			PreparedStatement ps = con.prepareStatement(sql);
			// 3번 참고
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				StudentDTO dto = new StudentDTO();
				dto.setStNum(rs.getString("id"));	//"컴럼명"
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
				list.add(dto);	// 다시 list 어레이리스트에 저장
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int saveData(String stNum, String name, int age) {
		// insert into newst values('111','홍길동','20');
		String sql = "insert into newst values('"+stNum+"','"+name+"',"+age+")";
		int result = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			//ResultSet rs = ps.executeQuery();
			result = ps.executeUpdate();
			// 저장 성공 시 1을 반환, 실패 시 catch로 이동하거나 0을 반환
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	// 위의 saveData()보다 간단한 방법으로 만든 클래스
	public int saveData02(String stNum, String name, int age) {
		// insert into newst values('111','홍길동','20');
		String sql = "insert into newst values(?,?,?)";
		// sql명령어를 적고 그걸 sql변수에 대입
		int result = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, stNum);
			ps.setString(2, name);
			ps.setInt(3, age);
			result = ps.executeUpdate();
			// 저장 성공 시 1을 반환, 실패 시 catch로 이동하거나 0을 반환
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int delete(String userNum) {
		int result = 0;
		//delete from newst where id = 'userNum';
		String sql = "delete from newst where id = '"+userNum+"'";
		//String sql = "delete from newst where id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			result = ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int modify(String stNum,String name,int age) {
		int result =0;
		//update newst set name='홍길동', age=20 where id='test';
		String sql = "update newst set name=?, age=? where id=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, stNum);
			result = ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
}
