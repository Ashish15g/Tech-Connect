package com.tech_connect.utilitiesclass;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
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
	//Date Picker
	private static final Map<String, Month> MONTH_MAP = createMonthMap();

    private static Map<String, Month> createMonthMap() {
        Map<String, Month> map = new HashMap<>();
        for (Month m : Month.values()) {
            map.put(m.name().toLowerCase(), m);
            map.put(capitalize(m.name().toLowerCase()), m);
        }
        return map;
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static Month convertMonth(String month) {
        if (month == null) return null;
        Month result = MONTH_MAP.get(month.trim());
        if (result == null) {
            throw new IllegalArgumentException("Invalid month: " + month);
        }
        return result;
    }

    /**
     * Selects the desired date in the calendar.
     *
     * @param driver WebDriver instance
     * @param calendarPage Page Object representing the calendar
     * @param requiredMonth e.g., "July"
     * @param requiredYear e.g., 2025
     * @param requiredDate e.g., "15"
     */
    public static void selectDatePro(WebElement displayedMonthElement, WebElement nextButton, WebElement previousButton,List<WebElement> allDateElements,
                                  String requiredMonth, int requiredYear, String requiredDate) {
        Month expectedMonth = convertMonth(requiredMonth);

        while (true) {
            // e.g., "July 2025"
            String[] parts = displayedMonthElement.getText().split(" ");
            String currentMonthText = parts[0];
            int currentYear = Integer.parseInt(parts[1]);

            Month currentMonth = convertMonth(currentMonthText);

            if (currentYear < requiredYear || (currentYear == requiredYear && currentMonth.compareTo(expectedMonth) < 0)) {
                nextButton.click();
            } else if (currentYear > requiredYear || (currentYear == requiredYear && currentMonth.compareTo(expectedMonth) > 0)) {
                previousButton.click();
            } else {
                // Year and Month match
                break;
            }
        }

        // Select the date
        for (WebElement dateElement : allDateElements) {
            if (dateElement.getText().equals(requiredDate)) {
                dateElement.click();
                break;
            }
        }
    }

}
