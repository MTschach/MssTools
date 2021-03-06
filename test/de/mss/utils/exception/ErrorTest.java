package de.mss.utils.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ErrorTest {

   @Test
   public void testError() {
      final Error e = new Error(1, "Testfehler", Integer.valueOf(531));

      assertEquals("ErrorCode", 1, e.getErrorCode());
      assertEquals("ErrorText", "Testfehler", e.getErrorText());
      assertEquals("toString", "Error : 1(Testfehler)", e.toString());
      assertEquals("StatusCode", Integer.valueOf(531), e.getStatusCode());
   }


   @Test
   public void testErrorEquals() {
      final Error e1 = new Error(1, "Testfehler");
      final Error e2 = new Error(1, "Testfehler");

      assertTrue("Equals", e1.equals(e2));

      assertFalse("False null", e1.equals(null));
      assertFalse("False Code", e1.equals(new Error(2, "Testfehler2")));
   }
}
