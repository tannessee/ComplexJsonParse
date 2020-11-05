import static org.testng.Assert.assertEquals;

import files.Payload;
import io.restassured.RestAssured;

import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		JsonPath js = new JsonPath(Payload.CoursePrice()); //  mock API не дожидаясь девелоперов
															// (Agile)
		// NUmber of courses returned by API
		int count = js.getInt("courses.size()"); // сколько подэлементов в courses
		System.out.println("Number of fields in courses : " + count);

		// Get number of purchased courses
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchased amount of courses : " + purchaseAmount);

		// Print titlw of the first course
		String title1 = js.get("courses[0].title"); // get by default =getString
		System.out.println("Title of the first course :" + title1);

		// Print all courses in their respective titles
		for (int i = 0; i < count; i++) {
			String t = js.get("courses[" + i + "].title");
			int price = js.get("courses[" + i + "].price");
			System.out.println("The course " + t + " costs " + price + "$");

		}

		// Print number of courses sold by RPA course
		for (int i = 0; i < count; i++) {
			String t = js.get("courses[" + i + "].title");
			if (t.equalsIgnoreCase("RPA")) {
				System.out.println("Number of copies sold by RPA : " + js.get("courses[" + i + "].copies"));
				break;
			}

		}
		// Verify if Sum of all courses prices matches with purchaseAmount
		int sum = 0;
		for (int i = 0; i < count; i++) {
			int mult = js.getInt("courses[" + i + "].price") * js.getInt("courses[" + i + "].copies");
			sum = mult + sum;
		}
		if (sum == (js.getInt("dashboard.purchaseAmount"))) {
			System.out.println("Sum of all courses prices matches with purchaseAmount");
		} else
			System.out.println("Sum of all courses prices DOESN'T match with purchaseAmount");

	}

}
