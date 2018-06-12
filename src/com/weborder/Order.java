package com.weborder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Order {
	public static void main(String[] args) throws Throwable {
		List<String> names = new ArrayList<>(100);	
		
		BufferedReader bf = new BufferedReader(new FileReader("MOCK_DATA.csv"));
		//add all names to array list
		for(int i=0; i<100; i++) {
			names.add(bf.readLine());
		}
		
		
		
		
		System.setProperty("webdriver.chrome.driver", "/Users/alya/Documents/selenium dependencies/drivers/"
				+ "chromedriver");
		
		
        WebDriver driver = new ChromeDriver();
        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
		
        
        //log in 
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Tester");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("test");
        driver.findElement(By.cssSelector("Input[type='submit']")).click();
        
        //go to order field
        driver.findElement(By.linkText("Order")).click();
        //generate random quantity 1-100
        Random r1 = new Random();
        int  randomQuantity = 1 + r1.nextInt(100);
       
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).sendKeys(""+ randomQuantity);
        
        
        //get random name
        String randomName ="";
        Random r2 = new Random();
        int randomNameIndex = r2.nextInt(100);
		for(int i=0; i<names.size(); i++) {
			if(i==randomNameIndex)
				randomName = names.get(i);
		
		}
        //set values for fields
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtName")).sendKeys("John " + randomName + " Smith");
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox2")).sendKeys("123 Any st");
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox3")).sendKeys("Pittsburgh");
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox4")).sendKeys("PA");
        //set random zip code
        Random r3 = new Random();
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox5")).sendKeys(""+(int)Math.floor(Math.random()*100000));
         /**get a random card type using random numbers from 0 to 2
          * where 0 - VISA
          * 1 - master card
          * 2 - American Express
          */
        Random r4 = new Random();
      int cardIndex = r4.nextInt(3) ;
      driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_" + cardIndex)).click(); ;
       
       switch (cardIndex) {
	case 0:
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys("4765345678901234");
		break;
	case 1:
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys("5876567843215678");
		break;
	case 2: 
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys("3098345623457654");
		break;
	default:
		break;
	} 
       
        //set expiration date
       driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox1")).sendKeys("10/22");
       //click Process
       driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();
     //Verify if order has been added
       String expected = "New order has been successfully added.";
       // Get text "New odder has been successfully added." as a result of submitting an order
       String actual = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_fmwOrder\"]/tbody/tr/td/div/strong")).getText();
       
       if(expected.equalsIgnoreCase(actual)) {
    	   System.out.println("Pass");
       }else {
    	   System.out.println("Failed");
       }
        
	}

}
