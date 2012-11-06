package ArrayStack;


import junit.framework.TestCase;

public class VMLimitsTest extends TestCase {
  int MAX_DEPTH = 14;
  int depth = 0;
  
  public void testChop() { 
    between(1, Integer.MAX_VALUE);
  }
  
  int between(int from, int to) {
    depth++;
    if (from == to) 
      return (to);
    int h = (to - from) /2;
    if (h == 1) 
      return (to);
    int i = from + h;
//    System.err.print("" + (from) + ", " + (to) +  ", " + i);
//    System.err.print("" + (i) + ", " + Math.log(Math.log10(new Double(i))));
    System.err.print("['" + depth + "'," + (i) );
    try { 
      Object[] a = new Object[i];
      System.err.println("],");
      a = null;
      return (between(i, to));
    } catch (OutOfMemoryError e) {
      System.err.println("],");
      e = null;
      return (between(from, i));
    }      
  }

}
