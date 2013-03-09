package ArrayStack;

public class FixedArrayStackTest extends ArrayStackSpec {

  @Override
  Sut getDefaultStack() {
    return new Sut(new FixedArrayStack());
  }

  @Override
  Sut getSizedStack(int size) {
    return new Sut(new FixedArrayStack(size));
  }

}
