package ArrayStack;


import junit.framework.TestCase;

public class VMLimitsTest extends TestCase {
  int depth = 0;
  
  public void testChop() { 
    // -Xmx=256m
    //assertEquals(33554433, between(1, Integer.MAX_VALUE));
    // -Xmx=512m
    //assertEquals(44691133, between(1, Integer.MAX_VALUE));
    // -Xmx=1024m
    //assertEquals(178908861, between(1, Integer.MAX_VALUE));
    // -Xmx=2048m
    //assertEquals(357871293, between(1, Integer.MAX_VALUE));
    // -Xmx=2000m
    //assertEquals(349482685, between(1, Integer.MAX_VALUE));
    // -Xmx=1900m
    //assertEquals(332000957, between(1, Integer.MAX_VALUE));
    // -Xmx=1950m
    //assertEquals(340733629, between(1, Integer.MAX_VALUE));
    // -Xmx=1960m
    //assertEquals(342486717, between(1, Integer.MAX_VALUE));
    // -Xmx=1966m
    //assertEquals(343535293, between(1, Integer.MAX_VALUE));
      assertEquals(343535293, between(1, Integer.MAX_VALUE));
    assertEquals(31, depth);
  }
  
  public int between(int from, int to) {
    if ((to - from) <= 1) 
      return (to);
    depth++;
    int i = from + ((to - from) /2);
    System.out.print("['" + depth + "'," + (i) + "]," );
    try { 
      Object[] a = new Object[i];
      a[1]="1";
      a=null;
      return (between(i, to));
    } catch (OutOfMemoryError e) {
      return (between(from, i));
    }      
  }

}
