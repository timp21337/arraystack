import ArrayStack.ArrayStack;
public class runArrayStack {

	public static void main( String[] args) {

		ArrayStack myStack = new ArrayStack(20);
		
		System.out.println("Starting run of ArrayStack");
		
		myStack.push(2);
		myStack.push(4);
		myStack.push(8);

		System.out.println(myStack.size());
		
		System.out.println(myStack.pop());
		System.out.println(myStack.pop());
		System.out.println(myStack.pop());

		System.out.println("Finished run of ArrayStack");
	}
}
