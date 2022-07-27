package apitester;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

public class AutomationCore extends SeleniumHelper
{
	public static String currentBrowser=null;
	public static String envName=null;
	public static String appURL=null;
	//private static boolean fileStatus=false;
	private static String resultFile=null;
	public static String testName = "";
	public static String testSetName = "";
	public static String testFileAttachmentPath = "";
	protected static Logger log = Logger.getLogger("AutomationCore.class");
	
	public AutomationCore(){
		PropertyConfigurator.configure("C:\\Users\\avykun1\\Desktop\\desktop-old\\Folders\\OMMS-SF\\com.optum.OMMS_SF\\src\\test\\resources\\com\\salesforce\\qa\\CONFIG\\log4j.properties");
	}
	
	/**
	 * This method Loads the config.properties file
	 */
	public static Properties loadProperties()
	{
		
		
		FileInputStream instream = null;
		try {
			instream = new FileInputStream("C:\\Users\\avykun1\\Desktop\\desktop-old\\Folders\\OMMS-SF\\com.optum.OMMS_SF\\src\\test\\resources\\com\\salesforce\\qa\\CONFIG\\config.properties");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(instream!=null)
		{
			Properties prop = new Properties();
			try 
			{
				prop.load(instream);
				return prop;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
			return null;	
	}
	
	/**
	 * This method is used close all opened browsers
	 */
//	public void closeAllOpenedBrowsers()
//	{
//		try
//		{
//			String os = System.getProperty("os.name");
//			if (os.contains("Windows"))
//			{
//					Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
//					SeleniumHelper.threadSleep(500);
//					Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");		
//					SeleniumHelper.threadSleep(500);
//					Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
//					SeleniumHelper.threadSleep(500);
//					//Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
//			}
//		}
//		catch (IOException e)
//		{
//			e.getMessage();
//		}	
//	}
	
//	public void closeAllOpenedDrivers()
//	{
//		try
//		{
//			String os = System.getProperty("os.name");
//			if (os.contains("Windows"))
//			{
//					
//					Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
//			}
//		}
//		catch (IOException e)
//		{
//			e.getMessage();
//		}	
//	}
	/**
	 * This method is used to kill the excel process
	 */
	public void killExcel()
	{
		try
		{
			String os = System.getProperty("os.name");
			if (os.contains("Windows"))
			{
				Runtime.getRuntime().exec("taskkill /IM EXCEL.EXE");
			}
		}
		catch (IOException e)
		{
			e.getMessage();
		}	
	}
	
	/**
	 * This method is used to get the property value from config.properties file.
	 * 
	 * @param propertyName : The name of the property for which user need to retrieve the value
	 * @return the property value for specified property name
	 * 
	 * Ex: Environment("browserName"):- it returns the value of browserName property from config.properties file
	 */
	public static String Environment(String propertyName)
	{
		Properties propValue = loadProperties();		
		return propValue.getProperty(propertyName);
	}
	
	/**
	 * This method is used to read the object properties for each element of specified screen
	 * 
	 * @param screenName The screen name/page name on which the required element/exists is exists
	 * @param objPropName The Property name / property value of the control/element for which selenium is searching for
	 *<p>
	 *Example for calling function :- 
	 *readORProperties("LoginaPgae","userNameXpath"), which returns the xpath value of username filed that is saved in
	 *objectRepositry.xml document
	 */
//	public String readORProperties(String screenName, String objPropName)
//	{
//		try {		
//			 	File fXmlFile = new File(Environment("objectRepoPath"));
//				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//				Document doc = dBuilder.parse(fXmlFile);		 
//				doc.getDocumentElement().normalize();					 
//				NodeList nList = doc.getElementsByTagName(screenName);
//				for (int temp = 0; temp < nList.getLength(); temp++)
//				{			 
//					Node nNode = nList.item(temp);
//					
//					if (nNode.getNodeType() == Node.ELEMENT_NODE) 
//					{			 
//						Element eElement = (Element) nNode;
//						return eElement.getElementsByTagName(objPropName).item(0).getTextContent();					
//			 
//					}
//				}
//		    } 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
//				return null;
//	}
	
	/**
	 * This method is used to read the object properties for each element of specified screen
	 * 
	 * @param screenName The screen name/page name on which the required element/exists is exists
	 * @param objPropName The Property name / property value of the control/element for which selenium is searching for
	 *<p>
	 *Example for calling function :- 
	 *readORProperties("LoginaPgae","userNameXpath"), which returns the xpath value of username filed that is saved in
	 *objectRepositry.xml document
	 */
	/*public String[] readORProperties(String screenName, String objectName)
	{
		try {	
				String[] elementProperties = new String[2];
			 	File fXmlFile = new File(Environment("objectRepoPath"));
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);		 
				doc.getDocumentElement().normalize();					 
				NodeList nList = doc.getElementsByTagName(screenName);
				for (int temp = 0; temp < nList.getLength(); temp++)
				{			 
					Node nNode = nList.item(temp);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) 
					{			 
						Element eElement = (Element) nNode;
						elementProperties[0]=eElement.getElementsByTagName(objectName).item(0).getAttributes().
								getNamedItem("locatorName").getTextContent();
						elementProperties[1]= eElement.getElementsByTagName(objectName).item(0).getTextContent();					
						return elementProperties;
					}
				}
		    } 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
				return null;
	}
	*/
	/**
	 * This method returns the 2 dimensional array object data from "inputData.xls" sheet based on table name
	 * @param tableName It holds the data table name from which the data should be retrieved.
	 * @return an Array[][] object data with rows and columns of data table from input sheet
	 * 
	 * <p>
	 * 
	 * Example: ReadDataFromExcel("TC_01")
	 * <p>
	 * Here TC_01 is table name that is defined in input data sheet
	 */
	
	public Object[][] ReadDataFromExcel(String tableName)
	{
		 ArrayList<String> cellDataList = new ArrayList<String>();
		 ArrayList<String> newCellDataList = new ArrayList<String>();
		 ArrayList<Integer> ignoredRows = new ArrayList<Integer>();
		 int RowCount=0;
		 int columnCount=0;
		 int itr=0;
		// boolean str=false;
		 //boolean num=false;
		 Object[][] data=null;
		 //int firstRowNum=0;
		 int statuscol=0;
		 Cell c=null;
		 int lastCellIndex = 0;
		 int nItr=0;
		 boolean recordsFOund=false;
		
		try
		{
			FileInputStream fileInputStream = new FileInputStream(Environment("testDataFilePath"));
			//Workbook workBook = new XSSFWorkbook(fileInputStream);
			Workbook workBook = WorkbookFactory.create(fileInputStream);
			int namedCellIdx = workBook.getNameIndex(tableName);			
		    org.apache.poi.ss.usermodel.Name aNamedCell = workBook.getNameAt(namedCellIdx);
			AreaReference area = new AreaReference(aNamedCell.getRefersToFormula());
			CellReference[] cellrefs = area.getAllReferencedCells();
			Sheet s = workBook.getSheet(aNamedCell.getSheetName());
			for(int i=0;i<cellrefs.length;i++)
			{				
				Row r = s.getRow(cellrefs[i].getRow());
				/*if(i<r.getLastCellNum() && statuscol==0 )
				{				
					 c= r.getCell(cellrefs[i].getCol());
					 if(c.getStringCellValue().equals("Status"))
					 {
						 statuscol=(c.getColumnIndex())-(r.getFirstCellNum());
						 lastCellIndex= statuscol;
					 }
					 if(i+1==r.getLastCellNum())
						{
							firstRowNum=(r.getLastCellNum()-r.getFirstCellNum());
							i= firstRowNum-1;
						}
					continue;
				}*/
				
				if(statuscol==0)
				{
					statuscol=(r.getLastCellNum()-r.getFirstCellNum())-1;
					lastCellIndex= statuscol;
				}
				c= r.getCell(cellrefs[i].getCol());
				
				switch (c.getCellType()) 
				{
					case XSSFCell.CELL_TYPE_STRING:
						//str =true;
						cellDataList.add(c.getStringCellValue().toString());
						if(c.getStringCellValue().toString().trim().equalsIgnoreCase("No")&&c.getColumnIndex()-r.getFirstCellNum()==statuscol)
						{				
							
							ignoredRows.add(c.getRowIndex());
						}	
					break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if(DateUtil.isCellDateFormatted(c))
						{
							cellDataList.add(c.getDateCellValue().toString());
							break;
						}
						c.setCellType(HSSFCell.CELL_TYPE_STRING);
						cellDataList.add(c.getStringCellValue().toString());
						//num=true;
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						c.setCellType(HSSFCell.CELL_TYPE_STRING);
						cellDataList.add(c.getStringCellValue().toString());
						break;
						
					case XSSFCell.CELL_TYPE_FORMULA:
						try
						{
							//str =true;							
							cellDataList.add(c.getStringCellValue().toString());
							if(c.getStringCellValue().toString().trim().equals("No")&&c.getColumnIndex()==statuscol)
							{				
								
								ignoredRows.add(c.getRowIndex());
							}
						}
						catch(Exception e)
						{						
							if(DateUtil.isCellDateFormatted(c))
							{
								cellDataList.add(c.getDateCellValue().toString());
							}
							else if(e.getMessage().contains("Cannot get a text value from a numeric formula cell"))
							{
								c.setCellType(HSSFCell.CELL_TYPE_STRING);
								cellDataList.add(c.getStringCellValue().toString());
							}
						}	
				}					
				
				if(i==cellrefs.length-1)
				{					
					columnCount=r.getPhysicalNumberOfCells();
					RowCount=(((i+1)/columnCount)-(ignoredRows.size()));
					//RowCount=RowCount-1;
				}				
			}
				workBook.close();
				recordsFOund=true;
			
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			System.out.println("Your test name and table name are not maching:"+tableName);
		}
		
		if(recordsFOund)
		{
			try
			{
				if(cellDataList.size()!=0)
				{
					
					while(itr<cellDataList.size())
					{
						
						if(cellDataList.get(statuscol).equals("Yes"))
						{			
							while(itr<=statuscol)
							{
								if(itr!=statuscol)
								{
									newCellDataList.add(cellDataList.get(itr));
								}
													
								itr++;
							}			
							
						}
						else
						{
							itr=(itr+1)+lastCellIndex;
						}
						
						statuscol=(statuscol+lastCellIndex)+1;
						
					}
				}
				
					if(newCellDataList.size()!=0)
					{
						while(nItr<newCellDataList.size())
						{
							data= new Object[RowCount][columnCount-1];
							for(int i=0;i<RowCount;i++)
							{	
								for (int j=0;j<columnCount-1;j++)
								{
									data[i][j]= newCellDataList.get(nItr);
									nItr++;
								}
							}
						}
					}
					else
					{
						System.out.println("No records are marked as 'Yes' in given table:"+ tableName);
					}		
			}		
			
			catch(Exception e)
			{
				e.getMessage();
				data=null;			
			}			
		}
		else
		{
			System.out.println("No records avialble in given table:"+ tableName);
		}	
			
			return data;
	}
	
	
	/**
	 * This method is used to format the date type value into required date format in string 
	 * @param dateValue The date value that is in other format
	 * @param format Required format type to be formatted
	 * @return Returns the string type date value
	 * <p>
	 * Example:-
	 * <p>
	 * formatDateAndTime(13 dec 2015, "MM_dd_yyyy");
	 */
	public String formatDateAndTime(Date  dateValue, String format)
	{		
		SimpleDateFormat ft =  new SimpleDateFormat(format);	
		String formatedDateValue= ft.format(dateValue);
		return formatedDateValue;
	}
	
	public String formatDateAndTime(String  dateValue, String inputformat, String outputFormat)
	{		
		SimpleDateFormat inputDateFormat =  new SimpleDateFormat(inputformat);	
		SimpleDateFormat outputDateFormat =  new SimpleDateFormat(outputFormat);	
		String formatedDateValue = null;
		Date inputDate=null;
		try 
		{
			inputDate=inputDateFormat.parse(dateValue);			
			formatedDateValue = outputDateFormat.format(inputDate);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatedDateValue;
	}
	
	/**
	 * This method is used to change the current Date format to expected date format
	 * @param dateValue The current date value that is supposed to change the format
	 * @param currentFormat The current format of date value
	 * @param expFormat The expected format that data is supposed to be
	 * @return Returns the a string type date value in expected format
	 * 
	 * <p>
	 * Example :- convertDateFromString(13 dec 2015, "dd MM yyyy"
	 */
	public String convertDateFromString(String  dateValue,String currentFormat, String expFormat)
	{	
		try
		{
			//Locale.setDefault(Locale.US);
			DateFormat ft =  new SimpleDateFormat(currentFormat);	
			DateFormat dft = new SimpleDateFormat(expFormat);
			Date formatedDateValue= (Date)ft.parse(dateValue);
			Date finalDate = (Date)dft.parse(formatedDateValue.toString());
			
			return finalDate.toString();
			
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return null;
		
	}
	
	/**
	 * This method is used to get the current time stamp
	 * @return Current Date 
	 */
	public Date getCurrentDateAndTime()
	{
		Date dNow = new Date( );
		return dNow;
	}
	
	public Date getWeekDayCurrentDateAndTime()
	{
		try
		{
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DAY_OF_WEEK);
			if(day==Calendar.SUNDAY)
			{
				cal.add(Calendar.DATE, -2);
				return cal.getTime();
			}
			else if(day==Calendar.SATURDAY)
			{
				cal.add(Calendar.DATE, -1);
				return cal.getTime();
			}
			else
			{
				return getCurrentDateAndTime();
			}
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		
		return null;
	}
	
	/**
	 * This method is used to get the difference between time values
	 * @param time1 The start time value
	 * @param time2 The end time value
	 * @param formatValue Format type of time to return 
	 * @return Returns the long type time value
	 */
	public long timeDiffInSeconds(String time1, String time2,String formatValue)
	{
		try
		{
			SimpleDateFormat format =  new SimpleDateFormat(formatValue);
			Date date1 = format.parse(time1);
			Date date2 = format.parse(time2);
			long difference = date2.getTime() - date1.getTime();
			difference = (difference) /(1000);  
			return difference;
		}
		catch(Exception e)
		{
			e.getMessage();
			return 0;
		}		
	}
	
	/**
	 * This method is used to create the Results file
	 */
	public String  CreateResultsFile()
	{
		
		if(resultFile!=null)
		{
			return resultFile;
		}
		//String newFile=null;
		 
		try
		{	
			Date currentDate = getCurrentDateAndTime();
			String formatedCurrentDate =formatDateAndTime(currentDate,Environment("resultDateFormat"));
			Thread.sleep(1000);
			resultFile = "Results\\"+Environment("projectName")+"_ResultsNew_"+formatedCurrentDate+".xlsm";
			File f = new File(resultFile);
			if(f.exists())
			{		
				//resultFile=newFile;
				return resultFile;
			}			
			FileInputStream fileInputStream = new FileInputStream(Environment("resultTemplatePath"));			
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);			    
			FileOutputStream fileOut = new FileOutputStream(resultFile);
			workbook.write(fileOut);
			workbook.close();
			fileInputStream.close();
			fileOut.close();
			return resultFile;
			
		}
		catch (Exception e)
		{
			resultFile =null;
		}
		
		return resultFile;
	}
	
	/**
	 * This method is used to create the Results file
	 * @param fileTypeExtn of String type value
	 * @param direcotryPath of String type value
	 * @return Creates a File 
	 */
	
	public File createFile(String fileTypeExtn, String direcotryPath)
	{
		File file =null;
		String fileName="";
		 
		try
		{	
			Date currentDate = getCurrentDateAndTime();
			String formatedCurrentDate =formatDateAndTime(currentDate,Environment("resultDateFormat"));
			Thread.sleep(1000);
			switch (fileTypeExtn) 
			{
			case "xls":
				fileName = direcotryPath+"\\"+Environment("projectName")+"_"+formatedCurrentDate+".xls";
				break;
			case "txt":
				fileName =direcotryPath+"\\"+Environment("projectName")+"_"+formatedCurrentDate+".txt";
				break;
			default:
				fileName = direcotryPath+"\\"+Environment("projectName")+"_"+formatedCurrentDate+".xls";
				break;
			}
			file = new File(fileName);
			if(file.exists())
			{				
				return file;
			}
			else
			{
				 file.createNewFile();
				 return file;
			}
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return file;
	}
	
	/**
	 * This method is used to get Path of the file 
	 * @param file of String type value
	 * @return It returns path of the file 
	 * Example : Seleniumhelper.getAbolutePathofFile(automation.xml)
	 */
	public String getAbolutePathofFile(File file)
	{
		try
		{
			return file.getAbsolutePath();
		}
		catch(Exception e)
		{
			e.getMessage();
			return null;
		}
	}
	
	/**
	 * This method is used to send text into a file 
	 * @param file String type value
	 * @param textToWrite String type value
	 * @return text sent into a file 
	 * Example : Seleniumhelper.writeAndSaveTextFile(Automation.xls,"automation")
	 */
	public boolean writeAndSaveTextFile(File file, String textToWrite)
	{
		try
		{
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(textToWrite);
			output.close();
			return true;
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return false;
		
	}
	

	/**
	 * This method is used to Report the result of each test
	 * @param testID Test script name/Test script ID of string type
	 * @param testName Test scenario objective of string type
	 * @param testDesc Test description objective of string type
	 * @param resultValue Test script result status
	 * @param startTime start time value of test script
	 * @param endTime end time value of test script
	 * @param responseTime response time of total script
	 * <p>
	 * Example:- ReportTestSummary("TC_01","Verify login functionality",
	 * "Verifying login functionality with valid credentials","PASS","12/12/15 10:15","12/12/16 10:30",
	 * 20)
	 */
	public void ReportTestSummary(String testID,String testName,String testDesc,String resultValue,
			String startTime, String endTime, long responseTime)
	{
		String resultFileName="";
		//int itr=0;
		ArrayList<String> columnTexts = new ArrayList<String>();
		try
		{
			resultFileName= CreateResultsFile();			
			FileInputStream fileInputStream = new FileInputStream(resultFileName);
			//Workbook workBook = new XSSFWorkbook(fileInputStream);
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workbook.getSheet("Summary");
			int rowCount = sheet.getPhysicalNumberOfRows();			
			XSSFRow mainrow = sheet.getRow(2);
			int lastColumNumber = mainrow.getLastCellNum();
			for(int i=0; i<lastColumNumber;i++)
			{
				columnTexts.add(mainrow.getCell(i).getStringCellValue());
			}
			XSSFRow newRow = sheet.createRow(rowCount);
			for(int j=0;j<columnTexts.size();j++)
			{
				//Need to add switch cases once java is upgraded to jre1.7 since jre1.6 doesnt support switch type
				if(columnTexts.get(j).toString().equals(Environment("ReportTempField1")))
				{
					newRow.createCell(j).setCellValue(testID);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField2")))
				{
					newRow.createCell(j).setCellValue(testName);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField3")))
				{
					newRow.createCell(j).setCellValue(testDesc);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField4")))
				{
					CellStyle style = workbook.createCellStyle();
					XSSFFont font = workbook.createFont();	
				    if(resultValue.equals("PASS"))
					{
						style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
						style.setFillPattern(CellStyle.SOLID_FOREGROUND);						
						font = workbook.createFont();				       
						font.setColor(IndexedColors.BLACK.getIndex());
						font.setBold(true);
				        style.setFont(font);				        
				        newRow.createCell(j).setCellStyle(style);	
				        newRow.getCell(j).setCellValue(resultValue);
					}
					else
					{
						style.setFillForegroundColor(IndexedColors.RED.getIndex());
						style.setFillPattern(CellStyle.SOLID_FOREGROUND);						       
						font.setColor(IndexedColors.BLACK.getIndex());
						font.setBold(true);
				        style.setFont(font);				        
				        newRow.createCell(j).setCellStyle(style);	
				        newRow.getCell(j).setCellValue(resultValue);
					}
				}
					
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField5")))
				{
					newRow.createCell(j).setCellValue(startTime);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField6")))
				{
					newRow.createCell(j).setCellValue(endTime);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField7")))
				{
					newRow.createCell(j).setCellValue(responseTime);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField8")))
				{
					newRow.createCell(j).setCellValue(getHostName());
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField9")))
				{
					newRow.createCell(j).setCellValue(envName);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField10")))
				{
					try
					{
						CellStyle hlink_style = workbook.createCellStyle();
				        XSSFFont hlink_font = workbook.createFont();			       
				        hlink_font.setColor(IndexedColors.BLUE.getIndex());
				        hlink_font.setBold(true);
				        hlink_font.setItalic(true);			        
				        hlink_style.setFont(hlink_font);
				        CreationHelper createHelper = workbook.getCreationHelper();
				        Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
				        link.setAddress(appURL);
				        newRow.createCell(j).setCellStyle(hlink_style);			        
				        newRow.getCell(j).setCellValue(appURL);
				        newRow.getCell(j).setHyperlink(link);
					}
					catch(Exception e)
					{
						e.getMessage();
						newRow.createCell(j).setCellValue(appURL);
					}
					
			        //newRow.getCell(j).getStringCellValue().
					//newRow.createCell(j).setCellValue(Environment("appURL"));
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField11")))
				{
					newRow.createCell(j).setCellValue(currentBrowser);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempField12")))
				{
					newRow.createCell(j).setCellValue(getUserName());
				}
			}
			FileOutputStream fileOutPutStream = new FileOutputStream(resultFileName);
			workbook.write(fileOutPutStream);
			workbook.close();
			fileInputStream.close();
			fileOutPutStream.close();				
		}
		catch (Exception e)
		{
			e.getMessage();
			
		}		
	}
	
	/**
	 * This  method is used to report the each test step result details
	 * @param testCaseNumber The test Id of string type
	 * @param rowIterationNumber The rowIteration Number of string type
	 * @param stepNumber The step number of string type
	 * @param stepName The step name of string type
	 * @param actualValue The actual result of test step 
	 * @param statusValue The status value of test step 
	 * @param screenShotStatus The screenshot status (Yes/No) if screenshot need Yes otherwise No
	 * @param Driver Page driver of webDriver type	 * 
	 * <p>
	 * Example :- ReportStepDetails("TC_01",0,1,"Login to home page",
	 * "Login is successful","PASS","Yes",loginPageDriver)
	 */
	public void ReportStepDetails(String testCaseNumber, int rowIterationNumber,int stepNumber,
			String stepName,String stepDetails,String expValue, String actualValue,
			String statusValue,String screenShotStatus,WebDriver Driver)
	{
		String resultFileName="";
		//int itr=0;
		ArrayList<String> columnTexts = new ArrayList<String>();
		try
		{
			resultFileName= CreateResultsFile();			
			FileInputStream fileInputStream = new FileInputStream(resultFileName);
			//Workbook workBook = new XSSFWorkbook(fileInputStream);
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workbook.getSheet("Steps");
			int rowCount = sheet.getPhysicalNumberOfRows();			
			XSSFRow mainrow = sheet.getRow(0);
			int lastColumNumber = mainrow.getLastCellNum();
			for(int i=0; i<lastColumNumber;i++)
			{
				columnTexts.add(mainrow.getCell(i).getStringCellValue());
			}
			XSSFRow newRow = sheet.createRow(rowCount);
			for(int j=0;j<columnTexts.size();j++)
			{
				
				//Need to add switch cases once java is upgraded to jre1.7 since jre1.6 doesnt support switch type
				if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField1")))
				{
					newRow.createCell(j).setCellValue(testCaseNumber);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField2")))
				{
					newRow.createCell(j).setCellValue(rowIterationNumber);					
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField3")))
				{
					newRow.createCell(j).setCellValue(stepNumber);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField4")))
				{
					newRow.createCell(j).setCellValue(stepName);
				}					
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField5")))
				{
					newRow.createCell(j).setCellValue(stepDetails);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField6")))
				{
					newRow.createCell(j).setCellValue(expValue);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField7")))
				{
					newRow.createCell(j).setCellValue(actualValue);
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField8")))
				{
					CellStyle style = workbook.createCellStyle();
					XSSFFont font = workbook.createFont();	
				    if(statusValue.equalsIgnoreCase("PASS"))
					{
						style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
						style.setFillPattern(CellStyle.SOLID_FOREGROUND);						
						font = workbook.createFont();				       
						font.setColor(IndexedColors.BLACK.getIndex());
						font.setBold(true);
				        style.setFont(font);				        
				        newRow.createCell(j).setCellStyle(style);	
				        newRow.getCell(j).setCellValue(statusValue);
					}
					else
					{
						style.setFillForegroundColor(IndexedColors.RED.getIndex());
						style.setFillPattern(CellStyle.SOLID_FOREGROUND);						       
						font.setColor(IndexedColors.BLACK.getIndex());
						font.setBold(true);
				        style.setFont(font);				        
				        newRow.createCell(j).setCellStyle(style);	
				        newRow.getCell(j).setCellValue(statusValue);
					}
				}
				else if(columnTexts.get(j).toString().equals(Environment("ReportTempStepField9")))
				{
					try
					{						
						if(screenShotStatus.equalsIgnoreCase("Yes"))
						{
							String seMinVal=formatDateAndTime(getCurrentDateAndTime(), "mmss");
							takeScreenShot();
							CellStyle hlink_style = workbook.createCellStyle();
					        XSSFFont hlink_font = workbook.createFont();			       
					        hlink_font.setColor(IndexedColors.BLUE.getIndex());
					        hlink_font.setBold(true);
					        hlink_font.setItalic(true);			        
					        hlink_style.setFont(hlink_font);					        
					        newRow.createCell(j).setCellStyle(hlink_style);
					        newRow.getCell(j).setCellValue("file:"+(Environment("screenShotsPath")+"\\"+Environment("projectName")+"\\"+
					        currentBrowser+"\\"+stepName+statusValue+seMinVal+".png"));
					    }													        
				}
				catch (Exception e)
				{
					e.getMessage();					 
				}
			}							
		}//ENd for
			FileOutputStream fileOutPutStream = new FileOutputStream(resultFileName);
			workbook.write(fileOutPutStream);
			workbook.close();
			fileInputStream.close();
			fileOutPutStream.close();
		}
		catch (Exception e)
		{
			e.getMessage();
			
		}		
	}
	
	/**
	 * This method is used to get the machine name
	 * @return Machine name of string type
	 */
	public String getHostName()
	{
		String hostName="Not found";
		try
		{
			hostName= InetAddress.getLocalHost().getHostName().toString();
			return hostName;
		}
		catch(Exception e)
		{
			
		}
		return hostName;
	}
	
	/**
	 * This method is used to get username 
	 * @return Username of string type
	 */
	public String getUserName()
	{
		String userName="Not found";
		try
		{
			userName = System.getProperty("user.name");
			return userName;
		}
		catch(Exception e)
		{
			
		}
		return userName;
	}
	/**
	 * This method is used to generate random string
	 * @param length of Int type
	 * @param strType of string type
	 * @return Generate random string according to length and type 
	 * Example: Seleniumhelper.generateRandomString(4,"AB")
	 */
	public String generateRandomString(int length, String strType)
	{
		StringBuffer buffer = new StringBuffer();
		String characters = "";

		if(strType.equalsIgnoreCase("AB"))
		{
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		}
		else if(strType.equalsIgnoreCase("N"))
		{
			characters = "1234567890";
		}
		else if(strType.equalsIgnoreCase("AN"))
		{
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		}
		
		int charactersLength = characters.length();

		for (int i = 0; i < length; i++) 
		{
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		return buffer.toString().toUpperCase();
	}
	
	
	
	
	/**
	 * This method is used to generate random integer
	 * @param minRange of Int type
	 * @param maxRange of Int type
	 * @return Generate random Int according to minRange and maxRange 
	 * Example: Seleniumhelper.generateRandomInteger(1,5)
	 */
	public double generateRandomInteger(int minRange, int maxRange)
	{
		int num=-1;
		try
		{
			num=maxRange-minRange;
			return minRange+(Math.random()*num);
		}
		catch (Exception e)
		{
			e.getMessage();
		}
			return -1;
	}

	/**
	 * This method is get JVM version
	 * @return JVMversion  of Int type
	 */
	public static String getJVMVersion()
	{
		String jvmBitVersion="";
		try
		{
			jvmBitVersion=System.getProperty("sun.arch.data.model");
			//System.out.println(jvmBitVersion);
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return jvmBitVersion;
	}
	
	public static String getDriverPath()
	{
		File f;
		try
		{
			switch (currentBrowser.toLowerCase()) 
			{
			case "ie":
				f = new File("Drivers", "IEDriverServer_Win32_2.47.0\\IEDriverServer.exe");
				return f.getAbsolutePath().replaceAll("Phycon.Automation.Tests", "Phycon.Automation.Core");
			case "chrome":
				f = new File("Drivers", "chromedriver_win32\\chromedriver.exe");
				return f.getAbsolutePath().replaceAll("Phycon.Automation.Tests", "Phycon.Automation.Core");

			default:
				f = new File("Drivers", "IEDriverServer_Win32_2.47.0\\IEDriverServer.exe");
				return f.getAbsolutePath().replaceAll("Phycon.Automation.Tests", "Phycon.Automation.Core");
			}
			
		}
		catch(Exception e)
		{
			return null;
		}
	}



}
