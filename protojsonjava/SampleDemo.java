package protojsonjava;
import java.util.ArrayList;

public class SampleDemo {
  public String name = "er gou";
  public Number age = 40;
  public Child son = new Child();
  public ArrayList<Child> daughters;
  public SampleDemo() {
    daughters = new ArrayList<Child>();
    daughters.add(new Child("tilala", 23));
    daughters.add(new Child("wendi", 14));
    daughters.add(new Child("umeily", 17));
  }
}

class Child {
  public String name = "ga zi";
  public Number age = 20;
  Child() {

  }
  Child(String name, Number age ) {
    this.name = name;
    this.age = age;
  }
}