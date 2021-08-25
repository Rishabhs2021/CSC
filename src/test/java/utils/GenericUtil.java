package utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class GenericUtil {

	public static String getCurrTimeStamp(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("PST"));
		String datetime = sdf.format(date);
		return datetime;
	}

	public static double roundTwoDecimals(double d) {
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(d));
	}

	public static String getUniqueUUID() {
		return UUID.randomUUID().toString();
	}

	public static String getUniqueAlphabetic(int countOfDigit) {
		return RandomStringUtils.randomAlphabetic(countOfDigit);
	}

	public static String getUniqueAlphanumeric(int numOfDigits) {
		return RandomStringUtils.randomAlphanumeric(numOfDigits);
	}

	public static String getUniqueAlphanumericEmail(int numOfDigits) {
		return RandomStringUtils.randomAlphanumeric(numOfDigits);
	}

	public static String getUniqueNumeric(int numOfDigits) {
		return RandomStringUtils.randomNumeric(numOfDigits);
	}

	public static String getBase64EncodedStr() {
		String base64EncodedStr = "";
		try {
			base64EncodedStr = Base64.getEncoder()
					.encodeToString(RandomStringUtils.randomAlphabetic(26).getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return base64EncodedStr;
	}

	public static synchronized String generateEmail(String domain, int length) {
		String email = "bi_" + System.nanoTime() + "@" + domain;
		return email;
	}

	protected static String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}
	
	public static String getUniqString(int length) {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 3) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	public static String generateInvalidEmail(String domain, int length) {
		String email = "biatInvalidEmailTest" + getCurrTimeStamp("ddMMMYY_H_m_s") + domain;
		return email;

	}

	public static String generatePreviewSanityEmail(String domain, int length) {
		String email = "previewbiattest" + getCurrTimeStamp("ddMMMYY_H_m_s") + "@" + domain;
		return email;
	}

	public static String generateProdSanityEmail(String domain, int length) {
		String email = "prodbiattest" + getCurrTimeStamp("ddMMMYY_H_m_s") + "@" + domain;
		return email;
	}

	public static String generateAdvocacyCampName() {
		String email = "BICamp" + getCurrTimeStamp("ddMMMYY_H_m_s");
		return email;
	}

	public static String generateAdvocacyCampCode() {
		String email = "BICode" + getCurrTimeStamp("ddMMMYY_H_m_s");
		return email;
	}

	public static String generateRewardName() {
		String email = "Test" + getCurrTimeStamp("ddMMMYY_H_m_s");
		return email;
	}
	
	public static boolean checkCampIdInArray(Object campId, ArrayList expectedCampIds) {
		System.out.println("Camp Id" + expectedCampIds);
		boolean campaignFound = false;
		for (Object cId : expectedCampIds) {
			if (cId.equals(campId)) {
				campaignFound = true;
				break;
			}
			else {
				campaignFound = false;
			}
		}
		return campaignFound;
	}
	


	public static String getCurrTimePST() {
		// 2020-12-31T20:04:46.502Z
		Date today = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.HOUR, 12);
		today = cal.getTime();

		df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
		String PST = df.format(today);

		System.out.println("Date in PST Timezone : " + PST);

		return PST;
	}

}
