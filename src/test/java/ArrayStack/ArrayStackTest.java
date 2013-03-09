package ArrayStack;

public class ArrayStackTest extends ArrayStackSpec {

  @Override
  Sut getDefaultStack() {
    return new Sut(new ArrayStack());
  }

  @Override
  Sut getSizedStack(int size) {
    return new Sut(new ArrayStack(size));
  }

}
