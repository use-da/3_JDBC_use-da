package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Employee;

public class JDBCExample5 {

	public static void main(String[] args) {
		
		// 입사일을 입력("2022-09-06") 받아 입력 받은 값보다 먼저 입사한 사람의 이름, 입사일, 성별(M/F) 조회
		
		Scanner sc=new Scanner(System.in);
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
			System.out.print("입사일 입력(YYYY-MM-DD) : ");
			String input=sc.next();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url="jdbc:oracle:thin:@localhost:1521:XE";
			
			String user="lyh";
			
			String pw="kh1234";
			
			conn=DriverManager.getConnection(url,user,pw);
			
			String sql="SELECT EMP_NAME 이름, "
					+ "	TO_CHAR(HIRE_DATE, 'YYYY\"년\" MM\"월\" DD\"일\"') 입사일,"
					+ "	DECODE(SUBSTR(EMP_NO,8,1), '1','M','2','F') 성별 "
					+ " FROM EMPLOYEE"
					+ " WHERE HIRE_DATE < TO_DATE('"+input+"')";
			//문자열 내부에 쌍따옴표 작성 시 \"로 작성해야한다
			
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery(sql);
			
			List<Employee>list=new ArrayList<>();
			
			while(rs.next()) {
				
				Employee emp=new Employee();  //기본 생성자로 Employee객체 생성
											  //필드 초기화 x -> setter를 이용해 세팅
				emp.setEmpName(rs.getString("이름"));     //조회 시 컬럼명 "이름"
				emp.setHireDate(rs.getString("입사일"));   //조회 시 컬럼명 "입사일"
				//현재 행의 "입사일" 컬럼의 문자열 값을 얻어와 emp가 참조하는 객체의 hireDate필드에 세팅
				
				emp.setGender(rs.getString("성별").charAt(0));
				//->char 자료형 매개변수가 필요
				//Java의 char : 문자 1개 의미
				//DB의 char : 고정 길이 문자열(==문자열)
				
				//DB컬럼 값을 char 자료형에 저장하고 싶으면 String.charAt(index)이용
				
				//list에 emp객체 추가
				list.add(emp);
				
			}//while문 끝
			
			//조회결과가 없는 경우
			if(list.size()==0) {
				//isEmpty
				System.out.println("조회 결과가 없습니다");
				
			}else {
				for(int i=0;i<list.size();i++) {
					System.out.printf("%02d) %s / %s / %c\n",i+1,list.get(i).getEmpName(),
							list.get(i).getHireDate(),list.get(i).getGender());
				}

			}
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(conn!=null)conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
