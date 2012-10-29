package ArrayStack;

import jdslcomp.simple.api.Stack;
import jdslcomp.simple.api.StackEmptyException;
import jdslcomp.simple.api.StackFullException;
//JUnit 3
import junit.framework.TestCase;

public abstract class ArrayStackSpec extends TestCase {
	
	public void testNegativeSizeConstructor() { 
		try {
  		getSizedStack(-1);
		} catch (NegativeArraySizeException e) { 
		  fail(failMessage(e)); // bug
		} catch (IllegalArgumentException e) { 
      e = null; // expected
		}
	}
  public void testZeroSizedConstructor() {
    @SuppressWarnings("unused")
    Stack zero = null;
    try { 
      zero = getSizedStack(0);
      fail("Should have bombed with IllegalArgumentException"); 
    } catch (IllegalArgumentException e) { 
      e = null; // expected
    }
	}

	public void testOneConstructor() {
    Stack one = getSizedStack(1);
    one.push("1");
    try { 
     one.push(new Integer(2));
    } catch (ArrayIndexOutOfBoundsException e) { 
      fail("Should have bombed with StackFullException"); 
    } catch (StackFullException e) { 
      e = null; // expected
    }
	}

  public void testThreeConstructor() {
    Stack one = getSizedStack(3);
    one.push("1");
    one.push("2");
    one.push("3");
    try { 
     one.push(new Integer(2));
    } catch (ArrayIndexOutOfBoundsException e) { 
      fail("Should have bombed with StackFullException"); 
    } catch (StackFullException e) { 
      e = null; // expected
    }
  }
  
  public void testCapacityConstructor() {
    Stack one = getSizedStack(ArrayStack.CAPACITY);
    exerciseSized(one, ArrayStack.CAPACITY);
  }
  public void testDefaultConstructor() {
    Stack one = getDefaultStack();
    exerciseSized(one, ArrayStack.CAPACITY);
  }

  private void exerciseSized(Stack one, int size) {
    for (int i = 0; i < size; i++) { 
      one.push(new Integer(i));
    }
    try { 
     one.push("breaker");
    } catch (ArrayIndexOutOfBoundsException e) { 
      fail("Should have bombed with StackFullException"); 
    } catch (StackFullException e) { 
      e = null; // expected
    }
    for (int i = 0; i < size; i++) { 
      one.pop();
    }
    try { 
      one.pop();
     } catch (ArrayIndexOutOfBoundsException e) { 
       fail("Should have bombed with StackFullException"); 
     } catch (StackEmptyException e) { 
       e = null; // expected
     }
    
  }
  
  
  

  public void testPush() {
		Stack myStack = getSizedStack(20);
		myStack.push(new Integer(2));
	}

  
  abstract Stack getDefaultStack();
  abstract Stack getSizedStack(int size);
  
  private String failMessage(NegativeArraySizeException e) {
    return e.getClass().getName() + (e.getMessage() == null ? "" : ":" + e.getMessage());
  }
}
