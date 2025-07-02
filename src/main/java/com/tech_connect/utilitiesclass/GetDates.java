package com.tech_connect.utilitiesclass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebElement;

public class GetDates {

	public static String dates(int day) {

		Date date = new Date();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		sim.format(date);

		Calendar cal = sim.getCalendar();
		cal.add(Calendar.DAY_OF_MONTH, day);
		String select_date = sim.format(cal.getTime());

		return select_date;
	}

	public static String date1(String format, int day) {

		Date date = new Date();
		SimpleDateFormat sim = new SimpleDateFormat(format);
		sim.format(date);

		Calendar cal = sim.getCalendar();
		cal.add(Calendar.DAY_OF_MONTH, day);
		String select_date = sim.format(cal.getTime());

		return select_date;
	}
//select date from the calendar
	public static void selectDate(WebElement monthElem, WebElement nextButton, List<WebElement> dateElements,
			String expectedMonth, String day)

	{

		while (true) {
			String currentMonth = monthElem.getText();

			if (currentMonth.equalsIgnoreCase(expectedMonth)) {
				break;
			}

			nextButton.click(); // keep clicking next until the desired month-year appears
		}

		for (WebElement dt : dateElements) {
			if (dt.getText().equals(day)) {
				dt.click();
				break;
			}
		}
	}

}
