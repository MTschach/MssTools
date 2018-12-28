package de.mss.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import de.mss.utils.exception.MssException;
import junit.framework.TestCase;

public class DateTimeToolsTest extends TestCase {

   @SuppressWarnings("unused")
   @Test
   public void testDateTimeTools() {
      try {
         new DateTimeTools();
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 1, e.getError().getErrorCode());
      }
   }


   @Test
   public void testParseString2Date() throws MssException {
      try {
         DateTimeTools.parseString2Date(null);
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode for null", 3, e.getError().getErrorCode());
      }

      try {
         DateTimeTools.parseString2Date("");
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode for ''", 3, e.getError().getErrorCode());
      }

      try {
         DateTimeTools.parseString2Date("bla");
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode f�r 'bla'", 3, e.getError().getErrorCode());
      }


      Date d = DateTimeTools.parseString2Date("2018-12-11T17:15:35 +0100");
      checkDate(d, true, true, false);

      d = DateTimeTools.parseString2Date("2018-12-11 17:15:35.128");
      checkDate(d, true, true, true);

      d = DateTimeTools.parseString2Date("2018-12-11 05:15:35 pm");
      checkDate(d, true, true, false);

      d = DateTimeTools.parseString2Date("2018-12-11 17:15:35");
      checkDate(d, true, true, false);

      d = DateTimeTools.parseString2Date("11.12.2018 17:15:35");
      checkDate(d, true, true, false);

      d = DateTimeTools.parseString2Date("2018-12-11 05:15 pm");
      checkDate(d, true, false, false);

      d = DateTimeTools.parseString2Date("11.12.2018 17:15");
      checkDate(d, true, false, false);

      d = DateTimeTools.parseString2Date("2018-12-11");
      checkDate(d, false, false, false);

      d = DateTimeTools.parseString2Date("11.12.2018");
      checkDate(d, false, false, false);
   }


   @Test
   public void testFormatDate() throws MssException {
      try {
         DateTimeTools.formatDate(null, "dd.MM.yyyy");
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 2, e.getError().getErrorCode());
      }
      Date d = DateTimeTools.parseString2Date("2018-12-11 17:15:35.128");

      assertEquals("11.12.2018", DateTimeTools.formatDate(d, "dd.MM.yyyy"));

      assertEquals("11.12.2018", DateTimeTools.formatDate(d, DateTimeFormat.DATE_FORMAT_DE));
      assertEquals("2018-12-11", DateTimeTools.formatDate(d, DateTimeFormat.DATE_FORMAT_EN));
      assertEquals("2018-12-11 17:15:35", DateTimeTools.formatDate(d, DateTimeFormat.DATE_TIME_FORMAT_DB));
      assertEquals("11.12.2018 17:15", DateTimeTools.formatDate(d, DateTimeFormat.DATE_TIME_FORMAT_DE));
      assertEquals("2018-12-11 05:15 PM", DateTimeTools.formatDate(d, DateTimeFormat.DATE_TIME_FORMAT_EN));
      assertEquals("2018-12-11 17:15:35.128", DateTimeTools.formatDate(d, DateTimeFormat.DATE_TIMESTAMP_FORMAT_DB));
      assertEquals("11.12.2018 17:15:35", DateTimeTools.formatDate(d, DateTimeFormat.DATE_TIMESTAMP_FORMAT_DE));
      assertEquals("2018-12-11 05:15:35 PM", DateTimeTools.formatDate(d, DateTimeFormat.DATE_TIMESTAMP_FORMAT_EN));
      assertEquals("2018-12-11T17:15:35 +0100", DateTimeTools.formatDate(d, DateTimeFormat.DATE_TIMESTAMP_FORMAT_UTC));
   }


   @Test
   public void testIsSameDay() throws MssException {
      try {
         DateTimeTools.isSameDay(null, null);
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 2, e.getError().getErrorCode());
      }

      java.util.Date today = DateTimeTools.getActDate();

      assertFalse("Date1 is null", DateTimeTools.isSameDay(null, today));
      assertFalse("Date2 is null", DateTimeTools.isSameDay(today, null));

      assertTrue("Today", DateTimeTools.isSameDay(today, DateTimeTools.getActDate()));
      assertFalse("Yesterday", DateTimeTools.isSameDay(today, DateTimeTools.getYesterdayDate()));
      assertFalse("Tomorrow", DateTimeTools.isSameDay(today, DateTimeTools.getTomorrowDate()));

      today = DateTimeTools.parseString2Date("2018-12-11");

      assertFalse("different year", DateTimeTools.isSameDay(today, DateTimeTools.parseString2Date("2017-12-11")));
      assertFalse("different month", DateTimeTools.isSameDay(today, DateTimeTools.parseString2Date("2018-11-11")));
      assertFalse("different day", DateTimeTools.isSameDay(today, DateTimeTools.parseString2Date("2018-12-12")));
   }


   @Test
   public void testGetMonday() throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      java.util.Date monday = DateTimeTools.getMondayDate();

      switch (gc.get(Calendar.DAY_OF_WEEK)) {
         case Calendar.MONDAY:
            break;
         case Calendar.TUESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 1);
            break;
         case Calendar.WEDNESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 2);
            break;
         case Calendar.THURSDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 3);
            break;
         case Calendar.FRIDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 4);
            break;
         case Calendar.SATURDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 5);
            break;
         case Calendar.SUNDAY:
         default:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 6);
            break;
      }

      assertTrue(DateTimeTools.isSameDay(monday, gc.getTime()));
   }


   @Test
   public void testGetTuesday() throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      java.util.Date monday = DateTimeTools.getTuesdayDate();

      switch (gc.get(Calendar.DAY_OF_WEEK)) {
         case Calendar.MONDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 1);
            break;
         case Calendar.TUESDAY:
            break;
         case Calendar.WEDNESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 1);
            break;
         case Calendar.THURSDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 2);
            break;
         case Calendar.FRIDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 3);
            break;
         case Calendar.SATURDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 4);
            break;
         case Calendar.SUNDAY:
         default:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 5);
            break;
      }

      assertTrue(DateTimeTools.isSameDay(monday, gc.getTime()));
   }


   @Test
   public void testGetWednesday() throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      java.util.Date monday = DateTimeTools.getWednesdayDate();

      switch (gc.get(Calendar.DAY_OF_WEEK)) {
         case Calendar.MONDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 2);
            break;
         case Calendar.TUESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 1);
            break;
         case Calendar.WEDNESDAY:
            break;
         case Calendar.THURSDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 1);
            break;
         case Calendar.FRIDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 2);
            break;
         case Calendar.SATURDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 3);
            break;
         case Calendar.SUNDAY:
         default:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 4);
            break;
      }

      assertTrue(DateTimeTools.isSameDay(monday, gc.getTime()));
   }


   @Test
   public void testGetThursday() throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      java.util.Date monday = DateTimeTools.getThursdayDate();

      switch (gc.get(Calendar.DAY_OF_WEEK)) {
         case Calendar.MONDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 3);
            break;
         case Calendar.TUESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 2);
            break;
         case Calendar.WEDNESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 1);
            break;
         case Calendar.THURSDAY:
            break;
         case Calendar.FRIDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 1);
            break;
         case Calendar.SATURDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 2);
            break;
         case Calendar.SUNDAY:
         default:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 3);
            break;
      }

      assertTrue(DateTimeTools.isSameDay(monday, gc.getTime()));
   }


   @Test
   public void testGetFriday() throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      java.util.Date monday = DateTimeTools.getFridayDate();

      switch (gc.get(Calendar.DAY_OF_WEEK)) {
         case Calendar.MONDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 4);
            break;
         case Calendar.TUESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 3);
            break;
         case Calendar.WEDNESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 2);
            break;
         case Calendar.THURSDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 1);
            break;
         case Calendar.FRIDAY:
            break;
         case Calendar.SATURDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 1);
            break;
         case Calendar.SUNDAY:
         default:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 2);
            break;
      }

      assertTrue(DateTimeTools.isSameDay(monday, gc.getTime()));
   }


   @Test
   public void testGetSaturday() throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      java.util.Date monday = DateTimeTools.getSaturdayDate();

      switch (gc.get(Calendar.DAY_OF_WEEK)) {
         case Calendar.MONDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 5);
            break;
         case Calendar.TUESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 4);
            break;
         case Calendar.WEDNESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 3);
            break;
         case Calendar.THURSDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 2);
            break;
         case Calendar.FRIDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 1);
            break;
         case Calendar.SATURDAY:
            break;
         case Calendar.SUNDAY:
         default:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) - 1);
            break;
      }

      assertTrue(DateTimeTools.isSameDay(monday, gc.getTime()));
   }


   @Test
   public void testGetSunday() throws MssException {
      GregorianCalendar gc = new GregorianCalendar();

      java.util.Date monday = DateTimeTools.getSundayDate();

      switch (gc.get(Calendar.DAY_OF_WEEK)) {
         case Calendar.MONDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 6);
            break;
         case Calendar.TUESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 5);
            break;
         case Calendar.WEDNESDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 4);
            break;
         case Calendar.THURSDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 3);
            break;
         case Calendar.FRIDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 2);
            break;
         case Calendar.SATURDAY:
            gc.set(Calendar.DAY_OF_WEEK, gc.get(Calendar.DAY_OF_WEEK) + 1);
            break;
         case Calendar.SUNDAY:
         default:
            break;
      }

      assertTrue(DateTimeTools.isSameDay(monday, gc.getTime()));
   }


   @Test
   public void testAddDate() throws MssException {
      java.util.Date tomorrow = DateTimeTools.getTomorrowDate();
      java.util.Date date = DateTimeTools.addDate(new java.util.Date(), 1, Calendar.DAY_OF_MONTH);
      assertTrue("addDate", DateTimeTools.isSameDay(tomorrow, date));

      try {
         DateTimeTools.addDate(null, 1, Calendar.DAY_OF_MONTH);
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 2, e.getError().getErrorCode());
      }

      try {
         DateTimeTools.addDate(new java.util.Date(), 1, 123);
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 3, e.getError().getErrorCode());
      }
   }

   private void checkDate(Date d, boolean withTime, boolean withSecond, boolean withMillisecond) {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(d);
      assertEquals("Year", 2018, gc.get(Calendar.YEAR));
      assertEquals("Month", 11, gc.get(Calendar.MONTH));
      assertEquals("Day", 11, gc.get(Calendar.DAY_OF_MONTH));

      if (withTime) {
         assertEquals("Hour", 17, gc.get(Calendar.HOUR_OF_DAY));
         assertEquals("Minute", 15, gc.get(Calendar.MINUTE));
      }
      if (withSecond) {
         assertEquals("Second", 35, gc.get(Calendar.SECOND));
      }
      if (withMillisecond) {
         assertEquals("Millisecond", 128, gc.get(Calendar.MILLISECOND));
      }
   }

}