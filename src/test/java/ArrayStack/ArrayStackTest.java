package ArrayStack;

import jdslcomp.simple.api.StackEmptyException;
import junit.framework.TestCase;

// JUnit 3

public class ArrayStackTest extends TestCase {
	
	
	// Test constructor
	
	public void testConstructor() { 
		try {
  		new ArrayStack(-1);
		} catch (NegativeArraySizeException e) { 
		  e = null; // bug
		}
    ArrayStack zero = new ArrayStack(0);
    try { 
      zero.pop();
    } catch (StackEmptyException e) { 
      e = null;
    }
    try { 
      zero.push(new Integer(1));
    } catch (ArrayIndexOutOfBoundsException e) { 
      e = null; //bug
    }
    try { 
      zero.pop();
    } catch (ArrayIndexOutOfBoundsException e) { 
      e = null; //bug
    }
    try { 
      zero.top();
    } catch (ArrayIndexOutOfBoundsException e) { 
      e = null; //bug
    }
    
    ArrayStack one = new ArrayStack(1);
    one.push("1");
    try { 
     one.push(new Integer(2));
    } catch (ArrayIndexOutOfBoundsException e) { 
      e = null; //bug
    }
	}
	public void testPush() {
		ArrayStack myStack = new ArrayStack(20);
		myStack.push(new Integer(2));
	}

}
