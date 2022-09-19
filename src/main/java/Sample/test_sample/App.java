package Sample.test_sample;




public class App {
	
	
	public static void main(String[] args) {
		
		try {
			test.login_MFA("CHROME");
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Check the credentials and Re-execute");
		}
		
	}

	

}
