package apitester;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class APITests {

	public static String  strInputFilePath = System.getProperty("user.dir")+"//TestData//API_Tests_InputSheet.xls";
	static String sessionID;
	static String params;
	String[] arrl;
	static RequestSpecification request1;	
	@SuppressWarnings("unchecked")
	
	@BeforeClass
	public void captureSessionID()
	{
		try
		{
			
			RestAssured.baseURI = "https://proview.caqh.org/credentialingapi/api/v3/";
			RequestSpecification request = RestAssured.given();			
			//RequestSpecification httpRequest = RestAssured.given();			
			 JSONObject requestParams = new JSONObject();	
			 
			 requestParams.put("caqhProviderId",11336247);
			 requestParams.put("organizationId",225);
			  request.body(requestParams.toJSONString());
				 
			     Response response = request.post("/entities?");
				 int statusCode = response.getStatusCode();
				sessionID = response.jsonPath().get("sessionId");
				System.out.println(statusCode + ":"+ sessionID);
				fnwriteExcel(strInputFilePath, 0, 1, sessionID);
				
				
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	@Test(priority = 0)
public void TC_01_PracticeAccounts()
	{
	
		System.out.println("TEst");
}
	@SuppressWarnings("unchecked")
	@Test(priority = 0)
	public void TC_01_PracticeAccounts()
	{
		try
		{
						
			 //Practice accounts
			 RestAssured.baseURI = "https://briovarx-provider-portal-dev.herokuapp.com";
			 request1 = RestAssured.given();				    
			 request1.header("sessionId", sessionID);
			 request1.header("Content-Type","application/json");		    
			 int row=1;
			 while (fnreadExcel(strInputFilePath,1,row)!="") 
			 {
			   JSONObject requestParams1 = new JSONObject();
			   params = fnreadExcel(strInputFilePath,1,row);
			   arrl = params.replace("{", "").replace("}", "").replace("\n","").replace("\"", "").split(",");
			   for (String item : arrl) 
			   {
					 String[] arr2 = item.split(":");
					 String a = arr2[0].trim();
					 String b = "";
					 if(arr2.length==1)
					    b = "";
					   else
						   b = arr2[1].trim();
					   
					   requestParams1.put(a,b);
				   }
					request1.body(requestParams1.toJSONString());						 
					Response response1 = request1.post("/practiceAccounts");
					int statusCode1 = response1.getStatusCode();							
					Object resmsg = response1.jsonPath().get("practiceAccounts");
					System.out.println(statusCode1+":"+ resmsg.toString());
					fnwriteExcel(strInputFilePath, 1, row, resmsg.toString());
					row=row+1;
			   }
		}
				  
			 catch (Exception e)
				{
					e.printStackTrace();
				}
		}
		
	@SuppressWarnings("unchecked")
	@Test(priority = 1)
	public void TC_02_AccountManager()
	{
		try
		{
					
			 RestAssured.baseURI = "https://briovarx-provider-portal-dev.herokuapp.com";
			 request1 = RestAssured.given();				    
			 request1.header("sessionId", sessionID);
			 request1.header("Content-Type","application/json");		    
			 int row=1;
			 while (fnreadExcel(strInputFilePath,2,row)!="") 
			 {
				 JSONObject requestParams1 = new JSONObject();
				 params = fnreadExcel(strInputFilePath,2,row);
				 arrl = params.replace("{", "").replace("}", "").replace("\n","").replace("\"", "").split(",");
				 for (String item : arrl) 
				 {
					 String[] arr2 = item.split(":");
					 String a = arr2[0].trim();
					 String b = "";
					 if(arr2.length==1)
						 b = "";
					 else
						 b = arr2[1].trim();
						 requestParams1.put(a,b);
				 }
				 request1.body(requestParams1.toJSONString());						 
				 Response response1 = request1.post("/accountManager");
				 int statusCode1 = response1.getStatusCode();							
				 Object resmsg = response1.jsonPath().get("listAccountManager");				
				 System.out.println(statusCode1+":"+ resmsg.toString());
				 fnwriteExcel(strInputFilePath, 2, row, resmsg.toString());
				 row=row+1;
			 }	   
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test(priority = 2)
	public void TC_03_PatientForPrescriber()
	{
		try
		{
			//PatientForPrescriber
			 RestAssured.baseURI = "https://briovarx-provider-portal-dev.herokuapp.com";
			 request1 = RestAssured.given();				    
			 request1.header("sessionId", sessionID);
			 request1.header("Content-Type","application/json");		    
			 int row=1;
			 
			 while (fnreadExcel(strInputFilePath,3,row)!="") 
			 {
				  
				 params = fnreadExcel(strInputFilePath,3,row);
				 JSONParser parser = new JSONParser();
				 JSONObject requestParams1 = (JSONObject) parser.parse(params);				      
				 request1.body(requestParams1.toJSONString());						 
				 Response response1 = request1.post("/patientPrescriber");
				 int statusCode1 = response1.getStatusCode();							
				 Object resmsg = response1.jsonPath().get();
				 System.out.println(statusCode1+":"+ resmsg.toString());
				 fnwriteExcel(strInputFilePath, 3, row, resmsg.toString());
				 row=row+1;
			   }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test(priority = 3)
	public void TC_04_ContactsAPI()
	{
		try
		{
			 
		   //Contacts API
		     
			 RestAssured.baseURI = "https://briovarx-provider-portal-dev.herokuapp.com";
			 request1 = RestAssured.given();				    
			 request1.header("sessionId", sessionID);
			 request1.header("Content-Type","application/json");		    
			 int row=1;
			   while (fnreadExcel(strInputFilePath,4,row)!="") 
			   {
				   JSONObject requestParams1 = new JSONObject();
				   params = fnreadExcel(strInputFilePath,4,row);
				   arrl = params.replace("{", "").replace("}", "").replace("\n","").replace("\"", "").split(",");
					 for (String item : arrl) 
					 {
						   String[] arr2 = item.split(":");
						   String a = arr2[0].trim();
						   String b = "";
						   if(arr2.length==1)
							    b = "";
						   else
							   b = arr2[1].trim();
						   
						   requestParams1.put(a,b);
					   }
					 request1.body(requestParams1.toJSONString());						 
					 Response response1 = request1.post("/contacts");
					 int statusCode1 = response1.getStatusCode();							
				     Object resmsg = response1.jsonPath().get();
				    
					System.out.println(statusCode1+":"+ resmsg.toString());
					fnwriteExcel(strInputFilePath, 4, row, resmsg.toString());
					row=row+1;
			   }
							   
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
				

	}
	
	public static String fnreadExcel(String fileName, int sheetid, int row1) 
	{
		  try
	        {
	            FileInputStream file = new FileInputStream(new File(fileName));	 
	           //Create Workbook instance holding reference to .xlsx file
	            HSSFWorkbook workbook  = new HSSFWorkbook(new POIFSFileSystem(file));	 
	            //Get first/desired sheet from the workbook
	            HSSFSheet sheet = workbook.getSheetAt(sheetid);
	            Cell cell = sheet.getRow(row1).getCell(1);
	            String cellval = cell.getStringCellValue();            
	            file.close();	
	            workbook.close();
	            return cellval;
	        }
		    catch(FileNotFoundException e)
			  {
			    	e.printStackTrace();
				    return "";	
			  }
	        catch (Exception e)
	        {
	            return "";
	        }
	}
	
	public static void fnwriteExcel(String fileName, int sheetid, int row1,String valuetowrite)
	{
		try
		{
			FileInputStream file = new FileInputStream(new File(fileName));	 
			
			HSSFWorkbook workbook  = new HSSFWorkbook(file);	 
			HSSFSheet sheet = workbook.getSheetAt(sheetid);
			HSSFRow row = sheet.getRow(row1); 
			Cell name = row.createCell(2); 
			name.setCellValue(valuetowrite);
			file.close();
			
			workbook.write(new FileOutputStream(new File(fileName)));
	        workbook.close();
		
		}
		catch (Exception e)
        {
            e.printStackTrace();
           
        }
		
		
		
	}
	
}